package org.fmi.movietracker.service.mapper;

import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.service.dto.SimilarityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Similarity and its DTO SimilarityDTO.
 */
@Mapper(componentModel = "spring", uses = {MovieMapper.class})
public interface SimilarityMapper extends EntityMapper<SimilarityDTO, Similarity> {

    @Mapping(source = "movieA.id", target = "movieAId")
    @Mapping(source = "movieB.id", target = "movieBId")
    SimilarityDTO toDto(Similarity similarity);

    @Mapping(source = "movieAId", target = "movieA")
    @Mapping(source = "movieBId", target = "movieB")
    Similarity toEntity(SimilarityDTO similarityDTO);

    default Similarity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Similarity similarity = new Similarity();
        similarity.setId(id);
        return similarity;
    }
}
