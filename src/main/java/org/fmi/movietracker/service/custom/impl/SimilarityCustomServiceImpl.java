package org.fmi.movietracker.service.custom.impl;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.User;
import org.fmi.movietracker.repository.SimilarityRepository;
import org.fmi.movietracker.service.custom.SimilarityCustomService;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.fmi.movietracker.service.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;



@Service
public class SimilarityCustomServiceImpl implements SimilarityCustomService {

    @Autowired
    private SimilarityRepository similarityRepository;

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public Page<MovieDTO> getSuggestionsForMovie(Movie movie, Pageable pageable) {
        return similarityRepository.getSuggestionsForMovie(movie, pageable).map(p -> movieMapper.toDto(p));
    }

    @Override
    public Page<MovieDTO> getSuggestionsForMovieAndUser(Movie movie, User user, Pageable pageable) {
        return similarityRepository.getSuggestionsForMovieAndUser(movie, user, pageable).map(p -> movieMapper.toDto(p));
    }

}
