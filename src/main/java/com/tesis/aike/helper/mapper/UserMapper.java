package com.tesis.aike.helper.mapper;

import com.tesis.aike.model.dto.UserDTO;
import com.tesis.aike.model.dto.RoleDTO; // Mantenemos este import para claridad en esta interfaz
import com.tesis.aike.model.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        imports = { RoleDTO.class }
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "role", expression = "java(entity.getRoleId() != null ? new com.tesis.aike.model.dto.RoleDTO(entity.getRoleId(), null) : null)")
    UserDTO toDTO(UsersEntity entity);

    @Mapping(target = "roleId", expression = "java(dto.getRole() != null ? dto.getRole().getId() : null)")
    UsersEntity toEntity(UserDTO dto);
}