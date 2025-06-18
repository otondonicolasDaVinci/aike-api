package com.tesis.aike.service;

import com.tesis.aike.helper.mapper.UserMapper;
import com.tesis.aike.model.dto.RoleDTO;
import com.tesis.aike.model.dto.UserDTO;
import com.tesis.aike.model.entity.RolesEntity;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.repository.RolesRepository;
import com.tesis.aike.repository.UsersRepository;
import com.tesis.aike.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private RolesRepository rolesRepository;

    private UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private UserServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new UserServiceImpl(usersRepository, rolesRepository, mapper, encoder);
    }

    @Test
    void createEncodesPasswordWithBCrypt() {
        UserDTO dto = new UserDTO();
        dto.setName("Test");
        dto.setEmail("test@example.com");
        dto.setDni("123");
        dto.setPassword("secret");
        dto.setRole(new RoleDTO(1L, "ADMIN"));

        when(usersRepository.save(any(UsersEntity.class))).thenAnswer(i -> i.getArgument(0));
        RolesEntity roleEntity = new RolesEntity();
        roleEntity.setId(1L);
        roleEntity.setName("ADMIN");
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(roleEntity));

        service.create(dto);

        ArgumentCaptor<UsersEntity> captor = ArgumentCaptor.forClass(UsersEntity.class);
        verify(usersRepository).save(captor.capture());

        UsersEntity saved = captor.getValue();
        assertNotEquals("secret", saved.getPassword());
        assertTrue(encoder.matches("secret", saved.getPassword()));
    }
}
