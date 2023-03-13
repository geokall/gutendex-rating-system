package com.morotech.javachallenge.repository;

import com.morotech.javachallenge.entity.RatingEntity;
import com.morotech.javachallenge.projection.BookAverageProjectionDTO;
import com.morotech.javachallenge.projection.BookProjectionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findByBookId(Long id);

    Optional<RatingEntity> findTopByBookId(Long bookId);

    @Query(value = "SELECT book_id AS bookId, AVG(rating) AS averageRating " +
            "FROM {h-schema}rating " +
            "GROUP BY book_id " +
            "ORDER BY 2 DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<BookProjectionDTO> fetchTopBooksBy(Long limit);

    @Query(value = "SELECT book_id AS bookId, month, AVG(rating) AS averageRating " +
            "FROM {h-schema}rating " +
            "WHERE book_id = :bookId " +
            "GROUP BY book_id, month " +
            "ORDER BY month DESC", nativeQuery = true)
    List<BookAverageProjectionDTO> fetchAverageRatingsPerMonth(Long bookId);
}
