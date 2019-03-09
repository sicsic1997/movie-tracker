package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.MoviePeopleRoleMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MoviePeopleRoleMapping and its DTO MoviePeopleRoleMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, MovieMapper.class, RoleMapper.class})
public interface MoviePeopleRoleMappingMapper extends EntityMapper<MoviePeopleRoleMappingDTO, MoviePeopleRoleMapping> {

    @Mapping(source = "people.id", target = "peopleId")
    @Mapping(source = "people.name", target = "peopleName")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.code", target = "roleCode")
    MoviePeopleRoleMappingDTO toDto(MoviePeopleRoleMapping moviePeopleRoleMapping);

    @Mapping(source = "peopleId", target = "people")
    @Mapping(source = "movieId", target = "movie")
    @Mapping(source = "roleId", target = "role")
    MoviePeopleRoleMapping toEntity(MoviePeopleRoleMappingDTO moviePeopleRoleMappingDTO);

    default MoviePeopleRoleMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        MoviePeopleRoleMapping moviePeopleRoleMapping = new MoviePeopleRoleMapping();
        moviePeopleRoleMapping.setId(id);
        return moviePeopleRoleMapping;
    }
}
