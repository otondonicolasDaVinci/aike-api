package com.tesis.aike.service;

import com.tesis.aike.model.dto.ReservationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReservationService {
    ReservationDTO create(ReservationDTO dto);
    ReservationDTO update(Integer id, ReservationDTO dto);
    void delete(Integer id);
    ReservationDTO findById(Integer id);
    List<ReservationDTO> findByUserId(Integer userId);
    List<ReservationDTO> findAll();
}
