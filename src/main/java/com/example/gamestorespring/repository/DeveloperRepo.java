package com.example.gamestorespring.repository;

import com.example.gamestorespring.entity.DeveloperEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface DeveloperRepo extends CrudRepository<DeveloperEntity, Long> {
    Set<DeveloperEntity> findAll();

    DeveloperEntity findByName(String name);
}
