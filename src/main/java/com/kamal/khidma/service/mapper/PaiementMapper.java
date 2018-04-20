package com.kamal.khidma.service.mapper;

import com.kamal.khidma.domain.*;
import com.kamal.khidma.service.dto.PaiementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Paiement and its DTO PaiementDTO.
 */
@Mapper(componentModel = "spring", uses = {EleveMapper.class})
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {

    @Mapping(source = "eleve.id", target = "eleveId")
    PaiementDTO toDto(Paiement paiement);

    @Mapping(source = "eleveId", target = "eleve")
    Paiement toEntity(PaiementDTO paiementDTO);

    default Paiement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paiement paiement = new Paiement();
        paiement.setId(id);
        return paiement;
    }
}
