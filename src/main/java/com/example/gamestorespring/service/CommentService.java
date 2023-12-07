package com.example.gamestorespring.service;

import com.example.gamestorespring.entity.CommentEntity;
import com.example.gamestorespring.entity.GameEntity;
import com.example.gamestorespring.entity.UserEntity;
import com.example.gamestorespring.repository.CommentRepo;
import com.example.gamestorespring.repository.GameRepo;
import com.example.gamestorespring.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentService {

    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private GameRepo gameRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Set<CommentEntity> getAllComments() {
        return commentRepo.findAll();
    }

    public void deleteById(long id) {
        commentRepo.deleteById(id);
    }

    public CommentEntity getCommentById(long id) {
        return commentRepo.findById(id).get();
    }

    public CommentEntity writeComment(String text, long gameId, String login) {
        UserEntity user = userRepo.findByLogin(login).orElse(null);
        GameEntity game = gameRepo.findById(gameId).orElse(null);
        if (user == null || game == null){
            return null;
        }
        CommentEntity comment = new CommentEntity();
        comment.setInfo(text);
        comment.setGame(game);
        comment.setUser(user);

        return commentRepo.save(comment);
    }

    public Set<CommentEntity> getCommentsByGame(GameEntity game) {
        return commentRepo.findAllByGame(game);
    }
}
