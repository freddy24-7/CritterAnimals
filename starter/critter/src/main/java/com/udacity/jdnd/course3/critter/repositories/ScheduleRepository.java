package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    //Finding schedules per pet instance
    List<Schedule> findAllByPets(Pet pet);

    //Finding schedules per employee instance
    List<Schedule> findAllByEmployees(Employee employee);

    //Finding schedules per customer instance
    List<Schedule> getAllByPetsIn(List<Pet> pets);
}
