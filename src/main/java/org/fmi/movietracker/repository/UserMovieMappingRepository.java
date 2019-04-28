package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.User;
import org.fmi.movietracker.domain.UserMovieMapping;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserMovieMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMovieMappingRepository extends JpaRepository<UserMovieMapping, Long> {

    @Query("select user_movie_mapping from UserMovieMapping user_movie_mapping where user_movie_mapping.user.login = ?#{principal.username}")
    List<UserMovieMapping> findByUserIsCurrentUser();

    @Query("select movie " +
        "       from UserMovieMapping user_movie_mapping " +
        "   join user_movie_mapping.movie movie" +
        "   join user_movie_mapping.movieStatus movie_status " +
        "   where user_movie_mapping.user.login = ?#{principal.username}" +
        "       and movie_status.code = 'HISTORY' ")
    List<Movie> findMoviesInHistoryByUserIsCurrentUser();

    @Query("select movie " +
        "       from UserMovieMapping user_movie_mapping " +
        "   join user_movie_mapping.movie movie" +
        "   join user_movie_mapping.movieStatus movie_status " +
        "   where user_movie_mapping.user.login = ?#{principal.username}" +
        "       and movie_status.code = 'WISH_LIST' ")
    List<Movie> findMoviesInWIshlistByUserIsCurrentUser();

    @Query("select user_movie_mapping from UserMovieMapping user_movie_mapping " +
        " join user_movie_mapping.movie movie where user_movie_mapping.user.login = ?#{principal.username} " +
        " and movie.id = :movieId ")
    List<UserMovieMapping> findByUserIsCurrentUserAndMovieId(@Param("movieId") Long movieId);

    Optional<UserMovieMapping> findByUser_LoginAndMovie_IdAndMovieStatus_Code(String userLogin, Long movieId, String movieStatusCode);

}
