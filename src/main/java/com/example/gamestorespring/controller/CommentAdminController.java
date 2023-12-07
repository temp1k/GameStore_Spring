package com.example.gamestorespring.controller;

import com.example.gamestorespring.entity.CommentEntity;
import com.example.gamestorespring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        return "admin/comments";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id) {
        commentService.deleteById(id);
        return "redirect:/comments";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("comment", commentService.getCommentById(id));
        return "admin/comment";
    }

}
