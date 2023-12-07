package com.example.gamestorespring.controller;

import com.example.gamestorespring.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {
    @Autowired
    private GameService gameService;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        model.addAttribute("role", getRoles());
        return "index";
    }

    @GetMapping("/main")
    public String main(Model model){
        model.addAttribute("role", getRoles());
        model.addAttribute("games", gameService.getAllGames());
        return "/main";
    }

    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok(getRoles());
    }

    private boolean isAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return true;
        } else {
            return false;
        }
    }

    private String getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return authorities.stream().collect(Collectors.toList()).get(0).getAuthority();
        }
        return "ROLE_GUEST";
    }
}
