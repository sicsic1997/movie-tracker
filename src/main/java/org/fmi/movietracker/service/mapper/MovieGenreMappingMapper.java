package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.MovieGenreMappingDTO;

import org.mapstruct.*;

import java.util.Set;

/**
 * Mapper for the entity MovieGenreMapping and its DTO MovieGenreMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {GenreMapper.class, MovieMapper.class})
public interface MovieGenreMappingMapper extends EntityMapper<MovieGenreMappingDTO, MovieGenreMapping> {

    Set<MovieGenreMappingDTO> toDto(Set<MovieGenreMapping> set);

    @Mapping(source = "genre.id", target = "genreId")
    @Mapping(source = "genre.code", target = "genreCode")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "movieTitle")
    MovieGenreMappingDTO toDto(MovieGenreMapping movieGenreMapping);

    @Mapping(source = "genreId", target = "genre")
    @Mapping(source = "movieId", target = "movie")
    MovieGenreMapping toEntity(MovieGenreMappingDTO movieGenreMappingDTO);

    default MovieGenreMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieGenreMapping movieGenreMapping = new MovieGenreMapping();
        movieGenreMapping.setId(id);
        return movieGenreMapping;
    }
}
