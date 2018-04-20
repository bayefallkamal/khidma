package com.kamal.khidma.service;

import com.kamal.khidma.service.dto.EnseignantDTO;
import java.util.List;

/**
 * Service Interface for managing Enseignant.
 */
public interface EnseignantService {

    /**
     * Save a enseignant.
     *
     * @param enseignantDTO the entity to save
     * @return the persisted entity
     */
    EnseignantDTO save(EnseignantDTO enseignantDTO);

    /**
     * Get all the enseignants.
     *
     * @return the list of entities
     */
    List<EnseignantDTO> findAll();

    /**
     * Get the "id" enseignant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EnseignantDTO findOne(Long id);

    /**
     * Delete the "id" enseignant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
