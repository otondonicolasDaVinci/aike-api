package com.tesis.aike.service;

import com.tesis.aike.model.dto.ReservationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReservationService {
    ReservationDTO create(ReservationDTO dto);
    ReservationDTO update(Long id, ReservationDTO dto);
    void delete(Long id);
    ReservationDTO findById(Long id);
    List<ReservationDTO> findByUserId(Long userId);
    List<ReservationDTO> findAll();
}
