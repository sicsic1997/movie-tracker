package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.MovieStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MovieStatus and its DTO MovieStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MovieStatusMapper extends EntityMapper<MovieStatusDTO, MovieStatus> {



    default MovieStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieStatus movieStatus = new MovieStatus();
        movieStatus.setId(id);
        return movieStatus;
    }
}
