package org.example.client.persistence.mappers;

import org.example.client.models.UserReq;
import org.example.client.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserReq userReq);
    UserReq entityToDto(User user);
}
