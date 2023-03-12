package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.GutendexDTO;

public interface GutendexService {

    GutendexDTO searchBook(String bookTitle, Long page);
}
