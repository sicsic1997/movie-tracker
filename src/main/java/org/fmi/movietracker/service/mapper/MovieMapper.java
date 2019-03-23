package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.MovieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Movie and its DTO MovieDTO.
 */
@Mapper(componentModel = "spring", uses = {
    RatedMapper.class,
    ProductionMapper.class,
    MovieLanguageMappingMapper.class,
    MovieGenreMappingMapper.class,
    MoviePeopleRoleMappingMapper.class
})
public interface MovieMapper extends EntityMapper<MovieDTO, Movie> {

    @Mapping(source = "rated.id", target = "ratedId")
    @Mapping(source = "rated.code", target = "ratedCode")
    @Mapping(source = "production.id", target = "productionId")
    @Mapping(source = "production.code", target = "productionCode")
    @Mapping(source = "genreLists", target = "genreLists")
    MovieDTO toDto(Movie movie);

    @Mapping(source = "ratedId", target = "rated")
    @Mapping(source = "productionId", target = "production")
    Movie toEntity(MovieDTO movieDTO);

    default Movie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setId(id);
        return movie;
    }
}
