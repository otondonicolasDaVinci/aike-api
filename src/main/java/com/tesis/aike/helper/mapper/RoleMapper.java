package com.tesis.aike.helper.mapper;

import com.tesis.aike.model.dto.RoleDTO;
import com.tesis.aike.model.entity.RolesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTO(RolesEntity entity);
    RolesEntity toEntity(RoleDTO dto);
}