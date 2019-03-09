package org.fmi.movietracker.web.rest;
import org.fmi.movietracker.service.MovieLanguageMappingService;
import org.fmi.movietracker.web.rest.errors.BadRequestAlertException;
import org.fmi.movietracker.web.rest.util.HeaderUtil;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.fmi.movietracker.service.dto.MovieLanguageMappingDTO;
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
 * REST controller for managing MovieLanguageMapping.
 */
@RestController
@RequestMapping("/api")
public class MovieLanguageMappingResource {

    private final Logger log = LoggerFactory.getLogger(MovieLanguageMappingResource.class);

    private static final String ENTITY_NAME = "movieLanguageMapping";

    private final MovieLanguageMappingService movieLanguageMappingService;

    public MovieLanguageMappingResource(MovieLanguageMappingService movieLanguageMappingService) {
        this.movieLanguageMappingService = movieLanguageMappingService;
    }

    /**
     * POST  /movie-language-mappings : Create a new movieLanguageMapping.
     *
     * @param movieLanguageMappingDTO the movieLanguageMappingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieLanguageMappingDTO, or with status 400 (Bad Request) if the movieLanguageMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-language-mappings")
    public ResponseEntity<MovieLanguageMappingDTO> createMovieLanguageMapping(@RequestBody MovieLanguageMappingDTO movieLanguageMappingDTO) throws URISyntaxException {
        log.debug("REST request to save MovieLanguageMapping : {}", movieLanguageMappingDTO);
        if (movieLanguageMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new movieLanguageMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieLanguageMappingDTO result = movieLanguageMappingService.save(movieLanguageMappingDTO);
        return ResponseEntity.created(new URI("/api/movie-language-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-language-mappings : Updates an existing movieLanguageMapping.
     *
     * @param movieLanguageMappingDTO the movieLanguageMappingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieLanguageMappingDTO,
     * or with status 400 (Bad Request) if the movieLanguageMappingDTO is not valid,
     * or with status 500 (Internal Server Error) if the movieLanguageMappingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-language-mappings")
    public ResponseEntity<MovieLanguageMappingDTO> updateMovieLanguageMapping(@RequestBody MovieLanguageMappingDTO movieLanguageMappingDTO) throws URISyntaxException {
        log.debug("REST request to update MovieLanguageMapping : {}", movieLanguageMappingDTO);
        if (movieLanguageMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovieLanguageMappingDTO result = movieLanguageMappingService.save(movieLanguageMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movieLanguageMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-language-mappings : get all the movieLanguageMappings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movieLanguageMappings in body
     */
    @GetMapping("/movie-language-mappings")
    public ResponseEntity<List<MovieLanguageMappingDTO>> getAllMovieLanguageMappings(Pageable pageable) {
        log.debug("REST request to get a page of MovieLanguageMappings");
        Page<MovieLanguageMappingDTO> page = movieLanguageMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-language-mappings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /movie-language-mappings/:id : get the "id" movieLanguageMapping.
     *
     * @param id the id of the movieLanguageMappingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieLanguageMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movie-language-mappings/{id}")
    public ResponseEntity<MovieLanguageMappingDTO> getMovieLanguageMapping(@PathVariable Long id) {
        log.debug("REST request to get MovieLanguageMapping : {}", id);
        Optional<MovieLanguageMappingDTO> movieLanguageMappingDTO = movieLanguageMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieLanguageMappingDTO);
    }

    /**
     * DELETE  /movie-language-mappings/:id : delete the "id" movieLanguageMapping.
     *
     * @param id the id of the movieLanguageMappingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-language-mappings/{id}")
    public ResponseEntity<Void> deleteMovieLanguageMapping(@PathVariable Long id) {
        log.debug("REST request to delete MovieLanguageMapping : {}", id);
        movieLanguageMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
