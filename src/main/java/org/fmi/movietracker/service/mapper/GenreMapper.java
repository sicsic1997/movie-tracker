package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.GenreDTO;

import org.fmi.movietracker.service.dto.MovieDTO;
import org.mapstruct.*;

import java.util.Set;

/**
 * Mapper for the entity Genre and its DTO GenreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenreMapper extends EntityMapper<GenreDTO, Genre> {

    Set<GenreDTO> toDto(Set<Genre> genreLists);

    default Genre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}
