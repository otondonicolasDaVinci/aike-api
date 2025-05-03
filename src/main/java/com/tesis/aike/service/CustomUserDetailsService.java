package com.tesis.aike.security;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserDetails loadUserByUsername(String id) {
        UsersEntity u = usersRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new UsernameNotFoundException(ConstantValues.UserService.NOT_FOUND));
        return User.withUsername(String.valueOf(u.getId()))
                .password(u.getPassword())
                .roles("USER")
                .build();
    }
}
