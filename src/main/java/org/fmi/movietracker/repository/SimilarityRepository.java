package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.Similarity;
import org.fmi.movietracker.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Similarity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SimilarityRepository extends JpaRepository<Similarity, Long> {

    @Query("" +
        "SELECT " +
        "   s.movieB " +
        "FROM Similarity s " +
        "WHERE s.movieA = :movie " +
        "ORDER by s.value DESC ")
    List<Movie> getSuggestionsForMovie(@Param("movie") Movie movie);

    @Query("" +
        "SELECT " +
        "   s.movieB " +
        "FROM Similarity s " +
        "WHERE " +
        "   s.movieA IN (SELECT umm.movie FROM UserMovieMapping umm WHERE umm.user = :user) " +
        "AND s.movieB NOT IN (SELECT umm.movie FROM UserMovieMapping umm WHERE umm.user = :user) " +
        "ORDER by s.value DESC ")
    List<Movie> getSuggestionsUser(@Param("user") User user);

}
