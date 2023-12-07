package com.example.gamestorespring.repository;

import com.example.gamestorespring.entity.BasketEntity;
import org.springframework.data.repository.CrudRepository;

public interface BasketRepo extends CrudRepository<BasketEntity, Long> {
}
