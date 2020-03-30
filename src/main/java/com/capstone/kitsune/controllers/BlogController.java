package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.repositories.BlogRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class BlogController {
    private BlogRepo blogDao;

    public BlogController(BlogRepo blogDao){
        this.blogDao = blogDao;
    }

    @GetMapping("/dashboard/blogs/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Blog());
        return "blogs/create";
    }

    @PostMapping("/dashboard/blogs/create")
    public String postNewBlog(@RequestParam String blogTitle, @RequestParam String handle, @RequestParam Date dateCreated, @RequestParam List<Category> categories) {
        Blog blog = new Blog(blogTitle, handle, dateCreated, categories);
        blogDao.save(blog);
        return "redirect:/blogs";
    }
}
