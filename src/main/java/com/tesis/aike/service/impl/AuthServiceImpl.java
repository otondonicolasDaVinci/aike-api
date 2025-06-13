package com.tesis.aike.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.tesis.aike.helper.mapper.UserMapper;
import com.tesis.aike.model.dto.RoleDTO;
import com.tesis.aike.model.dto.UserDTO;
import com.tesis.aike.model.entity.RolesEntity;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.repository.RolesRepository;
import com.tesis.aike.repository.UsersRepository;
import com.tesis.aike.security.JwtTokenUtil;
import com.tesis.aike.service.AuthService;
import com.tesis.aike.service.GoogleTokenVerifierService;
import com.tesis.aike.utils.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UsersRepository usersRepo;
    private final RolesRepository rolesRepo;
    private final JwtTokenUtil jwt;
    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final UserMapper userMapper;

    @Autowired
    public AuthServiceImpl(
            UsersRepository usersRepo,
            RolesRepository rolesRepo,
            JwtTokenUtil jwt,
            GoogleTokenVerifierService googleTokenVerifierService,
            UserMapper userMapper
    ) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.jwt = jwt;
        this.googleTokenVerifierService = googleTokenVerifierService;
        this.userMapper = userMapper;
    }

    @Override
    public Map<String, Object> login(String username, String rawPassword) {
        log.info("Iniciando login para el usuario: {}", username);

        UsersEntity userFound = usersRepo.findByName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!userFound.getPassword().equals(PasswordEncryptor.encryptPassword(rawPassword))) {
            log.warn("Intento de login fallido por contraseña incorrecta para usuario: {}", username);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        RolesEntity role = rolesRepo.findById(userFound.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rol de usuario no encontrado"));

        String roleName = role.getName().toUpperCase().startsWith("ADMIN") ? "ADMIN" : "CLIENT";
        String jwtToken = jwt.generate(userFound.getId(), roleName);

        log.info("Login exitoso para usuario: {}. Generando token.", username);
        return Map.of("token", jwtToken, "userId", userFound.getId());
    }

    @Override
    @Transactional
    public Map<String, Object> loginGoogle(String idToken) {
        log.info("Iniciando proceso de login con Google.");
        GoogleIdToken.Payload payload;
        try {
            payload = googleTokenVerifierService.verify(idToken);
            if (payload == null) {
                log.error("La verificación del token de Google ha fallado.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ID token inválido");
            }
        } catch (Exception e) {
            log.error("Excepción durante la verificación del token de Google.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al verificar el token de Google.");
        }

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        log.info("Token de Google verificado para el email: {}", email);

        UsersEntity user = usersRepo.findByEmail(email).orElseGet(() -> {
            log.info("Usuario con email {} no encontrado. Creando nuevo usuario.", email);
            try {
                RolesEntity role = rolesRepo.findByNameIgnoreCase("CLIENT")
                        .orElseThrow(() -> {
                            log.error("Error crítico: El rol 'CLIENT' no se encuentra en la BD.");
                            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rol CLIENT no encontrado");
                        });

                UserDTO dto = new UserDTO();
                dto.setEmail(email);
                dto.setName(name);
                dto.setPassword("from-google");
                dto.setDni("DNI Pendiente");
                dto.setRole(new RoleDTO(role.getId(), role.getName()));

                UsersEntity newUserEntity = userMapper.toEntity(dto);
                log.info("Guardando nuevo usuario...");
                return usersRepo.save(newUserEntity);

            } catch (Exception e) {
                log.error("Excepción al crear nuevo usuario para email: {}", email, e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo crear el nuevo usuario.");
            }
        });
        log.info("Usuario obtenido con ID: {}", user.getId());

        try {
            RolesEntity role = rolesRepo.findById(user.getRoleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rol del usuario no encontrado"));

            String finalRole = role.getName().toUpperCase().startsWith("ADMIN") ? "ADMIN" : "CLIENT";
            log.info("Generando token JWT para userId: {} con rol: {}", user.getId(), finalRole);

            String jwtToken = jwt.generate(user.getId(), finalRole);
            log.info("Login con Google completado exitosamente para: {}", email);

            return Map.of("token", jwtToken, "userId", user.getId(), "email", user.getEmail());

        } catch (Exception e) {
            log.error("Excepción al generar token JWT para usuario: {}", user.getId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo generar el token de sesión.");
        }
    }
}