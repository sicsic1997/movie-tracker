package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.MovieStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the MovieStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieStatusRepository extends JpaRepository<MovieStatus, Long> {

    Optional<MovieStatus> getByCode(String code);

}
