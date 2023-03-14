package com.morotech.javachallenge.service;

import com.morotech.javachallenge.dto.*;
import com.morotech.javachallenge.entity.RatingEntity;
import com.morotech.javachallenge.exception.MoroBadRequestException;
import com.morotech.javachallenge.exception.MoroNotFoundException;
import com.morotech.javachallenge.projection.BookAverageProjectionDTO;
import com.morotech.javachallenge.projection.BookProjectionDTO;
import com.morotech.javachallenge.repository.RatingRepository;
import com.morotech.javachallenge.utils.MoroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.morotech.javachallenge.utils.MoroConstant.*;
import static com.morotech.javachallenge.utils.MoroUtil.toMonthNameBy;

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
    @CachePut(value = CACHE_BOOK_DETAILS, key = "#dto.bookId")
    public BookDetailsDTO rateBook(RatingDTO dto) {
        ResultDTO book = gutendexService.findBookBy(dto.getBookId());

        saveRating(dto, book.getId());

        List<RatingEntity> listOfDbRatingInfo = fetchDatabaseRatingInfo(book.getId());

        return toBookDetailsDTO(book, listOfDbRatingInfo);
    }

    @Override
    @Cacheable(value = CACHE_BOOK_DETAILS, key = "#id")
    public BookDetailsDTO findBookDetails(Long id) {
        ResultDTO book = gutendexService.findBookBy(id);

        List<RatingEntity> listOfDbRatingInfo = fetchDatabaseRatingInfo(id);

        return toBookDetailsDTO(book, listOfDbRatingInfo);
    }

    @Override
    public List<BookProjectionDTO> fetchTopBooksBy(Long limit) {
        if (limit < 1 || limit > 10) {
            throw new MoroBadRequestException(LIMIT_ERROR);
        }

        return ratingRepository.fetchTopBooksBy(limit);
    }

    @Override
    public BookAverageDTO fetchBookAvgPerMonth(Long id) {
        Long bookId = validateBookExistenceInDb(id);

        var listOfAverageRatingsPerMonth = ratingRepository.fetchAverageRatingsPerMonth(id);

        List<AveragePerMonthDTO> ratingsPerMonth = listOfAverageRatingsPerMonth.stream()
                .map(this::toAveragePerMonthDTO)
                .collect(Collectors.toList());

        BookAverageDTO dto = new BookAverageDTO();
        dto.setId(bookId);
        dto.setRatingsPerMonth(ratingsPerMonth);

        return dto;
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

    private BookDetailsDTO toBookDetailsDTO(ResultDTO book, List<RatingEntity> listOfDbRatingInfo) {
        BookDetailsDTO dto = new BookDetailsDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthors(book.getAuthors());
        dto.setLanguages(book.getLanguages());
        dto.setDownloadCount(book.getDownloadCount());

        Double averageRating = fetchAverageRating(listOfDbRatingInfo);
        dto.setAverageRating(averageRating);

        List<String> reviews = fetchReviews(listOfDbRatingInfo);
        dto.setReviews(reviews);

        return dto;
    }

    private List<String> fetchReviews(List<RatingEntity> listOfDbRatingInfo) {
        return listOfDbRatingInfo.stream()
                .map(RatingEntity::getReview)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Double fetchAverageRating(List<RatingEntity> listOfDbRatingInfo) {
        return listOfDbRatingInfo.stream()
                .mapToDouble(RatingEntity::getRating)
                .average()
                .orElse(Double.NaN);
    }

    private void setCurrentMonth(RatingEntity rating) {
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonth().getValue();

        rating.setMonth(month);
    }

    private List<RatingEntity> fetchDatabaseRatingInfo(Long id) {
        List<RatingEntity> listOfRatings = ratingRepository.findByBookId(id);

        if (listOfRatings.isEmpty()) {
            throw new MoroNotFoundException(DB_BOOK_NOT_FOUND);
        }

        return listOfRatings;
    }

    private AveragePerMonthDTO toAveragePerMonthDTO(BookAverageProjectionDTO bookRating) {
        AveragePerMonthDTO avgDTO = new AveragePerMonthDTO();
        avgDTO.setAverageRating(bookRating.getAverageRating());

        Optional.of(bookRating.getMonth())
                .map(MoroUtil::toMonthNameBy)
                .ifPresent(avgDTO::setMonth);

        return avgDTO;
    }

    private Long validateBookExistenceInDb(Long id) {
        //fetching top instead of fetching list, to improve performance
        return ratingRepository.findTopByBookId(id)
                .map(RatingEntity::getBookId)
                .orElseThrow(() -> {
                    throw new MoroNotFoundException(DB_BOOK_NOT_FOUND);
                });
    }
}
