package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.MovieDTO;
import org.fmi.movietracker.service.dto.UserMovieMappingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserMovieMapping.
 */
public interface UserMovieMappingService {

    /**
     * Save a userMovieMapping.
     *
     * @param userMovieMappingDTO the entity to save
     * @return the persisted entity
     */
    UserMovieMappingDTO save(UserMovieMappingDTO userMovieMappingDTO);

    /**
     * Get all the userMovieMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserMovieMappingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userMovieMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserMovieMappingDTO> findOne(Long id);

    /**
     * Delete the "id" userMovieMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List<UserMovieMappingDTO> findMappingForMovieAndCurrentLogin(Long movieId);

    List<UserMovieMappingDTO> findByCurrentLogin();

    void deleteByMovieIdAndLoggedUser(Long movieId, String movieStatusCode);

    void createByMovieIdAndLoggedUser(Long movieId, String movieStatusCode);

    List<MovieDTO> findByCurrentLoginInHistory();

    List<MovieDTO> findByCurrentLoginInWishlist();

}
