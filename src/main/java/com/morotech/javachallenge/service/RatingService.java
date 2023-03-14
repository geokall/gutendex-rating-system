package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.BookAverageDTO;
import com.morotech.javachallenge.dto.BookDetailsDTO;
import com.morotech.javachallenge.dto.RatingDTO;
import com.morotech.javachallenge.entity.RatingEntity;
import com.morotech.javachallenge.projection.BookAverageProjectionDTO;
import com.morotech.javachallenge.projection.BookProjectionDTO;

import java.util.List;

public interface RatingService {

    BookDetailsDTO rateBook(RatingDTO dto);

    BookDetailsDTO findBookDetails(Long id);

    List<BookProjectionDTO> fetchTopBooksBy(Long limit);

    BookAverageDTO fetchBookAvgPerMonth(Long id);
}
