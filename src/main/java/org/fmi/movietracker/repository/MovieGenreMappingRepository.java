package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.MovieGenreMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MovieGenreMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieGenreMappingRepository extends JpaRepository<MovieGenreMapping, Long> {

}
