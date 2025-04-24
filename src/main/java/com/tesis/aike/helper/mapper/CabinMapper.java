package com.tesis.aike.helper.mapper;

import com.tesis.aike.model.dto.CabinDTO;
import com.tesis.aike.model.entity.CabinEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CabinMapper {
    CabinMapper INSTANCE = Mappers.getMapper(CabinMapper.class);

    CabinEntity toEntity(CabinDTO cabinDTO);
    CabinDTO toDTO(CabinEntity cabinEntity);
}
