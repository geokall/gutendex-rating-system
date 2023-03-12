package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.GutendexDTO;

public interface GutendexService {

    GutendexDTO searchBooks(String bookTitle, Long page);

    GutendexDTO findBookBy(Long id);
}
