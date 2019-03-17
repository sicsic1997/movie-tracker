package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.SimilarityService;
import org.fmi.movietracker.domain.Similarity;
import org.fmi.movietracker.repository.SimilarityRepository;
import org.fmi.movietracker.service.dto.SimilarityDTO;
import org.fmi.movietracker.service.mapper.SimilarityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Similarity.
 */
@Service
@Transactional
public class SimilarityServiceImpl implements SimilarityService {

    private final Logger log = LoggerFactory.getLogger(SimilarityServiceImpl.class);

    private final SimilarityRepository similarityRepository;

    private final SimilarityMapper similarityMapper;

    public SimilarityServiceImpl(SimilarityRepository similarityRepository, SimilarityMapper similarityMapper) {
        this.similarityRepository = similarityRepository;
        this.similarityMapper = similarityMapper;
    }

    /**
     * Save a similarity.
     *
     * @param similarityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SimilarityDTO save(SimilarityDTO similarityDTO) {
        log.debug("Request to save Similarity : {}", similarityDTO);
        Similarity similarity = similarityMapper.toEntity(similarityDTO);
        similarity = similarityRepository.save(similarity);
        return similarityMapper.toDto(similarity);
    }

    /**
     * Get all the similarities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SimilarityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Similarities");
        return similarityRepository.findAll(pageable)
            .map(similarityMapper::toDto);
    }


    /**
     * Get one similarity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SimilarityDTO> findOne(Long id) {
        log.debug("Request to get Similarity : {}", id);
        return similarityRepository.findById(id)
            .map(similarityMapper::toDto);
    }

    /**
     * Delete the similarity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Similarity : {}", id);        similarityRepository.deleteById(id);
    }
}
