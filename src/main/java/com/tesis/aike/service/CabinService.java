package com.tesis.aike.service;

import com.tesis.aike.model.dto.CabinDTO;

import java.util.List;

public interface CabinService {
    CabinDTO create(CabinDTO dto);

    CabinDTO update(Long id, CabinDTO dto);

    void delete(Long id);

    CabinDTO findById(Long id);

    List<CabinDTO> findAll();

    List<CabinDTO> findByAvailableTrue();

    List<CabinDTO> findByName(String name);

    List<CabinDTO> findByCapacity(int capacity);

}