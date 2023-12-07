package com.example.gamestorespring.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "baskets")
public class BasketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "baskets_games",
            joinColumns = { @JoinColumn(name = "basket_id") },
            inverseJoinColumns = { @JoinColumn(name = "game_id") }
    )
    private Set<GameEntity> games = new HashSet<>();

    public BasketEntity() {
    }

    public Set<GameEntity> getGames() {
        return games;
    }

    public void setGames(Set<GameEntity> games) {
        this.games = games;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
