package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.GutendexDTO;
import com.morotech.javachallenge.dto.RatingDTO;
import com.morotech.javachallenge.dto.ResultDTO;
import com.morotech.javachallenge.entity.RatingEntity;
import com.morotech.javachallenge.exception.MoroNotFoundException;
import com.morotech.javachallenge.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.morotech.javachallenge.utils.MoroConstant.*;

@Service
public class RatingServiceImpl implements RatingService {

    private final GutendexService gutendexService;
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(GutendexService gutendexService,
                             RatingRepository ratingRepository) {
        this.gutendexService = gutendexService;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void rateBook(RatingDTO dto) {
        ResultDTO book = findBook(dto.getBookId());

        RatingEntity rating = new RatingEntity();
        rating.setBookId(book.getId());
        rating.setRating(dto.getRating());
        rating.setReview(dto.getReview());

        Optional.ofNullable(dto.getMonth())
                .ifPresentOrElse(rating::setMonth, () -> setCurrentMonth(rating));

        ratingRepository.save(rating);
    }

    private ResultDTO findBook(Long id) {
        GutendexDTO book = gutendexService.findBookBy(id);

        if (book.getResults().isEmpty()) {
            throw new MoroNotFoundException(BOOK_NOT_FOUND);
        }

        return book.getResults().stream()
                .findFirst()
                .orElseThrow(() -> {
                    throw new MoroNotFoundException(BOOK_RESULTS_NOT_FOUND);
                });
    }

    private void setCurrentMonth(RatingEntity rating) {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonth().getValue();

        rating.setMonth(month);
    }
}
