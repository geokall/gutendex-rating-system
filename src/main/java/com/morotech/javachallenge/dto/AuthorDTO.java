package com.morotech.javachallenge.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AuthorDTO {

    private String name;

    @JsonAlias("birth_year")
    private String birthYear;

    @JsonAlias("death_year")
    private String deathYear;
}
