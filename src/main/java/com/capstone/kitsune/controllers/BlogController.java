package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.repositories.BlogRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {
    private BlogRepo blogDao;

    public BlogController(BlogRepo blogDao){
        this.blogDao = blogDao;
    }

    @GetMapping("/dashboard/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Blog());
        return "blogs/create";
    }

    @PostMapping("/dashboard/create")
    public String postNewBlog(@RequestParam String title, @RequestParam String body) {
        Blog blog = new Blog(title, body, loggedInUser);
        blogDao.save(blog);
        return "redirect:/blogs";
    }
}
