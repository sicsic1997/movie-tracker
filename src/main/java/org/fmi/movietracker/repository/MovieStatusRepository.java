package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.MovieStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MovieStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieStatusRepository extends JpaRepository<MovieStatus, Long> {

}
