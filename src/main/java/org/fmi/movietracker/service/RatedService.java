package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.RatedDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Rated.
 */
public interface RatedService {

    /**
     * Save a rated.
     *
     * @param ratedDTO the entity to save
     * @return the persisted entity
     */
    RatedDTO save(RatedDTO ratedDTO);

    /**
     * Get all the rateds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RatedDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rated.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RatedDTO> findOne(Long id);

    /**
     * Delete the "id" rated.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
