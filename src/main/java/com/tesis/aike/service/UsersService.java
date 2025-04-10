package com.tesis.aike.service; // Asegúrate de usar el paquete correcto para tus servicios

import com.tesis.aike.model.entity.UsersEntity; // Asegúrate de que la ruta a tu entidad sea correcta
import com.tesis.aike.repository.UsersRepository; // Asegúrate de que la ruta a tu repositorio sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<UsersEntity> obtenerTodosLosUsuarios() {
        return usersRepository.findAll();
    }

    public Optional<UsersEntity> obtenerUsuarioPorId(int id) {
        return usersRepository.findById(id);
    }

    public Optional<UsersEntity> obtenerUsuarioPorEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Optional<UsersEntity> obtenerUsuarioPorDni(String dni) {
        return usersRepository.findByDni(dni);
    }

    public List<UsersEntity> obtenerUsuariosPorRol(int roleId) {
        return usersRepository.findByRoleId(roleId);
    }

    public UsersEntity guardarUsuario(UsersEntity user) {
        return usersRepository.save(user);
    }

    public void eliminarUsuario(int id) {
        usersRepository.deleteById(id);
    }

    // Puedes agregar más métodos de lógica de negocio relacionados con los usuarios aquí
}