package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.MovieLanguageMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MovieLanguageMapping and its DTO MovieLanguageMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {MovieMapper.class, LanguageMapper.class})
public interface MovieLanguageMappingMapper extends EntityMapper<MovieLanguageMappingDTO, MovieLanguageMapping> {

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(source = "language.id", target = "languageId")
    @Mapping(source = "language.code", target = "languageCode")
    MovieLanguageMappingDTO toDto(MovieLanguageMapping movieLanguageMapping);

    @Mapping(source = "movieId", target = "movie")
    @Mapping(source = "languageId", target = "language")
    MovieLanguageMapping toEntity(MovieLanguageMappingDTO movieLanguageMappingDTO);

    default MovieLanguageMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieLanguageMapping movieLanguageMapping = new MovieLanguageMapping();
        movieLanguageMapping.setId(id);
        return movieLanguageMapping;
    }
}
