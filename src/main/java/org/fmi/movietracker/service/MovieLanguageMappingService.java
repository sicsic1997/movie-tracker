package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.MovieLanguageMappingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MovieLanguageMapping.
 */
public interface MovieLanguageMappingService {

    /**
     * Save a movieLanguageMapping.
     *
     * @param movieLanguageMappingDTO the entity to save
     * @return the persisted entity
     */
    MovieLanguageMappingDTO save(MovieLanguageMappingDTO movieLanguageMappingDTO);

    /**
     * Get all the movieLanguageMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MovieLanguageMappingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" movieLanguageMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MovieLanguageMappingDTO> findOne(Long id);

    /**
     * Delete the "id" movieLanguageMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
