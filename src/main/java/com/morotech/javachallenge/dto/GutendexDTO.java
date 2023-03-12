package com.morotech.javachallenge.dto;

import lombok.Data;

import java.util.List;

@Data
public class GutendexDTO {

    private String next;

    private String previous;

    private List<ResultDTO> results;
}
