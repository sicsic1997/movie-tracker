package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.MovieLanguageMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MovieLanguageMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieLanguageMappingRepository extends JpaRepository<MovieLanguageMapping, Long> {

}
