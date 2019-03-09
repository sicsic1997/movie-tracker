package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.MovieLanguageMappingService;
import org.fmi.movietracker.domain.MovieLanguageMapping;
import org.fmi.movietracker.repository.MovieLanguageMappingRepository;
import org.fmi.movietracker.service.dto.MovieLanguageMappingDTO;
import org.fmi.movietracker.service.mapper.MovieLanguageMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MovieLanguageMapping.
 */
@Service
@Transactional
public class MovieLanguageMappingServiceImpl implements MovieLanguageMappingService {

    private final Logger log = LoggerFactory.getLogger(MovieLanguageMappingServiceImpl.class);

    private final MovieLanguageMappingRepository movieLanguageMappingRepository;

    private final MovieLanguageMappingMapper movieLanguageMappingMapper;

    public MovieLanguageMappingServiceImpl(MovieLanguageMappingRepository movieLanguageMappingRepository, MovieLanguageMappingMapper movieLanguageMappingMapper) {
        this.movieLanguageMappingRepository = movieLanguageMappingRepository;
        this.movieLanguageMappingMapper = movieLanguageMappingMapper;
    }

    /**
     * Save a movieLanguageMapping.
     *
     * @param movieLanguageMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MovieLanguageMappingDTO save(MovieLanguageMappingDTO movieLanguageMappingDTO) {
        log.debug("Request to save MovieLanguageMapping : {}", movieLanguageMappingDTO);
        MovieLanguageMapping movieLanguageMapping = movieLanguageMappingMapper.toEntity(movieLanguageMappingDTO);
        movieLanguageMapping = movieLanguageMappingRepository.save(movieLanguageMapping);
        return movieLanguageMappingMapper.toDto(movieLanguageMapping);
    }

    /**
     * Get all the movieLanguageMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovieLanguageMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovieLanguageMappings");
        return movieLanguageMappingRepository.findAll(pageable)
            .map(movieLanguageMappingMapper::toDto);
    }


    /**
     * Get one movieLanguageMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MovieLanguageMappingDTO> findOne(Long id) {
        log.debug("Request to get MovieLanguageMapping : {}", id);
        return movieLanguageMappingRepository.findById(id)
            .map(movieLanguageMappingMapper::toDto);
    }

    /**
     * Delete the movieLanguageMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MovieLanguageMapping : {}", id);        movieLanguageMappingRepository.deleteById(id);
    }
}
