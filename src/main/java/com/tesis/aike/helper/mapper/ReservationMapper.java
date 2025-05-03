package com.tesis.aike.helper.mapper;

import com.tesis.aike.model.dto.ReservationDTO;
import com.tesis.aike.model.entity.ReservationsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CabinMapper.class, UserMapper.class})
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "cabinId", target = "cabin.id")
    ReservationDTO toDTO(ReservationsEntity entity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cabin.id", target = "cabinId")
    ReservationsEntity toEntity(ReservationDTO dto);
}
