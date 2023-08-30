package com.udacity.jdnd.course3.critter.dtos;

import com.udacity.jdnd.course3.critter.utils.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {
    private long id;
    private PetType type;
    private String name;
    private long customerId;
    private LocalDate birthDate;
    private String notes;

}
