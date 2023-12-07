package com.example.gamestorespring.controller;

import com.example.gamestorespring.exception.UserAlreadyExistException;
import com.example.gamestorespring.model.UserModel;
import com.example.gamestorespring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserAdminController {

    private UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model, @ModelAttribute("user") UserModel userForForm) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userForForm", userForForm);
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/users";
    }

    @PostMapping
    public String create(@ModelAttribute("user") UserModel user) {
        try{
            userService.create(user);
        }catch (UserAlreadyExistException e){
            return "error/402";
        }

        return "redirect:/users";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", userService.getAllRoles());
        return "admin/user";
    }

    @PatchMapping("/{id}")
    public String change(@ModelAttribute("user") UserModel user, @PathVariable("id") long id) {
        userService.changeUser(user, id);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
