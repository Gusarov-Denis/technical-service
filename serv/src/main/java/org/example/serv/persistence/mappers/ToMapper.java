package org.example.serv.persistence.mappers;

import org.example.serv.models.ToReq;
import org.example.serv.persistence.entity.To;
import org.mapstruct.Mapper;

@Mapper
public interface ToMapper {
    To dtoToEntity(ToReq oilReq);
    ToReq entityToDto(To to);
}
