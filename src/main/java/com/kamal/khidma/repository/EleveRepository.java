package com.kamal.khidma.repository;

import com.kamal.khidma.domain.Eleve;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Eleve entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {

}
