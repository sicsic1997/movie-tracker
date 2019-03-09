package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Rated;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rated entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatedRepository extends JpaRepository<Rated, Long> {

}
