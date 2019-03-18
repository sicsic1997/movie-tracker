package org.fmi.movietracker.service.custom;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.User;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

/**
 * Service Custom Interface for managing Similarity.
 */
public interface SimilarityCustomService {

    Page<MovieDTO> getSuggestionsForMovie(Movie movie, Pageable pageable);

    Page<MovieDTO> getSuggestionsForMovieAndUser(Movie movie, User user, Pageable pageable);

}
