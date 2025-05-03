package com.tesis.aike.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String dni;
    private String password;
    private RoleDTO role;

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDni() { return dni; }
    public String getPassword() { return password; }
    public RoleDTO getRole() { return role; }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setDni(String dni) { this.dni = dni; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(RoleDTO role) { this.role = role; }
}
