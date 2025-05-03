package com.tesis.aike.helper.mapper;

import com.tesis.aike.model.dto.UserDTO;
import com.tesis.aike.model.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roleId", target = "role.id")
    UserDTO toDTO(UsersEntity entity);

    @Mapping(source = "role.id", target = "roleId")
    UsersEntity toEntity(UserDTO dto);
}
