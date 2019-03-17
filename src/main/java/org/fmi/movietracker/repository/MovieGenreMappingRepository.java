package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.MovieGenreMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the MovieGenreMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieGenreMappingRepository extends JpaRepository<MovieGenreMapping, Long> {

    List<MovieGenreMapping> getAllByMovie(Movie movie);

}
