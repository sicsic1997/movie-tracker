package org.fmi.movietracker.service.custom.impl;

import io.github.jhipster.service.QueryService;
import org.fmi.movietracker.domain.*;
import org.fmi.movietracker.repository.MovieRepository;
import org.fmi.movietracker.service.dto.MovieDTO;
import org.fmi.movietracker.service.dto.MovieDashboardCriteria;
import org.fmi.movietracker.service.mapper.MovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MovieDashboardQueryService extends QueryService<Movie> {

    private final Logger log = LoggerFactory.getLogger(MovieDashboardQueryService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieMapper movieMapper;

    /**
     * Return a {@link List} of {@link MovieDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MovieDTO> findByCriteria(MovieDashboardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Movie> specification = createSpecification(criteria);
        return movieMapper.toDto(movieRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MovieDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MovieDTO> findByCriteria(MovieDashboardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Movie> specification = createSpecification(criteria);
        return movieRepository.findAll(specification, page)
            .map(movieMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MovieDashboardCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Movie> specification = createSpecification(criteria);
        return movieRepository.count(specification);
    }

    /**
     * Function to convert MovieDashboardCriteria to a {@link Specification}
     */
    private Specification<Movie> createSpecification(MovieDashboardCriteria criteria) {
        Specification<Movie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getFilter() != null && criteria.getFilter().getContains() != null) {
                specification = specification.and(getLikeSpecificationConcat(criteria.getFilter().getContains()));
            }
        }
        return specification;
    }

    private Specification<Movie> getLikeSpecificationConcat(String value) {
        return (Specification<Movie>) (root, query, builder) -> builder.like(
            builder.concat(root.get(Movie_.plot), builder
                    .concat("-", builder
                        .concat(root.get(Movie_.title), builder
                            .concat("-", root.join(Movie_.production).get(Production_.code))))),
            "%" + value + "%");
            
    }

}

