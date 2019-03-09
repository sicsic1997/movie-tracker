package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.UserMovieMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserMovieMapping and its DTO UserMovieMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, MovieMapper.class, MovieStatusMapper.class})
public interface UserMovieMappingMapper extends EntityMapper<UserMovieMappingDTO, UserMovieMapping> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(source = "movieStatus.id", target = "movieStatusId")
    @Mapping(source = "movieStatus.code", target = "movieStatusCode")
    UserMovieMappingDTO toDto(UserMovieMapping userMovieMapping);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "movieId", target = "movie")
    @Mapping(source = "movieStatusId", target = "movieStatus")
    UserMovieMapping toEntity(UserMovieMappingDTO userMovieMappingDTO);

    default UserMovieMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserMovieMapping userMovieMapping = new UserMovieMapping();
        userMovieMapping.setId(id);
        return userMovieMapping;
    }
}
