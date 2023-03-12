package com.morotech.javachallenge.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {

    private Long id;

    private String title;

    private List<AuthorDTO> authors;

    private List<String> languages;

    @JsonAlias("download_count")
    private Long downloadCount;
}
