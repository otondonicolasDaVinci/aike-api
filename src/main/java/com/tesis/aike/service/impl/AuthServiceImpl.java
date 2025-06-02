package com.tesis.aike.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.helper.mapper.UserMapper;
import com.tesis.aike.model.dto.UserDTO;
import com.tesis.aike.model.dto.RoleDTO;
import com.tesis.aike.model.entity.RolesEntity;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.repository.RolesRepository;
import com.tesis.aike.repository.UsersRepository;
import com.tesis.aike.security.JwtTokenUtil;
import com.tesis.aike.service.AuthService;
import com.tesis.aike.service.GoogleTokenVerifierService;
import com.tesis.aike.utils.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepo;
    private final RolesRepository rolesRepo;
    private final JwtTokenUtil jwt;
    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final UserMapper userMapper;

    @Autowired
    public AuthServiceImpl(UsersRepository usersRepo,
                           RolesRepository rolesRepo,
                           JwtTokenUtil jwt,
                           GoogleTokenVerifierService googleTokenVerifierService,
                           UserMapper userMapper) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.jwt = jwt;
        this.googleTokenVerifierService = googleTokenVerifierService;
        this.userMapper = userMapper;
    }

    @Override
    public String login(String user, String rawPassword) {
        UsersEntity userFound = usersRepo.findByName(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, ConstantValues.Security.LOGIN_FAILED));

        if (!userFound.getPassword().equals(PasswordEncryptor.encryptPassword(rawPassword))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ConstantValues.Security.LOGIN_FAILED);
        }

        RolesEntity role = rolesRepo.findById(userFound.getRoleId()).orElse(null);
        String roleNameDb = role == null ? "CLIENT" : role.getName().toUpperCase();
        String roleName = roleNameDb.startsWith("ADMIN") ? "ADMIN" : "CLIENT";

        return jwt.generate(userFound.getId(), roleName);
    }

    @Override
    public Map<String, Object> loginGoogle(String idToken) {
        GoogleIdToken.Payload payload = googleTokenVerifierService.verify(idToken);
        if (payload == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ID token inválido");

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        UsersEntity user = usersRepo.findByEmail(email).orElseGet(() -> {
            RolesEntity role = rolesRepo.findByNameIgnoreCase("CLIENT")
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rol CLIENT no encontrado"));

            UserDTO dto = new UserDTO();
            dto.setEmail(email);
            dto.setName(name);
            dto.setPassword("from-google");
            dto.setRole(new RoleDTO(role.getId(), role.getName()));

            return usersRepo.save(userMapper.toEntity(dto));
        });

        RolesEntity role = rolesRepo.findById(user.getRoleId()).orElse(null);
        String roleNameDb = role == null ? "CLIENT" : role.getName().toUpperCase();
        String roleName = roleNameDb.startsWith("ADMIN") ? "ADMIN" : "CLIENT";

        String jwtToken = jwt.generate(user.getId(), roleName);

        return Map.of("token", jwtToken, "userId", user.getId(), "email", user.getEmail());
    }
}
