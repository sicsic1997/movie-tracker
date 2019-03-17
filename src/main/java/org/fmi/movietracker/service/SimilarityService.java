package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.SimilarityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Similarity.
 */
public interface SimilarityService {

    /**
     * Save a similarity.
     *
     * @param similarityDTO the entity to save
     * @return the persisted entity
     */
    SimilarityDTO save(SimilarityDTO similarityDTO);

    /**
     * Get all the similarities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SimilarityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" similarity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SimilarityDTO> findOne(Long id);

    /**
     * Delete the "id" similarity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
