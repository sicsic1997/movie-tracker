package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.RatedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rated and its DTO RatedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RatedMapper extends EntityMapper<RatedDTO, Rated> {



    default Rated fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rated rated = new Rated();
        rated.setId(id);
        return rated;
    }
}
