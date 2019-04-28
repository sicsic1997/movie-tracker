package org.fmi.movietracker.service.custom;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.User;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Custom Interface for managing Similarity.
 */
public interface SimilarityCustomService {

    List<MovieDTO> getSuggestionsForMovie(Movie movie);

    List<MovieDTO> getSuggestionsForUser(User user);

}
