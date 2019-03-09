package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.ProductionService;
import org.fmi.movietracker.domain.Production;
import org.fmi.movietracker.repository.ProductionRepository;
import org.fmi.movietracker.service.dto.ProductionDTO;
import org.fmi.movietracker.service.mapper.ProductionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Production.
 */
@Service
@Transactional
public class ProductionServiceImpl implements ProductionService {

    private final Logger log = LoggerFactory.getLogger(ProductionServiceImpl.class);

    private final ProductionRepository productionRepository;

    private final ProductionMapper productionMapper;

    public ProductionServiceImpl(ProductionRepository productionRepository, ProductionMapper productionMapper) {
        this.productionRepository = productionRepository;
        this.productionMapper = productionMapper;
    }

    /**
     * Save a production.
     *
     * @param productionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductionDTO save(ProductionDTO productionDTO) {
        log.debug("Request to save Production : {}", productionDTO);
        Production production = productionMapper.toEntity(productionDTO);
        production = productionRepository.save(production);
        return productionMapper.toDto(production);
    }

    /**
     * Get all the productions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Productions");
        return productionRepository.findAll(pageable)
            .map(productionMapper::toDto);
    }


    /**
     * Get one production by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductionDTO> findOne(Long id) {
        log.debug("Request to get Production : {}", id);
        return productionRepository.findById(id)
            .map(productionMapper::toDto);
    }

    /**
     * Delete the production by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Production : {}", id);        productionRepository.deleteById(id);
    }
}
