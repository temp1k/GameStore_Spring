package com.example.gamestorespring.service;

import com.example.gamestorespring.entity.BasketEntity;
import com.example.gamestorespring.entity.DeveloperEntity;
import com.example.gamestorespring.entity.GameEntity;
import com.example.gamestorespring.entity.UserEntity;
import com.example.gamestorespring.model.GameModel;
import com.example.gamestorespring.repository.BasketRepo;
import com.example.gamestorespring.repository.DeveloperRepo;
import com.example.gamestorespring.repository.GameRepo;
import com.example.gamestorespring.repository.UserRepo;
import com.example.gamestorespring.utils.ImageUtil;
import com.example.gamestorespring.utils.LoggerCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class GameService {

    private BasketRepo basketRepo;
    private GameRepo gameRepo;
    private DeveloperRepo devRepo;
    private UserRepo userRepo;

    @Autowired
    public GameService(GameRepo gameRepo, DeveloperRepo developerRepo, UserRepo userRepo, BasketRepo basketRepo) {
        this.gameRepo = gameRepo;
        this.devRepo = developerRepo;
        this.userRepo = userRepo;
        this.basketRepo = basketRepo;
    }

    public Set<GameEntity> getAllGames(){
        return gameRepo.findAll();
    }

    public Set<DeveloperEntity> getAllDevelopers() {
        return devRepo.findAll();
    }

    public void createGame(GameModel gameModel) {
        GameEntity new_game = new GameEntity();

        new_game = GameModelToGameEntity(gameModel, new_game);

        gameRepo.save(new_game);
    }

    public GameModel getGameById(long id) {
        GameEntity gameEntity = gameRepo.findById(id).get();

        GameModel game = new GameModel();
        game.setName(gameEntity.getName());
        game.setDescription(gameEntity.getDescription());
        game.setPrice(gameEntity.getPrice());
        game.setId(gameEntity.getId());
        game.setDeveloper(gameEntity.getDeveloper().getName());
        game.setNameImage(gameEntity.getImage());
        game.setComments(gameEntity.getComments());

        return game;
    }

    public GameEntity getGameByIdEntity(long id) {
        GameEntity gameEntity = gameRepo.findById(id)
                .orElse(null);

        return gameEntity;
    }

    public void changeGame(long id, GameModel gameModel) {
        GameEntity gameEntity = gameRepo.findById(id).get();

        gameEntity = GameModelToGameEntity(gameModel, gameEntity);

        gameRepo.save(gameEntity);
    }

    private GameEntity GameModelToGameEntity(GameModel gameModel, GameEntity gameEntity) {
        gameEntity.setName(gameModel.getName());
        gameEntity.setDescription(gameModel.getDescription());
        gameEntity.setPrice(gameModel.getPrice());
        DeveloperEntity dev = devRepo.findByName(gameModel.getDeveloper());
        if (dev == null) {
            dev = new DeveloperEntity(gameModel.getDeveloper());
            devRepo.save(dev);
        }
        gameEntity.setDeveloper(dev);

        if (Objects.requireNonNull(gameModel.getImage().getOriginalFilename()).isEmpty()){
            if(gameEntity.getImage() == null) {
                gameEntity.setImage(ImageUtil.getDefaultImageGame());
            }
        }
        else{
            try {
                gameEntity.setImage(ImageUtil.saveImage(gameModel.getImage()));
            } catch (IOException e){
                gameEntity.setImage(ImageUtil.getDefaultImageGame());
            }
        }

        return gameEntity;
    }

    public void deleteGameById(long id) {
        gameRepo.deleteById(id);
    }

    public void addGameToBasketById(long id, String login) {
        UserEntity user = userRepo.findByLogin(login).get();
        BasketEntity basket = user.getBasket();
        basket.getGames().add(gameRepo.findById(id).get());

        basketRepo.save(basket);

    }

    public boolean checkGame(long id, String login) {
        UserEntity user = userRepo.findByLogin(login).get();
        return user.getBasket().getGames().stream().anyMatch(g -> g.getId() == id);
    }

    public Set<GameEntity> getGamesInBasket(String login) {
        UserEntity user = userRepo.findByLogin(login).get();
        return user.getBasket().getGames();
    }
}
