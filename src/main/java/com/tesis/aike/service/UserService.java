package com.tesis.aike.service;

import com.tesis.aike.model.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDTO create(UserDTO dto);
    UserDTO update(Integer id, UserDTO dto);
    void delete(Integer id);
    UserDTO findById(Integer id);
    List<UserDTO> findAll();
    UserDTO findByEmail(String email);
    UserDTO findByDni(String dni);
}
