package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.MovieStatusService;
import org.fmi.movietracker.domain.MovieStatus;
import org.fmi.movietracker.repository.MovieStatusRepository;
import org.fmi.movietracker.service.dto.MovieStatusDTO;
import org.fmi.movietracker.service.mapper.MovieStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MovieStatus.
 */
@Service
@Transactional
public class MovieStatusServiceImpl implements MovieStatusService {

    private final Logger log = LoggerFactory.getLogger(MovieStatusServiceImpl.class);

    private final MovieStatusRepository movieStatusRepository;

    private final MovieStatusMapper movieStatusMapper;

    public MovieStatusServiceImpl(MovieStatusRepository movieStatusRepository, MovieStatusMapper movieStatusMapper) {
        this.movieStatusRepository = movieStatusRepository;
        this.movieStatusMapper = movieStatusMapper;
    }

    /**
     * Save a movieStatus.
     *
     * @param movieStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MovieStatusDTO save(MovieStatusDTO movieStatusDTO) {
        log.debug("Request to save MovieStatus : {}", movieStatusDTO);
        MovieStatus movieStatus = movieStatusMapper.toEntity(movieStatusDTO);
        movieStatus = movieStatusRepository.save(movieStatus);
        return movieStatusMapper.toDto(movieStatus);
    }

    /**
     * Get all the movieStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovieStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovieStatuses");
        return movieStatusRepository.findAll(pageable)
            .map(movieStatusMapper::toDto);
    }


    /**
     * Get one movieStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MovieStatusDTO> findOne(Long id) {
        log.debug("Request to get MovieStatus : {}", id);
        return movieStatusRepository.findById(id)
            .map(movieStatusMapper::toDto);
    }

    /**
     * Delete the movieStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MovieStatus : {}", id);        movieStatusRepository.deleteById(id);
    }
}
