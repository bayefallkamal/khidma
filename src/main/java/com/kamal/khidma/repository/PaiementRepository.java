package com.kamal.khidma.repository;

import com.kamal.khidma.domain.Paiement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Paiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

}
