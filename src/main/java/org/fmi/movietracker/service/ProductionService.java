package org.fmi.movietracker.service;

import org.fmi.movietracker.service.dto.ProductionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Production.
 */
public interface ProductionService {

    /**
     * Save a production.
     *
     * @param productionDTO the entity to save
     * @return the persisted entity
     */
    ProductionDTO save(ProductionDTO productionDTO);

    /**
     * Get all the productions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" production.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductionDTO> findOne(Long id);

    /**
     * Delete the "id" production.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
