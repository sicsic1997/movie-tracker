package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.RatedService;
import org.fmi.movietracker.domain.Rated;
import org.fmi.movietracker.repository.RatedRepository;
import org.fmi.movietracker.service.dto.RatedDTO;
import org.fmi.movietracker.service.mapper.RatedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Rated.
 */
@Service
@Transactional
public class RatedServiceImpl implements RatedService {

    private final Logger log = LoggerFactory.getLogger(RatedServiceImpl.class);

    private final RatedRepository ratedRepository;

    private final RatedMapper ratedMapper;

    public RatedServiceImpl(RatedRepository ratedRepository, RatedMapper ratedMapper) {
        this.ratedRepository = ratedRepository;
        this.ratedMapper = ratedMapper;
    }

    /**
     * Save a rated.
     *
     * @param ratedDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RatedDTO save(RatedDTO ratedDTO) {
        log.debug("Request to save Rated : {}", ratedDTO);
        Rated rated = ratedMapper.toEntity(ratedDTO);
        rated = ratedRepository.save(rated);
        return ratedMapper.toDto(rated);
    }

    /**
     * Get all the rateds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RatedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rateds");
        return ratedRepository.findAll(pageable)
            .map(ratedMapper::toDto);
    }


    /**
     * Get one rated by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RatedDTO> findOne(Long id) {
        log.debug("Request to get Rated : {}", id);
        return ratedRepository.findById(id)
            .map(ratedMapper::toDto);
    }

    /**
     * Delete the rated by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rated : {}", id);        ratedRepository.deleteById(id);
    }
}
