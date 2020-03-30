package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.repositories.PostRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    private PostRepo postDao;

    public PostController(PostRepo postDao){
        this.postDao = postDao;
    }

    @GetMapping("/dashboard/create")
    public String showCreateForm(Model model) {
            model.addAttribute("post", new Post());
            return "posts/create";
    }

//    @PostMapping("/dashboard/create")
//    public String postNewPost(@RequestParam String title, @RequestParam String body) {
//        Post post = new Post(title, body, loggedInUser);
//        postDao.save(post);
//        return "redirect:/posts";
//    }
}
