package com.kamal.khidma.service.impl;

import com.kamal.khidma.service.EnseignantService;
import com.kamal.khidma.domain.Enseignant;
import com.kamal.khidma.repository.EnseignantRepository;
import com.kamal.khidma.service.dto.EnseignantDTO;
import com.kamal.khidma.service.mapper.EnseignantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Enseignant.
 */
@Service
@Transactional
public class EnseignantServiceImpl implements EnseignantService {

    private final Logger log = LoggerFactory.getLogger(EnseignantServiceImpl.class);

    private final EnseignantRepository enseignantRepository;

    private final EnseignantMapper enseignantMapper;

    public EnseignantServiceImpl(EnseignantRepository enseignantRepository, EnseignantMapper enseignantMapper) {
        this.enseignantRepository = enseignantRepository;
        this.enseignantMapper = enseignantMapper;
    }

    /**
     * Save a enseignant.
     *
     * @param enseignantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnseignantDTO save(EnseignantDTO enseignantDTO) {
        log.debug("Request to save Enseignant : {}", enseignantDTO);
        Enseignant enseignant = enseignantMapper.toEntity(enseignantDTO);
        enseignant = enseignantRepository.save(enseignant);
        return enseignantMapper.toDto(enseignant);
    }

    /**
     * Get all the enseignants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EnseignantDTO> findAll() {
        log.debug("Request to get all Enseignants");
        return enseignantRepository.findAllWithEagerRelationships().stream()
            .map(enseignantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one enseignant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EnseignantDTO findOne(Long id) {
        log.debug("Request to get Enseignant : {}", id);
        Enseignant enseignant = enseignantRepository.findOneWithEagerRelationships(id);
        return enseignantMapper.toDto(enseignant);
    }

    /**
     * Delete the enseignant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enseignant : {}", id);
        enseignantRepository.delete(id);
    }
}
