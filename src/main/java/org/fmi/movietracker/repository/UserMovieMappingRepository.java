package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.UserMovieMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserMovieMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMovieMappingRepository extends JpaRepository<UserMovieMapping, Long> {

    @Query("select user_movie_mapping from UserMovieMapping user_movie_mapping where user_movie_mapping.user.login = ?#{principal.username}")
    List<UserMovieMapping> findByUserIsCurrentUser();

}
