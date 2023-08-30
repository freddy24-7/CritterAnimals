package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule addSchedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, addSchedule);
        addSchedule = scheduleService.addSchedule(addSchedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        BeanUtils.copyProperties(addSchedule, scheduleDTO);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return getScheduleDTOs(scheduleService.getScheduleByPetId(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return getScheduleDTOs(scheduleService.getScheduleByEmployeeId(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> customerSchedules = scheduleService.getScheduleByCustomerId(customerId);
        return getScheduleDTOs(customerSchedules);
    }

    private List<ScheduleDTO> getScheduleDTOs(List<Schedule> schedules) {
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(getEmployeeIdsFromSchedule(schedule));
        scheduleDTO.setPetIds(getPetIdsFromSchedule(schedule));
        return scheduleDTO;
    }

    private List<Long> getPetIdsFromSchedule(Schedule schedule) {
        return schedule.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
    }

    private List<Long> getEmployeeIdsFromSchedule(Schedule schedule) {
        return schedule.getEmployees().stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
    }
}
