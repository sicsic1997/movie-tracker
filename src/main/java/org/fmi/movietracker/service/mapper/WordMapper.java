package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.WordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Word and its DTO WordDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WordMapper extends EntityMapper<WordDTO, Word> {



    default Word fromId(Long id) {
        if (id == null) {
            return null;
        }
        Word word = new Word();
        word.setId(id);
        return word;
    }
}
