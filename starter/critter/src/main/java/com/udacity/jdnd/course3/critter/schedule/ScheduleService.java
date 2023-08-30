package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ScheduleService {

    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    private final ScheduleRepository scheduleRepository;

    public Schedule addSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petRepository.findAllById(petIds);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            return scheduleRepository.findAllByEmployees(employee);
        } else {
            return Collections.emptyList();
        }
    }

    public List<Schedule> getScheduleByPetId(long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet != null) {
            return scheduleRepository.findAllByPets(pet);
        } else {
            return Collections.emptyList();
        }
    }

    public List<Schedule> getScheduleByCustomerId(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        assert customer != null;
        List<Pet> pets = customer.getPets();
        return scheduleRepository.getAllByPetsIn(pets);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
