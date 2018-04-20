package com.kamal.khidma.service.mapper;

import com.kamal.khidma.domain.*;
import com.kamal.khidma.service.dto.ParentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Parent and its DTO ParentDTO.
 */
@Mapper(componentModel = "spring", uses = {EleveMapper.class})
public interface ParentMapper extends EntityMapper<ParentDTO, Parent> {

    @Mapping(source = "eleve.id", target = "eleveId")
    ParentDTO toDto(Parent parent);

    @Mapping(source = "eleveId", target = "eleve")
    Parent toEntity(ParentDTO parentDTO);

    default Parent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parent parent = new Parent();
        parent.setId(id);
        return parent;
    }
}
