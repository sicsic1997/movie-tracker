package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Movie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Movie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAll();

}
