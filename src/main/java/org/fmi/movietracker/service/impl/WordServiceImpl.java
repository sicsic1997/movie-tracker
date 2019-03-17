package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.service.WordService;
import org.fmi.movietracker.domain.Word;
import org.fmi.movietracker.repository.WordRepository;
import org.fmi.movietracker.service.dto.WordDTO;
import org.fmi.movietracker.service.mapper.WordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Word.
 */
@Service
@Transactional
public class WordServiceImpl implements WordService {

    private final Logger log = LoggerFactory.getLogger(WordServiceImpl.class);

    private final WordRepository wordRepository;

    private final WordMapper wordMapper;

    public WordServiceImpl(WordRepository wordRepository, WordMapper wordMapper) {
        this.wordRepository = wordRepository;
        this.wordMapper = wordMapper;
    }

    /**
     * Save a word.
     *
     * @param wordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WordDTO save(WordDTO wordDTO) {
        log.debug("Request to save Word : {}", wordDTO);
        Word word = wordMapper.toEntity(wordDTO);
        word = wordRepository.save(word);
        return wordMapper.toDto(word);
    }

    /**
     * Get all the words.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Words");
        return wordRepository.findAll(pageable)
            .map(wordMapper::toDto);
    }


    /**
     * Get one word by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WordDTO> findOne(Long id) {
        log.debug("Request to get Word : {}", id);
        return wordRepository.findById(id)
            .map(wordMapper::toDto);
    }

    /**
     * Delete the word by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Word : {}", id);        wordRepository.deleteById(id);
    }
}
