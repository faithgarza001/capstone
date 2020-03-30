package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.PostRepo;
import com.capstone.kitsune.repositories.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    private PostRepo postDao;
    private UserRepo userDao;

    public PostController(PostRepo postDao, UserRepo userDao){
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/dashboard/posts/create")
    public String showCreateForm(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedInUser != null) {
            model.addAttribute("post", new Post());
            return "posts/create";
        } else{
            return "redirect:/login";
        }
    }

    @PostMapping("/dashboard/posts/create")
    public String postNewPost(@RequestParam String title, @RequestParam String body) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post(title, body);
        postDao.save(post);
        return "redirect:/dashboard";
    }
}
