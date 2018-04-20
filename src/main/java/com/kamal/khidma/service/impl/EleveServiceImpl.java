package com.kamal.khidma.service.impl;

import com.kamal.khidma.service.EleveService;
import com.kamal.khidma.domain.Eleve;
import com.kamal.khidma.repository.EleveRepository;
import com.kamal.khidma.service.dto.EleveDTO;
import com.kamal.khidma.service.mapper.EleveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Eleve.
 */
@Service
@Transactional
public class EleveServiceImpl implements EleveService {

    private final Logger log = LoggerFactory.getLogger(EleveServiceImpl.class);

    private final EleveRepository eleveRepository;

    private final EleveMapper eleveMapper;

    public EleveServiceImpl(EleveRepository eleveRepository, EleveMapper eleveMapper) {
        this.eleveRepository = eleveRepository;
        this.eleveMapper = eleveMapper;
    }

    /**
     * Save a eleve.
     *
     * @param eleveDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EleveDTO save(EleveDTO eleveDTO) {
        log.debug("Request to save Eleve : {}", eleveDTO);
        Eleve eleve = eleveMapper.toEntity(eleveDTO);
        eleve = eleveRepository.save(eleve);
        return eleveMapper.toDto(eleve);
    }

    /**
     * Get all the eleves.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EleveDTO> findAll() {
        log.debug("Request to get all Eleves");
        return eleveRepository.findAll().stream()
            .map(eleveMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eleve by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EleveDTO findOne(Long id) {
        log.debug("Request to get Eleve : {}", id);
        Eleve eleve = eleveRepository.findOne(id);
        return eleveMapper.toDto(eleve);
    }

    /**
     * Delete the eleve by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eleve : {}", id);
        eleveRepository.delete(id);
    }
}
