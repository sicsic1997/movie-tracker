package org.fmi.movietracker.service.custom.impl;

import org.fmi.movietracker.domain.Movie;
import org.fmi.movietracker.domain.User;
import org.fmi.movietracker.repository.SimilarityRepository;
import org.fmi.movietracker.service.custom.SimilarityCustomService;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.fmi.movietracker.service.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SimilarityCustomServiceImpl implements SimilarityCustomService {

    public static int NUMBER_OF_SUGGESTIONS = 12;

    @Autowired
    private SimilarityRepository similarityRepository;

    @Autowired
    private MovieMapper movieMapper;

    @Override
    @Transactional
    public List<MovieDTO> getSuggestionsForMovie(Movie movie) {
        return similarityRepository.getSuggestionsForMovie(movie).stream()
        .limit(NUMBER_OF_SUGGESTIONS).map(p -> movieMapper.toDto(p)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MovieDTO> getSuggestionsForUser(User user) {
        return similarityRepository.getSuggestionsUser(user).stream()
            .distinct()
            .limit(NUMBER_OF_SUGGESTIONS).map(p -> movieMapper.toDto(p)).collect(Collectors.toList());
    }

}
