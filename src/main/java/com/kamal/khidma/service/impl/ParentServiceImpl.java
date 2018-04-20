package com.kamal.khidma.service.impl;

import com.kamal.khidma.service.ParentService;
import com.kamal.khidma.domain.Parent;
import com.kamal.khidma.repository.ParentRepository;
import com.kamal.khidma.service.dto.ParentDTO;
import com.kamal.khidma.service.mapper.ParentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Parent.
 */
@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final Logger log = LoggerFactory.getLogger(ParentServiceImpl.class);

    private final ParentRepository parentRepository;

    private final ParentMapper parentMapper;

    public ParentServiceImpl(ParentRepository parentRepository, ParentMapper parentMapper) {
        this.parentRepository = parentRepository;
        this.parentMapper = parentMapper;
    }

    /**
     * Save a parent.
     *
     * @param parentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParentDTO save(ParentDTO parentDTO) {
        log.debug("Request to save Parent : {}", parentDTO);
        Parent parent = parentMapper.toEntity(parentDTO);
        parent = parentRepository.save(parent);
        return parentMapper.toDto(parent);
    }

    /**
     * Get all the parents.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParentDTO> findAll() {
        log.debug("Request to get all Parents");
        return parentRepository.findAll().stream()
            .map(parentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one parent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ParentDTO findOne(Long id) {
        log.debug("Request to get Parent : {}", id);
        Parent parent = parentRepository.findOne(id);
        return parentMapper.toDto(parent);
    }

    /**
     * Delete the parent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parent : {}", id);
        parentRepository.delete(id);
    }
}
