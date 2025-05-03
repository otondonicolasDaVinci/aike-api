package com.tesis.aike.service;

import com.tesis.aike.model.dto.CabinDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CabinService {
    CabinDTO create(CabinDTO dto);

    CabinDTO update(Integer id, CabinDTO dto);

    void delete(Integer id);

    CabinDTO findById(Integer id);

    List<CabinDTO> findAll();

    List<CabinDTO> findByAvailableTrue();

    List<CabinDTO> findByName(String name);

    List<CabinDTO> findByCapacity(int capacity);

}