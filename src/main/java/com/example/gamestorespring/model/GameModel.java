package com.example.gamestorespring.model;

import com.example.gamestorespring.entity.CommentEntity;
import com.example.gamestorespring.entity.DeveloperEntity;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private long id;

    private String name;
    private String description;
    @Min(0)
    private double price;
    private String developer;

    List<CommentEntity> comments = new ArrayList<>();

    private MultipartFile image;
    private String nameImage;

    public GameModel() {
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
