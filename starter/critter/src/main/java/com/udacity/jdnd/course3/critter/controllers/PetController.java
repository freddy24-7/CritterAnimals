package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    private final CustomerRepository customerRepository;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if (petDTO.getCustomerId() != 0) {
            Customer owner = customerRepository.findById(petDTO.getCustomerId()).orElse(null);
            pet.setCustomer(owner);
        }
        Pet savedPet = petService.addPet(pet, petDTO.getCustomerId());
        return convertToDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPetById(@PathVariable long petId) {
        Pet pet = petService.findPetById(petId);
        return convertToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getAllPets() {
        List<Pet> pets = petService.getPets();
        return pets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByCustomer(ownerId);
        return pets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private PetDTO convertToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer().getId() != null) {
            petDTO.setCustomerId(pet.getCustomer().getId());
        }
        return petDTO;
    }
}
