package com.morotech.javachallenge.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RatingDTO {

    @NotNull(message = "Book id is mandatory")
    private Long bookId;

    @NotNull(message = "Book rate is mandatory")
    @Min(value = 1, message = "Minimum range is 1")
    @Max(value = 5, message = "Maximum range is 5")
    private Integer rating;

    private String review;

    private Integer month;
}
