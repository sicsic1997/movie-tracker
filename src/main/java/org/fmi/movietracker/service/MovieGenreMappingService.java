package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.MovieGenreMappingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MovieGenreMapping.
 */
public interface MovieGenreMappingService {

    /**
     * Save a movieGenreMapping.
     *
     * @param movieGenreMappingDTO the entity to save
     * @return the persisted entity
     */
    MovieGenreMappingDTO save(MovieGenreMappingDTO movieGenreMappingDTO);

    /**
     * Get all the movieGenreMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MovieGenreMappingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" movieGenreMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MovieGenreMappingDTO> findOne(Long id);

    /**
     * Delete the "id" movieGenreMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
