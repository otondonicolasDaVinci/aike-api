package com.tesis.aike.service;

import com.tesis.aike.model.entity.CabinsEntity;
import com.tesis.aike.repository.CabinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabinsService {

    @Autowired
    private CabinsRepository cabinsRepository;

    public CabinsEntity guardarCabina(CabinsEntity cabina) {
        return cabinsRepository.save(cabina);
    }

    public CabinsEntity obtenerCabinaPorId(int id) {
        return cabinsRepository.findById(id).orElse(null);
    }

    public List<CabinsEntity> obtenerTodasLasCabinas() {
        return cabinsRepository.findAll();
    }

    public void eliminarCabina(int id) {
        cabinsRepository.deleteById(id);
    }

    // ... otros métodos que utilicen el repositorio

    public List<CabinsEntity> getAvailableCabins() {
        // Llama al método del repositorio que busca por available = true
        List<CabinsEntity> availableCabins = cabinsRepository.findByAvailableTrue();
        // Aquí podrías añadir lógica adicional si fuera necesario (DTO mapping, etc.)
        return availableCabins;
    }
}