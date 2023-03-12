package com.morotech.javachallenge.controller;

import com.morotech.javachallenge.dto.GutendexDTO;
import com.morotech.javachallenge.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gutendex")
public class GutendexController {

    private final GutendexService gutendexService;

    @Autowired
    public GutendexController(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
    }

    @GetMapping("/search-book")
    public ResponseEntity<GutendexDTO> searchBook(@RequestParam String bookTitle,
                                                  @RequestParam(required = false) Long page) {
        GutendexDTO response = gutendexService.searchBooks(bookTitle, page);

        return ResponseEntity.ok().body(response);
    }
}
