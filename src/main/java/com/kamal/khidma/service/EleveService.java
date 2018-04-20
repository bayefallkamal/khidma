package com.kamal.khidma.service;

import com.kamal.khidma.service.dto.EleveDTO;
import java.util.List;

/**
 * Service Interface for managing Eleve.
 */
public interface EleveService {

    /**
     * Save a eleve.
     *
     * @param eleveDTO the entity to save
     * @return the persisted entity
     */
    EleveDTO save(EleveDTO eleveDTO);

    /**
     * Get all the eleves.
     *
     * @return the list of entities
     */
    List<EleveDTO> findAll();

    /**
     * Get the "id" eleve.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EleveDTO findOne(Long id);

    /**
     * Delete the "id" eleve.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
