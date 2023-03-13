package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.BookDetailsDTO;
import com.morotech.javachallenge.dto.RatingDTO;
import com.morotech.javachallenge.entity.RatingEntity;
import com.morotech.javachallenge.projection.BookProjectionDTO;

import java.util.List;

public interface RatingService {

    void rateBook(RatingDTO dto);

    BookDetailsDTO findBookDetails(Long id);

    List<RatingEntity> fetchRatingsBy(Long id);

    List<BookProjectionDTO> fetchTopBooksBy(Long limit);
}
