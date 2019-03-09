package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.PeopleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing People.
 */
public interface PeopleService {

    /**
     * Save a people.
     *
     * @param peopleDTO the entity to save
     * @return the persisted entity
     */
    PeopleDTO save(PeopleDTO peopleDTO);

    /**
     * Get all the people.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PeopleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" people.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PeopleDTO> findOne(Long id);

    /**
     * Delete the "id" people.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
