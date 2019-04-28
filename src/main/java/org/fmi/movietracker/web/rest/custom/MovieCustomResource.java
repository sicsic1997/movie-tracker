package org.fmi.movietracker.web.rest.custom;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.User;
import org.fmi.movietracker.service.MovieService;
import org.fmi.movietracker.service.UserService;
import org.fmi.movietracker.service.custom.SimilarityCustomService;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.fmi.movietracker.service.mapper.MovieMapper;
import org.fmi.movietracker.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Movie.
 */
@RestController
@RequestMapping("/api")
public class MovieCustomResource {

    private final Logger log = LoggerFactory.getLogger(MovieCustomResource.class);

    @Autowired
    private SimilarityCustomService similarityCustomService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieMapper movieMapper;

    /**
     * GET  /suggestion/:id : get the "id" movie suggestions.
     *
     * @param id the id of the movie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the List of movieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/suggestion/{id}")
    public ResponseEntity<List<MovieDTO>> getSuggestionsForMovie(@PathVariable Long id) {
        log.debug("REST request to get suggestions for movie : {}", id);
        Optional<MovieDTO> movieDTOOptional = movieService.findOne(id);
        if(movieDTOOptional.isPresent()) {
            Movie movie = movieMapper.toEntity(movieDTOOptional.get());
            List<MovieDTO> movieList = similarityCustomService.getSuggestionsForMovie(movie);
            return ResponseEntity.ok().body(movieList);
        }
        return ResponseEntity.ok(null);
    }

    /**
     * GET  /suggestion/user : get the "id" movie suggestions.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the List of movieDTO, or with status 404 (Not Found)
     */
    @GetMapping("/suggestion/user")
    public ResponseEntity<List<MovieDTO>> getSuggestionsForMovieAndUser() {
        log.debug("REST request to get suggestions for user : {}");
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            List<MovieDTO> movieList = similarityCustomService.getSuggestionsForUser(user);
            return ResponseEntity.ok().body(movieList);
        }
        return ResponseEntity.ok(null);
    }

}
