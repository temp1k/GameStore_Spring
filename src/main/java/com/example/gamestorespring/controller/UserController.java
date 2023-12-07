package com.example.gamestorespring.controller;

import com.example.gamestorespring.entity.GameEntity;
import com.example.gamestorespring.model.UserModel;
import com.example.gamestorespring.service.CommentService;
import com.example.gamestorespring.service.GameService;
import com.example.gamestorespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/main")
    public String index(Model model){
        model.addAttribute("games", gameService.getAllGames());
        return "/main";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails currentUser){
        model.addAttribute("user", userService.getUserByLogin(currentUser.getUsername()));
        return "/user/profile";
    }

    @GetMapping("/basket")
    public String basket(@AuthenticationPrincipal UserDetails currentUser,
                                 Model model){
        UserModel user = userService.getUserByLogin(currentUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("games", gameService.getGamesInBasket(user.getLogin()));
        return "user/basket";
    }

    @PatchMapping("/profile/{id}")
    public String changeProfile(@ModelAttribute("user") UserModel user, @PathVariable("id") long id) {
        userService.changeUser(user, id);

        return "redirect:/user/profile";
    }

    @GetMapping("/game/{id}")
    public String showGame(@PathVariable("id") long id, Model model,
                           @AuthenticationPrincipal UserDetails currentUser) {
        GameEntity game = gameService.getGameByIdEntity(id);
        model.addAttribute("game", game);
        model.addAttribute("inBasket", gameService.checkGame(id, currentUser.getUsername()));
        return "user/game";
    }

    @PostMapping("/game/user/game/add")
    private String addGameToBasket(@RequestParam("id") long id,
                                  @AuthenticationPrincipal UserDetails currentUser) {
        gameService.addGameToBasketById(id, currentUser.getUsername());
        return "redirect:/user/game/"+id;
    }

    @PostMapping("/comment")
    private String writeComment(@RequestParam("id") long id,
                                @RequestParam("text") String text,
                                @AuthenticationPrincipal UserDetails currentUser) {
        commentService.writeComment(text, id, currentUser.getUsername());

        return "redirect:/user/game/"+id;
    }
}
