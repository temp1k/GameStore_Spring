package com.example.gamestorespring.repository;

import com.example.gamestorespring.entity.GameEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GameRepo extends CrudRepository<GameEntity, Long> {
    Set<GameEntity> findAll();
}
