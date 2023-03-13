package com.morotech.javachallenge.repository;

import com.morotech.javachallenge.entity.RatingEntity;
import com.morotech.javachallenge.projection.BookProjectionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findByBookId(Long id);

    @Query(value = "SELECT book_id as bookId, AVG(rating) as averageRating " +
            "FROM {h-schema}rating " +
            "GROUP BY book_id " +
            "ORDER BY 2 DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<BookProjectionDTO> fetchTopBooksBy(Long limit);
}
