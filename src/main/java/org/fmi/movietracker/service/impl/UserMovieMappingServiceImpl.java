package org.fmi.movietracker.service.impl;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.MovieStatus;
import org.fmi.movietracker.domain.User;
import org.fmi.movietracker.repository.MovieRepository;
import org.fmi.movietracker.repository.MovieStatusRepository;
import org.fmi.movietracker.repository.UserRepository;
import org.fmi.movietracker.security.SecurityUtils;
import org.fmi.movietracker.service.UserMovieMappingService;
import org.fmi.movietracker.domain.UserMovieMapping;
import org.fmi.movietracker.repository.UserMovieMappingRepository;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.fmi.movietracker.service.dto.UserMovieMappingDTO;
import org.fmi.movietracker.service.mapper.MovieMapper;
import org.fmi.movietracker.service.mapper.UserMovieMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserMovieMapping.
 */
@Service
@Transactional
public class UserMovieMappingServiceImpl implements UserMovieMappingService {

    private final Logger log = LoggerFactory.getLogger(UserMovieMappingServiceImpl.class);

    private final UserMovieMappingRepository userMovieMappingRepository;

    private final UserMovieMappingMapper userMovieMappingMapper;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private final MovieStatusRepository movieStatusRepository;

    private final MovieMapper movieMapper;

    public UserMovieMappingServiceImpl(UserMovieMappingRepository userMovieMappingRepository,
                                       UserMovieMappingMapper userMovieMappingMapper,
                                       UserRepository userRepository,
                                       MovieRepository movieRepository,
                                       MovieStatusRepository movieStatusRepository,
                                       MovieMapper movieMapper) {
        this.userMovieMappingRepository = userMovieMappingRepository;
        this.userMovieMappingMapper = userMovieMappingMapper;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.movieStatusRepository = movieStatusRepository;
        this.movieMapper = movieMapper;
    }

    /**
     * Save a userMovieMapping.
     *
     * @param userMovieMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserMovieMappingDTO save(UserMovieMappingDTO userMovieMappingDTO) {
        log.debug("Request to save UserMovieMapping : {}", userMovieMappingDTO);
        UserMovieMapping userMovieMapping = userMovieMappingMapper.toEntity(userMovieMappingDTO);
        userMovieMapping = userMovieMappingRepository.save(userMovieMapping);
        return userMovieMappingMapper.toDto(userMovieMapping);
    }

    /**
     * Get all the userMovieMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserMovieMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserMovieMappings");
        return userMovieMappingRepository.findAll(pageable)
            .map(userMovieMappingMapper::toDto);
    }


    /**
     * Get one userMovieMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserMovieMappingDTO> findOne(Long id) {
        log.debug("Request to get UserMovieMapping : {}", id);
        return userMovieMappingRepository.findById(id)
            .map(userMovieMappingMapper::toDto);
    }

    /**
     * Delete the userMovieMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserMovieMapping : {}", id);        userMovieMappingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<UserMovieMappingDTO> findMappingForMovieAndCurrentLogin(Long movieId) {
        return userMovieMappingRepository.findByUserIsCurrentUserAndMovieId(movieId)
            .stream().map(userMovieMappingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserMovieMappingDTO> findByCurrentLogin() {
        return userMovieMappingRepository.findByUserIsCurrentUser()
            .stream().map(userMovieMappingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByMovieIdAndLoggedUser(Long movieId, String movieStatusCode) {
        Optional<UserMovieMapping> userMovieMappingOptional =
            userMovieMappingRepository.findByUser_LoginAndMovie_IdAndMovieStatus_Code(SecurityUtils.getCurrentUserLogin().get(), movieId, movieStatusCode);
        userMovieMappingOptional.ifPresent(userMovieMapping -> delete(userMovieMapping.getId()));
    }

    @Override
    @Transactional
    public void createByMovieIdAndLoggedUser(Long movieId, String movieStatusCode) {
        String userLogin = SecurityUtils.getCurrentUserLogin().get();
        Optional<User> userOptional = userRepository.findOneByLogin(userLogin);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Movie> movieOptional = movieRepository.findById(movieId);
            if(movieOptional.isPresent()) {
                Movie movie = movieOptional.get();
                Optional<MovieStatus> movieStatusOptional = movieStatusRepository.getByCode(movieStatusCode);
                if(movieStatusOptional.isPresent()) {
                    MovieStatus movieStatus = movieStatusOptional.get();
                    UserMovieMapping umm = new UserMovieMapping();
                    umm.setMovie(movie);
                    umm.setUser(user);
                    umm.setMovieStatus(movieStatus);
                    userMovieMappingRepository.save(umm);
                }
            }
        }
    }

    @Override
    public List<MovieDTO> findByCurrentLoginInHistory() {
        return userMovieMappingRepository
            .findMoviesInHistoryByUserIsCurrentUser()
            .stream()
            .map(movieMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> findByCurrentLoginInWishlist() {
        return userMovieMappingRepository
            .findMoviesInWIshlistByUserIsCurrentUser()
            .stream()
            .map(movieMapper::toDto)
            .collect(Collectors.toList());
    }

}
