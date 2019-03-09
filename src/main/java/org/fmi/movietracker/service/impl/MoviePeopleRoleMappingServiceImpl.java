package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.MoviePeopleRoleMappingService;
import org.fmi.movietracker.domain.MoviePeopleRoleMapping;
import org.fmi.movietracker.repository.MoviePeopleRoleMappingRepository;
import org.fmi.movietracker.service.dto.MoviePeopleRoleMappingDTO;
import org.fmi.movietracker.service.mapper.MoviePeopleRoleMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MoviePeopleRoleMapping.
 */
@Service
@Transactional
public class MoviePeopleRoleMappingServiceImpl implements MoviePeopleRoleMappingService {

    private final Logger log = LoggerFactory.getLogger(MoviePeopleRoleMappingServiceImpl.class);

    private final MoviePeopleRoleMappingRepository moviePeopleRoleMappingRepository;

    private final MoviePeopleRoleMappingMapper moviePeopleRoleMappingMapper;

    public MoviePeopleRoleMappingServiceImpl(MoviePeopleRoleMappingRepository moviePeopleRoleMappingRepository, MoviePeopleRoleMappingMapper moviePeopleRoleMappingMapper) {
        this.moviePeopleRoleMappingRepository = moviePeopleRoleMappingRepository;
        this.moviePeopleRoleMappingMapper = moviePeopleRoleMappingMapper;
    }

    /**
     * Save a moviePeopleRoleMapping.
     *
     * @param moviePeopleRoleMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MoviePeopleRoleMappingDTO save(MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO) {
        log.debug("Request to save MoviePeopleRoleMapping : {}", moviePeopleRoleMappingDTO);
        MoviePeopleRoleMapping moviePeopleRoleMapping = moviePeopleRoleMappingMapper.toEntity(moviePeopleRoleMappingDTO);
        moviePeopleRoleMapping = moviePeopleRoleMappingRepository.save(moviePeopleRoleMapping);
        return moviePeopleRoleMappingMapper.toDto(moviePeopleRoleMapping);
    }

    /**
     * Get all the moviePeopleRoleMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MoviePeopleRoleMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MoviePeopleRoleMappings");
        return moviePeopleRoleMappingRepository.findAll(pageable)
            .map(moviePeopleRoleMappingMapper::toDto);
    }


    /**
     * Get one moviePeopleRoleMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MoviePeopleRoleMappingDTO> findOne(Long id) {
        log.debug("Request to get MoviePeopleRoleMapping : {}", id);
        return moviePeopleRoleMappingRepository.findById(id)
            .map(moviePeopleRoleMappingMapper::toDto);
    }

    /**
     * Delete the moviePeopleRoleMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MoviePeopleRoleMapping : {}", id);        moviePeopleRoleMappingRepository.deleteById(id);
    }
}
