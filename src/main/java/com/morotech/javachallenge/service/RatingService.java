package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.BookDetailsDTO;
import com.morotech.javachallenge.dto.RatingDTO;
import com.morotech.javachallenge.entity.RatingEntity;

import java.util.List;

public interface RatingService {

    void rateBook(RatingDTO dto);

    BookDetailsDTO findBookDetails(Long id);

    List<RatingEntity> fetchRatingsBy(Long id);
}
