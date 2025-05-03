package com.tesis.aike.service.impl;

import com.tesis.aike.helper.mapper.CabinMapper;
import com.tesis.aike.model.dto.CabinDTO;
import com.tesis.aike.model.entity.CabinEntity;
import com.tesis.aike.repository.CabinRepository;
import com.tesis.aike.service.CabinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CabinServiceImpl implements CabinService {
    private final CabinRepository repository;
    private final CabinMapper mapper;

    @Autowired
    public CabinServiceImpl(CabinRepository repository, CabinMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CabinDTO create(CabinDTO dto) {
        CabinEntity entity = mapper.toEntity(dto);
        CabinEntity savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public CabinDTO update(Integer id, CabinDTO dto) {
        CabinEntity entity = repository.findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Cabin not found with id: " + id));
        entity.setName(dto.getName());
        entity.setCapacity(dto.getCapacity());
        entity.setDescription(dto.getDescription());
        entity.setAvailable(dto.getAvailable());
        CabinEntity updatedEntity = repository.save(entity);
        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id.intValue())) {
            throw new RuntimeException("Cabin not found with id: " + id);
        }
        repository.deleteById(id.intValue());
    }

    @Override
    public CabinDTO findById(Integer id) {
        CabinEntity entity = repository.findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Cabin not found with id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<CabinDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<CabinDTO> findByAvailableTrue() {
        return repository.findByAvailableTrue().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<CabinDTO> findByName(String name) {
        return null;
    }

    @Override
    public List<CabinDTO> findByCapacity(int capacity) {
        return null;
    }
}