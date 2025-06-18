package com.tesis.aike.service.impl;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.helper.mapper.UserMapper;
import com.tesis.aike.model.dto.RoleDTO;
import com.tesis.aike.model.dto.UserDTO;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.repository.RolesRepository;
import com.tesis.aike.repository.UsersRepository;
import com.tesis.aike.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepo;
    private final RolesRepository rolesRepo;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepo,
                           RolesRepository rolesRepo,
                           UserMapper mapper,
                           BCryptPasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    private UserDTO toDTOWithRoleName(UsersEntity entity) {
        UserDTO dto = mapper.toDTO(entity);
        rolesRepo.findById((long) entity.getRoleId().intValue()).ifPresent(role -> {
            RoleDTO r = new RoleDTO();
            r.setId(role.getId());
            r.setName(role.getName());
            dto.setRole(r);
        });
        return dto;
    }

    public UserDTO create(UserDTO in) {
        in.setPassword(passwordEncoder.encode(in.getPassword()));
        UsersEntity saved = usersRepo.save(mapper.toEntity(in));
        return toDTOWithRoleName(saved);
    }

    public UserDTO update(Integer id, UserDTO in) {
        UsersEntity e = usersRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantValues.UserService.NOT_FOUND));
        e.setName(in.getName());
        e.setEmail(in.getEmail());
        e.setDni(in.getDni());
        e.setRoleId(in.getRole().getId());
        if (in.getPassword() != null && !in.getPassword().isBlank())
            e.setPassword(passwordEncoder.encode(in.getPassword()));
        return toDTOWithRoleName(e);
    }

    public UserDTO findById(Integer id) {
        return toDTOWithRoleName(usersRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantValues.UserService.NOT_FOUND)));
    }

    public List<UserDTO> findAll() {
        return usersRepo.findAll().stream().map(this::toDTOWithRoleName).toList();
    }

    public UserDTO findByEmail(String email) {
        return toDTOWithRoleName(usersRepo.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantValues.UserService.NOT_FOUND)));
    }

    public UserDTO findByDni(String dni) {
        return toDTOWithRoleName(usersRepo.findByDni(dni).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ConstantValues.UserService.NOT_FOUND)));
    }

    public void delete(Integer id) {
        usersRepo.deleteById(id);
    }
}
