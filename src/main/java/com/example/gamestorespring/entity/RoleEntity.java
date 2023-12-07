package com.example.gamestorespring.entity;

import com.example.gamestorespring.model.RoleModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<UserEntity> user;

    public RoleEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<UserEntity> getUser() {
        return user;
    }

    public void setUser(Set<UserEntity> user) {
        this.user = user;
    }
}
