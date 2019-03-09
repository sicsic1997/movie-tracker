package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.MovieStatusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MovieStatus.
 */
public interface MovieStatusService {

    /**
     * Save a movieStatus.
     *
     * @param movieStatusDTO the entity to save
     * @return the persisted entity
     */
    MovieStatusDTO save(MovieStatusDTO movieStatusDTO);

    /**
     * Get all the movieStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MovieStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" movieStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MovieStatusDTO> findOne(Long id);

    /**
     * Delete the "id" movieStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
