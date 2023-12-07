package com.example.gamestorespring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;
    private String description;
    @Min(0)
    private double price;

    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "developer_id", nullable = true)
    @JsonIgnore
    private DeveloperEntity developer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game", fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "games", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BasketEntity> baskets;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<BasketEntity> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<BasketEntity> baskets) {
        this.baskets = baskets;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public DeveloperEntity getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperEntity developer) {
        this.developer = developer;
    }

    public GameEntity() {
    }
}
