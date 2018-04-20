package com.kamal.khidma.service.mapper;

import com.kamal.khidma.domain.*;
import com.kamal.khidma.service.dto.EleveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Eleve and its DTO EleveDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EleveMapper extends EntityMapper<EleveDTO, Eleve> {


    @Mapping(target = "parents", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    @Mapping(target = "enseignants", ignore = true)
    Eleve toEntity(EleveDTO eleveDTO);

    default Eleve fromId(Long id) {
        if (id == null) {
            return null;
        }
        Eleve eleve = new Eleve();
        eleve.setId(id);
        return eleve;
    }
}
