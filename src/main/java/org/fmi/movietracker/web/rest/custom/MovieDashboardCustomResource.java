package org.fmi.movietracker.web.rest.custom;

import io.github.jhipster.web.util.ResponseUtil;
import org.fmi.movietracker.service.MovieService;
import org.fmi.movietracker.service.custom.impl.MovieDashboardQueryService;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.fmi.movietracker.service.dto.MovieDashboardCriteria;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieDashboardCustomResource {

    private final Logger log = LoggerFactory.getLogger(MovieDashboardCustomResource.class);

    @Autowired
    private MovieDashboardQueryService movieDashboardQueryService;

    @Autowired
    private MovieService movieService;

    /**
     * GET  /movie-dashboard : get all the movies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of movies in body
     */
    @GetMapping("/movie-dashboard")
    public ResponseEntity<List<MovieDTO>> getAllMovies(MovieDashboardCriteria movieDashboardCriteria, Pageable pageable) {
        log.debug("REST request to get a page of Movies");
        Page<MovieDTO> page = movieDashboardQueryService.findByCriteria(movieDashboardCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/movie-dashboard");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /movies/:id : get the "id" movie.
     *
     * @param id the id of the movieDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/movie-dashboard/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        Optional<MovieDTO> movieDTO = movieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movieDTO);
    }

}
