package com.example.gamestorespring.controller;

import com.example.gamestorespring.model.GameModel;
import com.example.gamestorespring.service.GameService;
import com.example.gamestorespring.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/games")
public class GameAdminController {

    private GameService gameService;

    @Autowired
    public GameAdminController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    private String index(@ModelAttribute("gameModal") GameModel gameModel, Model model) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("developers", gameService.getAllDevelopers());
        return "admin/games";
    }

    @PostMapping
    private String create(@ModelAttribute("gameModel") GameModel gameModel) {
        gameService.createGame(gameModel);

        return "redirect:/games";
    }

    @GetMapping("show/{id}")
    private String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("gameModel", gameService.getGameById(id));
        model.addAttribute("developers", gameService.getAllDevelopers());
        return "admin/game";
    }

    @PatchMapping("/{id}")
    private String change(@PathVariable("id") long id, @ModelAttribute("gameModel") GameModel gameModel) {
        gameService.changeGame(id, gameModel);

        return "redirect:/games";
    }

    @DeleteMapping("/{id}")
    private String delete(@PathVariable("id") long id) {
        gameService.deleteGameById(id);

        return "redirect:/games";
    }

    @PostMapping("/test")
    private ResponseEntity test(@RequestParam("file") MultipartFile file) {
        String uploadDir = "src/main/resources/static/img";
        File uploadDirPath = new File(uploadDir);

        if (!uploadDirPath.exists()) {
            uploadDirPath.mkdirs();
        }

        try {
            Path filePath = Paths.get(uploadDir + "/" + ImageUtil.generateRandomName(file.getContentType()));
            Files.write(filePath, file.getBytes());
            return ResponseEntity.ok(file.getContentType());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Файл не загружен");
        }
    }
}
