package org.fmi.movietracker.web.rest.custom;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieDashboardCustomResource {

    private final Logger log = LoggerFactory.getLogger(MovieDashboardCustomResource.class);

    @Autowired
    private MovieDashboardQueryService movieDashboardQueryService;

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

}
