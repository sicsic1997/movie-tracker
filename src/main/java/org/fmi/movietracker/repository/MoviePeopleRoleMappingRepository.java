package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.MoviePeopleRoleMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MoviePeopleRoleMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoviePeopleRoleMappingRepository extends JpaRepository<MoviePeopleRoleMapping, Long> {

}
