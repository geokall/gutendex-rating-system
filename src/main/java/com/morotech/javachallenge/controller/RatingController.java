package com.morotech.javachallenge.controller;

import com.morotech.javachallenge.dto.BookDetailsDTO;
import com.morotech.javachallenge.dto.GutendexDTO;
import com.morotech.javachallenge.dto.RatingDTO;
import com.morotech.javachallenge.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rate")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/book")
    public ResponseEntity<GutendexDTO> rateBook(@Valid @RequestBody RatingDTO dto) {
        ratingService.rateBook(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetch-book-details/{id}")
    public ResponseEntity<BookDetailsDTO> fetchBookDetails(@PathVariable Long id) {
        BookDetailsDTO response = ratingService.findBookDetails(id);

        return ResponseEntity.ok().body(response);
    }
}
