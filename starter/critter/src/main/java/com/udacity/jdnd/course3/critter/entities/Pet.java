package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.utils.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Customer customer;

}
