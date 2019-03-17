package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.WordDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Word.
 */
public interface WordService {

    /**
     * Save a word.
     *
     * @param wordDTO the entity to save
     * @return the persisted entity
     */
    WordDTO save(WordDTO wordDTO);

    /**
     * Get all the words.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WordDTO> findAll(Pageable pageable);


    /**
     * Get the "id" word.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WordDTO> findOne(Long id);

    /**
     * Delete the "id" word.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
