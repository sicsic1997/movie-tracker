package org.fmi.movietracker.web.rest;
import org.fmi.movietracker.service.WordService;
import org.fmi.movietracker.web.rest.errors.BadRequestAlertException;
import org.fmi.movietracker.web.rest.util.HeaderUtil;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.fmi.movietracker.service.dto.WordDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Word.
 */
@RestController
@RequestMapping("/api")
public class WordResource {

    private final Logger log = LoggerFactory.getLogger(WordResource.class);

    private static final String ENTITY_NAME = "word";

    private final WordService wordService;

    public WordResource(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * POST  /words : Create a new word.
     *
     * @param wordDTO the wordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wordDTO, or with status 400 (Bad Request) if the word has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/words")
    public ResponseEntity<WordDTO> createWord(@Valid @RequestBody WordDTO wordDTO) throws URISyntaxException {
        log.debug("REST request to save Word : {}", wordDTO);
        if (wordDTO.getId() != null) {
            throw new BadRequestAlertException("A new word cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WordDTO result = wordService.save(wordDTO);
        return ResponseEntity.created(new URI("/api/words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /words : Updates an existing word.
     *
     * @param wordDTO the wordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wordDTO,
     * or with status 400 (Bad Request) if the wordDTO is not valid,
     * or with status 500 (Internal Server Error) if the wordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/words")
    public ResponseEntity<WordDTO> updateWord(@Valid @RequestBody WordDTO wordDTO) throws URISyntaxException {
        log.debug("REST request to update Word : {}", wordDTO);
        if (wordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WordDTO result = wordService.save(wordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /words : get all the words.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of words in body
     */
    @GetMapping("/words")
    public ResponseEntity<List<WordDTO>> getAllWords(Pageable pageable) {
        log.debug("REST request to get a page of Words");
        Page<WordDTO> page = wordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/words");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /words/:id : get the "id" word.
     *
     * @param id the id of the wordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/words/{id}")
    public ResponseEntity<WordDTO> getWord(@PathVariable Long id) {
        log.debug("REST request to get Word : {}", id);
        Optional<WordDTO> wordDTO = wordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wordDTO);
    }

    /**
     * DELETE  /words/:id : delete the "id" word.
     *
     * @param id the id of the wordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/words/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        log.debug("REST request to delete Word : {}", id);
        wordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
