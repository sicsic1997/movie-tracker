package org.fmi.movietracker.web.rest;
import org.fmi.movietracker.service.RatedService;
import org.fmi.movietracker.web.rest.errors.BadRequestAlertException;
import org.fmi.movietracker.web.rest.util.HeaderUtil;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.fmi.movietracker.service.dto.RatedDTO;
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
 * REST controller for managing Rated.
 */
@RestController
@RequestMapping("/api")
public class RatedResource {

    private final Logger log = LoggerFactory.getLogger(RatedResource.class);

    private static final String ENTITY_NAME = "rated";

    private final RatedService ratedService;

    public RatedResource(RatedService ratedService) {
        this.ratedService = ratedService;
    }

    /**
     * POST  /rateds : Create a new rated.
     *
     * @param ratedDTO the ratedDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ratedDTO, or with status 400 (Bad Request) if the rated has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rateds")
    public ResponseEntity<RatedDTO> createRated(@Valid @RequestBody RatedDTO ratedDTO) throws URISyntaxException {
        log.debug("REST request to save Rated : {}", ratedDTO);
        if (ratedDTO.getId() != null) {
            throw new BadRequestAlertException("A new rated cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatedDTO result = ratedService.save(ratedDTO);
        return ResponseEntity.created(new URI("/api/rateds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rateds : Updates an existing rated.
     *
     * @param ratedDTO the ratedDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ratedDTO,
     * or with status 400 (Bad Request) if the ratedDTO is not valid,
     * or with status 500 (Internal Server Error) if the ratedDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rateds")
    public ResponseEntity<RatedDTO> updateRated(@Valid @RequestBody RatedDTO ratedDTO) throws URISyntaxException {
        log.debug("REST request to update Rated : {}", ratedDTO);
        if (ratedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RatedDTO result = ratedService.save(ratedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ratedDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rateds : get all the rateds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rateds in body
     */
    @GetMapping("/rateds")
    public ResponseEntity<List<RatedDTO>> getAllRateds(Pageable pageable) {
        log.debug("REST request to get a page of Rateds");
        Page<RatedDTO> page = ratedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rateds");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rateds/:id : get the "id" rated.
     *
     * @param id the id of the ratedDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ratedDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rateds/{id}")
    public ResponseEntity<RatedDTO> getRated(@PathVariable Long id) {
        log.debug("REST request to get Rated : {}", id);
        Optional<RatedDTO> ratedDTO = ratedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratedDTO);
    }

    /**
     * DELETE  /rateds/:id : delete the "id" rated.
     *
     * @param id the id of the ratedDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rateds/{id}")
    public ResponseEntity<Void> deleteRated(@PathVariable Long id) {
        log.debug("REST request to delete Rated : {}", id);
        ratedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
