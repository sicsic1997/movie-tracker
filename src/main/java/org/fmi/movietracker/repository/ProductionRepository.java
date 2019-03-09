package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Production;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Production entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {

}
