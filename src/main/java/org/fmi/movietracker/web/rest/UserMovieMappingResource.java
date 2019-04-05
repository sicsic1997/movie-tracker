package org.fmi.movietracker.web.rest;
import org.fmi.movietracker.service.UserMovieMappingService;
import org.fmi.movietracker.web.rest.errors.BadRequestAlertException;
import org.fmi.movietracker.web.rest.util.HeaderUtil;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.fmi.movietracker.service.dto.UserMovieMappingDTO;
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
 * REST controller for managing UserMovieMapping.
 */
@RestController
@RequestMapping("/api")
public class UserMovieMappingResource {

    private final Logger log = LoggerFactory.getLogger(UserMovieMappingResource.class);

    private static final String ENTITY_NAME = "userMovieMapping";

    private final UserMovieMappingService userMovieMappingService;

    public UserMovieMappingResource(UserMovieMappingService userMovieMappingService) {
        this.userMovieMappingService = userMovieMappingService;
    }

    /**
     * POST  /user-movie-mappings : Create a new userMovieMapping.
     *
     * @param userMovieMappingDTO the userMovieMappingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userMovieMappingDTO, or with status 400 (Bad Request) if the userMovieMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-movie-mappings")
    public ResponseEntity<UserMovieMappingDTO> createUserMovieMapping(@RequestBody UserMovieMappingDTO userMovieMappingDTO) throws URISyntaxException {
        log.debug("REST request to save UserMovieMapping : {}", userMovieMappingDTO);
        if (userMovieMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new userMovieMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMovieMappingDTO result = userMovieMappingService.save(userMovieMappingDTO);
        return ResponseEntity.created(new URI("/api/user-movie-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-movie-mappings : Updates an existing userMovieMapping.
     *
     * @param userMovieMappingDTO the userMovieMappingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userMovieMappingDTO,
     * or with status 400 (Bad Request) if the userMovieMappingDTO is not valid,
     * or with status 500 (Internal Server Error) if the userMovieMappingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-movie-mappings")
    public ResponseEntity<UserMovieMappingDTO> updateUserMovieMapping(@RequestBody UserMovieMappingDTO userMovieMappingDTO) throws URISyntaxException {
        log.debug("REST request to update UserMovieMapping : {}", userMovieMappingDTO);
        if (userMovieMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserMovieMappingDTO result = userMovieMappingService.save(userMovieMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userMovieMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-movie-mappings : get all the userMovieMappings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userMovieMappings in body
     */
    @GetMapping("/user-movie-mappings")
    public ResponseEntity<List<UserMovieMappingDTO>> getAllUserMovieMappings(Pageable pageable) {
        log.debug("REST request to get a page of UserMovieMappings");
        Page<UserMovieMappingDTO> page = userMovieMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-movie-mappings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-movie-mappings/user-movie
     *
     * @param movieId
     * @param movieStatusCode
     * @return the ResponseEntity with status 200 (OK) and the list of userMovieMappings in body
     */
    @GetMapping("/user-movie-mappings/save")
    public ResponseEntity<Void> saveUserMappingForMovieAndLoggedUser(@RequestParam Long movieId, @RequestParam String movieStatusCode) {
        userMovieMappingService.createByMovieIdAndLoggedUser(movieId, movieStatusCode);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /user-movie-mappings/:id : get the "id" userMovieMapping.
     *
     * @param id the id of the userMovieMappingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userMovieMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-movie-mappings/{id}")
    public ResponseEntity<UserMovieMappingDTO> getUserMovieMapping(@PathVariable Long id) {
        log.debug("REST request to get UserMovieMapping : {}", id);
        Optional<UserMovieMappingDTO> userMovieMappingDTO = userMovieMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userMovieMappingDTO);
    }

    /**
     * GET  /user-movie-mappings/user-movie : get the "id" userMovieMapping.
     *
     * @param movieId the id of the movie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userMovieMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-movie-mappings/user-movie")
    public ResponseEntity<List<UserMovieMappingDTO>> getUserMovieMappingForMovieAndLoggedUser(@RequestParam Long movieId) {
        log.debug("REST request to get UserMovieMapping : {}, {}", movieId);
        List<UserMovieMappingDTO> userMovieMappingDTOList = userMovieMappingService.findMappingForMovieAndCurrentLogin(movieId);
        return ResponseEntity.ok().body(userMovieMappingDTOList);
    }

    /**
     * GET  /user-movie-mappings/user : get the "id" userMovieMapping.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the userMovieMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-movie-mappings/user")
    public ResponseEntity<List<UserMovieMappingDTO>> getUserMovieMappingForLoggedUser() {
        log.debug("REST request to get UserMovieMapping");
        List<UserMovieMappingDTO> userMovieMappingDTOList = userMovieMappingService.findByCurrentLogin();
        return ResponseEntity.ok().body(userMovieMappingDTOList);
    }

    /**
     * DELETE  /user-movie-mappings/:id : delete the "id" userMovieMapping.
     *
     * @param id the id of the userMovieMappingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-movie-mappings/{id}")
    public ResponseEntity<Void> deleteUserMovieMapping(@PathVariable Long id) {
        log.debug("REST request to delete UserMovieMapping : {}", id);
        userMovieMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * DELETE  /user-movie-mappings/user/:movieId/:movieStatusCode : delete the "movieId"
     *
     * @param movieId the id of the movie
     * @param movieStatusCode the code of the mapping
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-movie-mappings/user/{movieId}/{movieStatusCode}")
    public ResponseEntity<Void> deleteUserMovieMappingByMovie(@PathVariable Long movieId, @PathVariable String movieStatusCode) {
        log.debug("REST request to delete UserMovieMapping : {}", movieId);
        userMovieMappingService.deleteByMovieIdAndLoggedUser(movieId, movieStatusCode);
        return ResponseEntity.ok().build();
    }



}
