package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.MoviePeopleRoleMappingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MoviePeopleRoleMapping.
 */
public interface MoviePeopleRoleMappingService {

    /**
     * Save a moviePeopleRoleMapping.
     *
     * @param moviePeopleRoleMappingDTO the entity to save
     * @return the persisted entity
     */
    MoviePeopleRoleMappingDTO save(MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO);

    /**
     * Get all the moviePeopleRoleMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MoviePeopleRoleMappingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" moviePeopleRoleMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MoviePeopleRoleMappingDTO> findOne(Long id);

    /**
     * Delete the "id" moviePeopleRoleMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
