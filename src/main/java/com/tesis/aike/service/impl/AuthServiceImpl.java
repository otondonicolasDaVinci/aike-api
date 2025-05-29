package com.tesis.aike.service.impl;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.model.entity.RolesEntity;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.repository.RolesRepository;
import com.tesis.aike.repository.UsersRepository;
import com.tesis.aike.security.JwtTokenUtil;
import com.tesis.aike.service.AuthService;
import com.tesis.aike.utils.PasswordEncryptor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepo;
    private final RolesRepository rolesRepo;
    private final JwtTokenUtil jwt;

    public AuthServiceImpl(UsersRepository usersRepo,
                           RolesRepository rolesRepo,
                           JwtTokenUtil jwt) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.jwt = jwt;
    }

    @Override
    public String login(String user, String rawPassword) {
       UsersEntity userFound = usersRepo.findByName(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, ConstantValues.Security.LOGIN_FAILED));

        if (!userFound.getPassword().equals(PasswordEncryptor.encryptPassword(rawPassword))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ConstantValues.Security.LOGIN_FAILED);
        }

        RolesEntity role = rolesRepo.findById(Math.toIntExact(userFound.getRoleId())).orElse(null);
        String roleNameDb = role == null ? "CLIENT" : role.getName().toUpperCase();
        String roleName = roleNameDb.startsWith("ADMIN") ? "ADMIN" : "CLIENT";

        return jwt.generate(userFound.getId(), roleName);
    }
}
