package com.example.gamestorespring.repository;

import com.example.gamestorespring.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    Set<UserEntity> findAll();

    Optional<UserEntity> findByLogin(String login);
}
