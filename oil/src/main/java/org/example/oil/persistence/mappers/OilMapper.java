package org.example.oil.persistence.mappers;

import org.example.oil.models.OilReq;
import org.example.oil.persistence.entity.Oil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OilMapper {
    Oil dtoToEntity(OilReq oilReq);
    OilReq entityToDto(Oil oil);
}
