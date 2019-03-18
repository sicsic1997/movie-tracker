package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.User;
import org.fmi.movietracker.domain.UserMovieMapping;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
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
