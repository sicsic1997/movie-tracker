package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.ProductionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Production and its DTO ProductionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductionMapper extends EntityMapper<ProductionDTO, Production> {



    default Production fromId(Long id) {
        if (id == null) {
            return null;
        }
        Production production = new Production();
        production.setId(id);
        return production;
    }
}
