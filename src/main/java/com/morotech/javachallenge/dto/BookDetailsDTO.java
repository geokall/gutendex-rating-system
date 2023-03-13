package com.morotech.javachallenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDetailsDTO extends ResultDTO {

    private Double averageRating;

    private List<String> reviews;
}
