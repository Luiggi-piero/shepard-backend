package com.example.skilllinkbackend.features.role.model;

import com.example.skilllinkbackend.features.usuario.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Table(name = "roles")
@Entity(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING) // Almacena el enum como String en la bd
    private RolesEnum name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    private boolean enabled = true;

    public Role() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RolesEnum getName() {
        return name;
    }

    public void setName(RolesEnum name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
