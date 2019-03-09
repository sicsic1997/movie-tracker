package org.fmi.movietracker.web.rest;
import org.fmi.movietracker.service.MovieGenreMappingService;
import org.fmi.movietracker.web.rest.errors.BadRequestAlertException;
import org.fmi.movietracker.web.rest.util.HeaderUtil;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.fmi.movietracker.service.dto.MovieGenreMappingDTO;
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
 * REST controller for managing MovieGenreMapping.
 */
@RestController
@RequestMapping("/api")
public class MovieGenreMappingResource {

    private final Logger log = LoggerFactory.getLogger(MovieGenreMappingResource.class);

    private static final String ENTITY_NAME = "movieGenreMapping";

    private final MovieGenreMappingService movieGenreMappingService;

    public MovieGenreMappingResource(MovieGenreMappingService movieGenreMappingService) {
        this.movieGenreMappingService = movieGenreMappingService;
    }

    /**
     * POST  /movie-genre-mappings : Create a new movieGenreMapping.
     *
     * @param movieGenreMappingDTO the movieGenreMappingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieGenreMappingDTO, or with status 400 (Bad Request) if the movieGenreMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-genre-mappings")
    public ResponseEntity<MovieGenreMappingDTO> createMovieGenreMapping(@RequestBody MovieGenreMappingDTO movieGenreMappingDTO) throws URISyntaxException {
        log.debug("REST request to save MovieGenreMapping : {}", movieGenreMappingDTO);
        if (movieGenreMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieGenreMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieGenreMappingDTO result = movieGenreMappingService.save(movieGenreMappingDTO);
        return ResponseEntity.created(new URI("/api/movie-genre-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-genre-mappings : Updates an existing movieGenreMapping.
     *
     * @param movieGenreMappingDTO the movieGenreMappingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieGenreMappingDTO,
     * or with status 400 (Bad Request) if the movieGenreMappingDTO is not valid,
     * or with status 500 (Internal Server Error) if the movieGenreMappingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-genre-mappings")
    public ResponseEntity<MovieGenreMappingDTO> updateMovieGenreMapping(@RequestBody MovieGenreMappingDTO movieGenreMappingDTO) throws URISyntaxException {
        log.debug("REST request to update MovieGenreMapping : {}", movieGenreMappingDTO);
        if (movieGenreMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieGenreMappingDTO result = movieGenreMappingService.save(movieGenreMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movieGenreMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-genre-mappings : get all the movieGenreMappings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movieGenreMappings in body
     */
    @GetMapping("/movie-genre-mappings")
    public ResponseEntity<List<MovieGenreMappingDTO>> getAllMovieGenreMappings(Pageable pageable) {
        log.debug("REST request to get a page of MovieGenreMappings");
        Page<MovieGenreMappingDTO> page = movieGenreMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-genre-mappings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /movie-genre-mappings/:id : get the "id" movieGenreMapping.
     *
     * @param id the id of the movieGenreMappingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieGenreMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movie-genre-mappings/{id}")
    public ResponseEntity<MovieGenreMappingDTO> getMovieGenreMapping(@PathVariable Long id) {
        log.debug("REST request to get MovieGenreMapping : {}", id);
        Optional<MovieGenreMappingDTO> movieGenreMappingDTO = movieGenreMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieGenreMappingDTO);
    }

    /**
     * DELETE  /movie-genre-mappings/:id : delete the "id" movieGenreMapping.
     *
     * @param id the id of the movieGenreMappingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-genre-mappings/{id}")
    public ResponseEntity<Void> deleteMovieGenreMapping(@PathVariable Long id) {
        log.debug("REST request to delete MovieGenreMapping : {}", id);
        movieGenreMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
