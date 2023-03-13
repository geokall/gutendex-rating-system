package com.morotech.javachallenge.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookAverageDTO {

    private Long id;

    private List<AveragePerMonthDTO> ratingsPerMonth;
}
