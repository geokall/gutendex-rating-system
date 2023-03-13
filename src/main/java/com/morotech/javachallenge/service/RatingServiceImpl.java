package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.BookDetailsDTO;
import com.morotech.javachallenge.dto.RatingDTO;
import com.morotech.javachallenge.dto.ResultDTO;
import com.morotech.javachallenge.entity.RatingEntity;
import com.morotech.javachallenge.exception.MoroNotFoundException;
import com.morotech.javachallenge.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ResultDTO book = gutendexService.findBookBy(dto.getBookId());

        saveRating(dto, book.getId());
    }

    @Override
    public BookDetailsDTO findBookDetails(Long id) {
        ResultDTO book = gutendexService.findBookBy(id);

        List<RatingEntity> listOfRatings = fetchRatingsBy(book.getId());

        return toBookDetailsDTO(book, listOfRatings);
    }

    @Override
    public List<RatingEntity> fetchRatingsBy(Long id) {
        List<RatingEntity> listOfRatings = ratingRepository.findByBookId(id);

        if (listOfRatings.isEmpty()) {
            throw new MoroNotFoundException(BOOK_NOT_FOUND);
        }

        return listOfRatings;
    }


    private void saveRating(RatingDTO dto, Long id) {
        RatingEntity rating = new RatingEntity();
        rating.setBookId(id);

        rating.setRating(dto.getRating());
        rating.setReview(dto.getReview());

        Optional.ofNullable(dto.getMonth())
                .ifPresentOrElse(rating::setMonth, () -> setCurrentMonth(rating));

        ratingRepository.save(rating);
    }

    private BookDetailsDTO toBookDetailsDTO(ResultDTO book, List<RatingEntity> listOfRatings) {
        BookDetailsDTO dto = new BookDetailsDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthors(book.getAuthors());
        dto.setLanguages(book.getLanguages());
        dto.setDownloadCount(book.getDownloadCount());

        Double averageRating = fetchAverageRating(listOfRatings);
        dto.setAverageRating(averageRating);

        List<String> reviews = fetchRatings(listOfRatings);
        dto.setReviews(reviews);

        return dto;
    }

    private List<String> fetchRatings(List<RatingEntity> listOfRatings) {
        return listOfRatings.stream()
                .filter(Objects::nonNull)
                .map(RatingEntity::getReview)
                .collect(Collectors.toList());
    }

    private Double fetchAverageRating(List<RatingEntity> listOfRatings) {
        return listOfRatings.stream()
                .mapToDouble(RatingEntity::getRating)
                .average()
                .orElse(Double.NaN);
    }

    private void setCurrentMonth(RatingEntity rating) {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonth().getValue();

        rating.setMonth(month);
    }
}
