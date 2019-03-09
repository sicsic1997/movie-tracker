package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.MovieGenreMappingService;
import org.fmi.movietracker.domain.MovieGenreMapping;
import org.fmi.movietracker.repository.MovieGenreMappingRepository;
import org.fmi.movietracker.service.dto.MovieGenreMappingDTO;
import org.fmi.movietracker.service.mapper.MovieGenreMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MovieGenreMapping.
 */
@Service
@Transactional
public class MovieGenreMappingServiceImpl implements MovieGenreMappingService {

    private final Logger log = LoggerFactory.getLogger(MovieGenreMappingServiceImpl.class);

    private final MovieGenreMappingRepository movieGenreMappingRepository;

    private final MovieGenreMappingMapper movieGenreMappingMapper;

    public MovieGenreMappingServiceImpl(MovieGenreMappingRepository movieGenreMappingRepository, MovieGenreMappingMapper movieGenreMappingMapper) {
        this.movieGenreMappingRepository = movieGenreMappingRepository;
        this.movieGenreMappingMapper = movieGenreMappingMapper;
    }

    /**
     * Save a movieGenreMapping.
     *
     * @param movieGenreMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MovieGenreMappingDTO save(MovieGenreMappingDTO movieGenreMappingDTO) {
        log.debug("Request to save MovieGenreMapping : {}", movieGenreMappingDTO);
        MovieGenreMapping movieGenreMapping = movieGenreMappingMapper.toEntity(movieGenreMappingDTO);
        movieGenreMapping = movieGenreMappingRepository.save(movieGenreMapping);
        return movieGenreMappingMapper.toDto(movieGenreMapping);
    }

    /**
     * Get all the movieGenreMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovieGenreMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovieGenreMappings");
        return movieGenreMappingRepository.findAll(pageable)
            .map(movieGenreMappingMapper::toDto);
    }


    /**
     * Get one movieGenreMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MovieGenreMappingDTO> findOne(Long id) {
        log.debug("Request to get MovieGenreMapping : {}", id);
        return movieGenreMappingRepository.findById(id)
            .map(movieGenreMappingMapper::toDto);
    }

    /**
     * Delete the movieGenreMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MovieGenreMapping : {}", id);        movieGenreMappingRepository.deleteById(id);
    }
}
