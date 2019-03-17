package org.fmi.movietracker.web.rest;
import org.fmi.movietracker.service.SimilarityService;
import org.fmi.movietracker.web.rest.errors.BadRequestAlertException;
import org.fmi.movietracker.web.rest.util.HeaderUtil;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.fmi.movietracker.service.dto.SimilarityDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Similarity.
 */
@RestController
@RequestMapping("/api")
public class SimilarityResource {

    private final Logger log = LoggerFactory.getLogger(SimilarityResource.class);

    private static final String ENTITY_NAME = "similarity";

    private final SimilarityService similarityService;

    public SimilarityResource(SimilarityService similarityService) {
        this.similarityService = similarityService;
    }

    /**
     * POST  /similarities : Create a new similarity.
     *
     * @param similarityDTO the similarityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new similarityDTO, or with status 400 (Bad Request) if the similarity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/similarities")
    public ResponseEntity<SimilarityDTO> createSimilarity(@RequestBody SimilarityDTO similarityDTO) throws URISyntaxException {
        log.debug("REST request to save Similarity : {}", similarityDTO);
        if (similarityDTO.getId() != null) {
            throw new BadRequestAlertException("A new similarity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SimilarityDTO result = similarityService.save(similarityDTO);
        return ResponseEntity.created(new URI("/api/similarities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /similarities : Updates an existing similarity.
     *
     * @param similarityDTO the similarityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated similarityDTO,
     * or with status 400 (Bad Request) if the similarityDTO is not valid,
     * or with status 500 (Internal Server Error) if the similarityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/similarities")
    public ResponseEntity<SimilarityDTO> updateSimilarity(@RequestBody SimilarityDTO similarityDTO) throws URISyntaxException {
        log.debug("REST request to update Similarity : {}", similarityDTO);
        if (similarityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SimilarityDTO result = similarityService.save(similarityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, similarityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /similarities : get all the similarities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of similarities in body
     */
    @GetMapping("/similarities")
    public ResponseEntity<List<SimilarityDTO>> getAllSimilarities(Pageable pageable) {
        log.debug("REST request to get a page of Similarities");
        Page<SimilarityDTO> page = similarityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/similarities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /similarities/:id : get the "id" similarity.
     *
     * @param id the id of the similarityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the similarityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/similarities/{id}")
    public ResponseEntity<SimilarityDTO> getSimilarity(@PathVariable Long id) {
        log.debug("REST request to get Similarity : {}", id);
        Optional<SimilarityDTO> similarityDTO = similarityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(similarityDTO);
    }

    /**
     * DELETE  /similarities/:id : delete the "id" similarity.
     *
     * @param id the id of the similarityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/similarities/{id}")
    public ResponseEntity<Void> deleteSimilarity(@PathVariable Long id) {
        log.debug("REST request to delete Similarity : {}", id);
        similarityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
