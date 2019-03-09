package org.fmi.movietracker.web.rest;
import org.fmi.movietracker.service.MovieStatusService;
import org.fmi.movietracker.web.rest.errors.BadRequestAlertException;
import org.fmi.movietracker.web.rest.util.HeaderUtil;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.fmi.movietracker.service.dto.MovieStatusDTO;
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
 * REST controller for managing MovieStatus.
 */
@RestController
@RequestMapping("/api")
public class MovieStatusResource {

    private final Logger log = LoggerFactory.getLogger(MovieStatusResource.class);

    private static final String ENTITY_NAME = "movieStatus";

    private final MovieStatusService movieStatusService;

    public MovieStatusResource(MovieStatusService movieStatusService) {
        this.movieStatusService = movieStatusService;
    }

    /**
     * POST  /movie-statuses : Create a new movieStatus.
     *
     * @param movieStatusDTO the movieStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieStatusDTO, or with status 400 (Bad Request) if the movieStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-statuses")
    public ResponseEntity<MovieStatusDTO> createMovieStatus(@Valid @RequestBody MovieStatusDTO movieStatusDTO) throws URISyntaxException {
        log.debug("REST request to save MovieStatus : {}", movieStatusDTO);
        if (movieStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieStatusDTO result = movieStatusService.save(movieStatusDTO);
        return ResponseEntity.created(new URI("/api/movie-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-statuses : Updates an existing movieStatus.
     *
     * @param movieStatusDTO the movieStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieStatusDTO,
     * or with status 400 (Bad Request) if the movieStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the movieStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-statuses")
    public ResponseEntity<MovieStatusDTO> updateMovieStatus(@Valid @RequestBody MovieStatusDTO movieStatusDTO) throws URISyntaxException {
        log.debug("REST request to update MovieStatus : {}", movieStatusDTO);
        if (movieStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieStatusDTO result = movieStatusService.save(movieStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movieStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-statuses : get all the movieStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movieStatuses in body
     */
    @GetMapping("/movie-statuses")
    public ResponseEntity<List<MovieStatusDTO>> getAllMovieStatuses(Pageable pageable) {
        log.debug("REST request to get a page of MovieStatuses");
        Page<MovieStatusDTO> page = movieStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /movie-statuses/:id : get the "id" movieStatus.
     *
     * @param id the id of the movieStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movie-statuses/{id}")
    public ResponseEntity<MovieStatusDTO> getMovieStatus(@PathVariable Long id) {
        log.debug("REST request to get MovieStatus : {}", id);
        Optional<MovieStatusDTO> movieStatusDTO = movieStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieStatusDTO);
    }

    /**
     * DELETE  /movie-statuses/:id : delete the "id" movieStatus.
     *
     * @param id the id of the movieStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-statuses/{id}")
    public ResponseEntity<Void> deleteMovieStatus(@PathVariable Long id) {
        log.debug("REST request to delete MovieStatus : {}", id);
        movieStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
