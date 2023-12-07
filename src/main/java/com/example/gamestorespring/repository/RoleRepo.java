package com.example.gamestorespring.repository;

import com.example.gamestorespring.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepo extends CrudRepository<RoleEntity, Long> {
    List<RoleEntity> findAll();

    Optional<RoleEntity> findByRole(String roleUser);
}
