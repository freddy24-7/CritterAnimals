package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public Pet addPet(Pet pet, Long customerId) {
        //Getting customer object
        Customer petOwner = getCustomerById(customerId);

        //Allocating owner to pet
        pet.setCustomer(petOwner);

        //Saving the pet
        Pet addedPet = petRepository.save(pet);

        //Adding pet to owners list of pets
        petOwner.getPets().add(addedPet);

        //Updating customer/owner data
        customerRepository.save(petOwner);
        return addedPet;
    }

    public Pet findPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByCustomer(long customerId) {
        Customer customer = getCustomerById(customerId);
        return petRepository.findByCustomer(customer);
    }

    private Customer getCustomerById(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.orElse(null);
    }
}
