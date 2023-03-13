package com.morotech.javachallenge.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RatingDTO {

    @NotNull(message = "Book id is mandatory")
    private Long bookId;

    @NotNull(message = "Book rate is mandatory")
    @Min(value = 0, message = "Minimum rating value is 0")
    @Max(value = 5, message = "Maximum rating value is 5")
    private Integer rating;

    @Length(max = 1000, message = "Maximum review characters are up to 1.000")
    private String review;

    @Min(value = 1, message = "Minimum month value is 1")
    @Max(value = 12, message = "Maximum month value is 12")
    private Integer month;
}
