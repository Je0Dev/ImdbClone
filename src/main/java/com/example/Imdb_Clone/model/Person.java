package com.example.Imdb_Clone.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import java.time.LocalDate;

@MappedSuperclass // 1. Designates this as a base class for entities.
@Data
public abstract class Person {

    private String name;
    private LocalDate birthDate;
    private String biography;
}
