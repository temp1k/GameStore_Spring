package com.example.gamestorespring.repository;

import com.example.gamestorespring.entity.CommentEntity;
import com.example.gamestorespring.entity.GameEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CommentRepo extends CrudRepository<CommentEntity, Long> {
    Set<CommentEntity> findAll();

    Set<CommentEntity> findAllByGame(GameEntity game);
}
