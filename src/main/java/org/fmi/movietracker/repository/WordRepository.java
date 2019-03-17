package org.fmi.movietracker.repository;

import org.fmi.movietracker.domain.Word;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Word entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

}
