package org.fmi.movietracker.web.rest;
import org.fmi.movietracker.service.MoviePeopleRoleMappingService;
import org.fmi.movietracker.web.rest.errors.BadRequestAlertException;
import org.fmi.movietracker.web.rest.util.HeaderUtil;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.fmi.movietracker.service.dto.MoviePeopleRoleMappingDTO;
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
 * REST controller for managing MoviePeopleRoleMapping.
 */
@RestController
@RequestMapping("/api")
public class MoviePeopleRoleMappingResource {

    private final Logger log = LoggerFactory.getLogger(MoviePeopleRoleMappingResource.class);

    private static final String ENTITY_NAME = "moviePeopleRoleMapping";

    private final MoviePeopleRoleMappingService moviePeopleRoleMappingService;

    public MoviePeopleRoleMappingResource(MoviePeopleRoleMappingService moviePeopleRoleMappingService) {
        this.moviePeopleRoleMappingService = moviePeopleRoleMappingService;
    }

    /**
     * POST  /movie-people-role-mappings : Create a new moviePeopleRoleMapping.
     *
     * @param moviePeopleRoleMappingDTO the moviePeopleRoleMappingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moviePeopleRoleMappingDTO, or with status 400 (Bad Request) if the moviePeopleRoleMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-people-role-mappings")
    public ResponseEntity<MoviePeopleRoleMappingDTO> createMoviePeopleRoleMapping(@RequestBody MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO) throws URISyntaxException {
        log.debug("REST request to save MoviePeopleRoleMapping : {}", moviePeopleRoleMappingDTO);
        if (moviePeopleRoleMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new moviePeopleRoleMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoviePeopleRoleMappingDTO result = moviePeopleRoleMappingService.save(moviePeopleRoleMappingDTO);
        return ResponseEntity.created(new URI("/api/movie-people-role-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-people-role-mappings : Updates an existing moviePeopleRoleMapping.
     *
     * @param moviePeopleRoleMappingDTO the moviePeopleRoleMappingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moviePeopleRoleMappingDTO,
     * or with status 400 (Bad Request) if the moviePeopleRoleMappingDTO is not valid,
     * or with status 500 (Internal Server Error) if the moviePeopleRoleMappingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-people-role-mappings")
    public ResponseEntity<MoviePeopleRoleMappingDTO> updateMoviePeopleRoleMapping(@RequestBody MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO) throws URISyntaxException {
        log.debug("REST request to update MoviePeopleRoleMapping : {}", moviePeopleRoleMappingDTO);
        if (moviePeopleRoleMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoviePeopleRoleMappingDTO result = moviePeopleRoleMappingService.save(moviePeopleRoleMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moviePeopleRoleMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-people-role-mappings : get all the moviePeopleRoleMappings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moviePeopleRoleMappings in body
     */
    @GetMapping("/movie-people-role-mappings")
    public ResponseEntity<List<MoviePeopleRoleMappingDTO>> getAllMoviePeopleRoleMappings(Pageable pageable) {
        log.debug("REST request to get a page of MoviePeopleRoleMappings");
        Page<MoviePeopleRoleMappingDTO> page = moviePeopleRoleMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-people-role-mappings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /movie-people-role-mappings/:id : get the "id" moviePeopleRoleMapping.
     *
     * @param id the id of the moviePeopleRoleMappingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moviePeopleRoleMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movie-people-role-mappings/{id}")
    public ResponseEntity<MoviePeopleRoleMappingDTO> getMoviePeopleRoleMapping(@PathVariable Long id) {
        log.debug("REST request to get MoviePeopleRoleMapping : {}", id);
        Optional<MoviePeopleRoleMappingDTO> moviePeopleRoleMappingDTO = moviePeopleRoleMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moviePeopleRoleMappingDTO);
    }

    /**
     * DELETE  /movie-people-role-mappings/:id : delete the "id" moviePeopleRoleMapping.
     *
     * @param id the id of the moviePeopleRoleMappingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-people-role-mappings/{id}")
    public ResponseEntity<Void> deleteMoviePeopleRoleMapping(@PathVariable Long id) {
        log.debug("REST request to delete MoviePeopleRoleMapping : {}", id);
        moviePeopleRoleMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
