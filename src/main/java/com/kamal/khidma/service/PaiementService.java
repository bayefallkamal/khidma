package com.kamal.khidma.service;

import com.kamal.khidma.service.dto.PaiementDTO;
import java.util.List;

/**
 * Service Interface for managing Paiement.
 */
public interface PaiementService {

    /**
     * Save a paiement.
     *
     * @param paiementDTO the entity to save
     * @return the persisted entity
     */
    PaiementDTO save(PaiementDTO paiementDTO);

    /**
     * Get all the paiements.
     *
     * @return the list of entities
     */
    List<PaiementDTO> findAll();

    /**
     * Get the "id" paiement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PaiementDTO findOne(Long id);

    /**
     * Delete the "id" paiement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
