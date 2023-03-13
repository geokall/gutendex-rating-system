package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.GutendexDTO;
import com.morotech.javachallenge.dto.ResultDTO;

public interface GutendexService {

    GutendexDTO searchBooks(String bookTitle, Long page);

    ResultDTO findBookBy(Long id);
}
