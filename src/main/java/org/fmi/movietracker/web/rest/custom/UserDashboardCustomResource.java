package org.fmi.movietracker.web.rest.custom;

import org.fmi.movietracker.service.UserMovieMappingService;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.fmi.movietracker.service.dto.UserMovieMappingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserDashboardCustomResource {

    public static final String MOVIE_STATUS_CODE_HISTORY = "HISTORY";
    public static final String MOVIE_STATUS_CODE_WISHLIST = "WISHLIST";

    private final Logger log = LoggerFactory.getLogger(UserDashboardCustomResource.class);

    private final UserMovieMappingService userMovieMappingService;

    public UserDashboardCustomResource(UserMovieMappingService userMovieMappingService) {
        this.userMovieMappingService = userMovieMappingService;
    }

    /**
     * GET  /user-dashboard : get all movies in history
     *
     * @return the ResponseEntity with status 200 (OK) and with body the userMovieMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-dashboard/history")
    public ResponseEntity<List<MovieDTO>> getUserMovieMappingFromHistoryForLoggedUser() {
        log.debug("REST request to get UserMovieMapping from history");
        List<MovieDTO> userMovieMappingDTOList =
            userMovieMappingService.findByCurrentLoginInHistory();
        return ResponseEntity.ok().body(userMovieMappingDTOList);
    }

    /**
     * GET  /user-dashboard : get all movies in wishlist
     *
     * @return the ResponseEntity with status 200 (OK) and with body the userMovieMappingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-dashboard/wishlist")
    public ResponseEntity<List<MovieDTO>> getUserMovieMappingFromWishlistForLoggedUser() {
        log.debug("REST request to get UserMovieMapping from wishlist");
        List<MovieDTO> userMovieMappingDTOList =
            userMovieMappingService.findByCurrentLoginInWishlist();
        return ResponseEntity.ok().body(userMovieMappingDTOList);
    }

}
