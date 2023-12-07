package com.example.gamestorespring.config;

import com.example.gamestorespring.entity.UserEntity;
import com.example.gamestorespring.exception.UserAlreadyExistException;
import com.example.gamestorespring.model.UserModel;
import com.example.gamestorespring.repository.UserRepo;
import com.example.gamestorespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("reg")
    public String getReg(Model model,@ModelAttribute("user") UserModel user, String e){
        model.addAttribute("user", user);
        model.addAttribute("error", e);
        return "registration";
    }

    @PostMapping("reg")
    public String reg(Model model, @ModelAttribute("user") UserModel user) throws Exception {
        try {
            userService.regUser(user);
        } catch (UserAlreadyExistException e){
            return getReg(model, user, e.getMessage());
        }
        return "redirect:/login";
    }
}
