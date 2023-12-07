package com.example.gamestorespring.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "developer")
public class DeveloperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public DeveloperEntity() {
    }

    public DeveloperEntity(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
