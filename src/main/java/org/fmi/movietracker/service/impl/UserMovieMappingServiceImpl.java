package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.UserMovieMappingService;
import org.fmi.movietracker.domain.UserMovieMapping;
import org.fmi.movietracker.repository.UserMovieMappingRepository;
import org.fmi.movietracker.service.dto.UserMovieMappingDTO;
import org.fmi.movietracker.service.mapper.UserMovieMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserMovieMapping.
 */
@Service
@Transactional
public class UserMovieMappingServiceImpl implements UserMovieMappingService {

    private final Logger log = LoggerFactory.getLogger(UserMovieMappingServiceImpl.class);

    private final UserMovieMappingRepository userMovieMappingRepository;

    private final UserMovieMappingMapper userMovieMappingMapper;

    public UserMovieMappingServiceImpl(UserMovieMappingRepository userMovieMappingRepository, UserMovieMappingMapper userMovieMappingMapper) {
        this.userMovieMappingRepository = userMovieMappingRepository;
        this.userMovieMappingMapper = userMovieMappingMapper;
    }

    /**
     * Save a userMovieMapping.
     *
     * @param userMovieMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserMovieMappingDTO save(UserMovieMappingDTO userMovieMappingDTO) {
        log.debug("Request to save UserMovieMapping : {}", userMovieMappingDTO);
        UserMovieMapping userMovieMapping = userMovieMappingMapper.toEntity(userMovieMappingDTO);
        userMovieMapping = userMovieMappingRepository.save(userMovieMapping);
        return userMovieMappingMapper.toDto(userMovieMapping);
    }

    /**
     * Get all the userMovieMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserMovieMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserMovieMappings");
        return userMovieMappingRepository.findAll(pageable)
            .map(userMovieMappingMapper::toDto);
    }


    /**
     * Get one userMovieMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserMovieMappingDTO> findOne(Long id) {
        log.debug("Request to get UserMovieMapping : {}", id);
        return userMovieMappingRepository.findById(id)
            .map(userMovieMappingMapper::toDto);
    }

    /**
     * Delete the userMovieMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserMovieMapping : {}", id);        userMovieMappingRepository.deleteById(id);
    }
}
