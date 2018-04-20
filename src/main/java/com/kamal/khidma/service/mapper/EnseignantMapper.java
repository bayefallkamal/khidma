package com.kamal.khidma.service.mapper;

import com.kamal.khidma.domain.*;
import com.kamal.khidma.service.dto.EnseignantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Enseignant and its DTO EnseignantDTO.
 */
@Mapper(componentModel = "spring", uses = {EleveMapper.class})
public interface EnseignantMapper extends EntityMapper<EnseignantDTO, Enseignant> {



    default Enseignant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enseignant enseignant = new Enseignant();
        enseignant.setId(id);
        return enseignant;
    }
}
