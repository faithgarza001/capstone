package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.BlogRepo;
import com.capstone.kitsune.repositories.PostRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
    public class BlogController {
        private BlogRepo blogDao;

    public BlogController(BlogRepo blogDao) {
        this.blogDao = blogDao;
    }

        //Create form for a blogs
        @GetMapping("/dashboard/blogs/create")
        public String showCreateBlogForm(Model model) {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (loggedInUser != null) {
                model.addAttribute("blog", new Blog());
                return "blogs/create";
            } else {
                return "redirect:/login";
            }
        }

//        //Saving a new blog to the database
//        @PostMapping("/dashboard/blogs/create")
//        public String postNewBlog(@RequestParam String blogTitle, @RequestParam String handle) {
//        if (handle == null){
//
//            handle = "hardCodedHandle";
//        }
//            Blog blog = new Blog(blogTitle, handle);
//            blogDao.save(blog);
//            return "redirect:blogs/index";
//        }

        //Saving a new blog to the database
        @PostMapping("/blogs/create")
        public String postNewBlog(@RequestParam String blogTitle, @RequestParam String handle) {
            Blog newBlog = new Blog();
            newBlog.setBlogTitle(blogTitle);
            if (handle == null){
                handle = "hardCodedHandle";
            }
            newBlog.setHandle(handle);
            User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            newBlog.setUser(loggedIn);
            blogDao.save(newBlog);
            return "blogs/index";
        }

        //Editing a blog form
        @GetMapping("/dashboard/blogs/{id}/edit")
        public String editBlogForm(@PathVariable long id, Model model) {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (loggedInUser != null) {
                Blog blog = blogDao.getOne(id);
                model.addAttribute("blog", blog);
                return "blogs/edit";
            } else {
                return "redirect:/login";
            }
        }

        //Saving the edit to the database
        @PostMapping("/dashboard/blogs/{id}/edit")
        public String saveBlogEdit(@PathVariable long id, @RequestParam String blogTitle, @RequestParam String handle) {
            Blog blog = blogDao.getOne(id);
            blog.setBlogTitle(blogTitle);
            blog.setHandle(handle);
            blogDao.save(blog);
            return "redirect:blogs/index";
        }

        // Viewing All Blogs
        @GetMapping("/dashboard/blogs")
        public String getAllBlogs(Model model) {
                model.addAttribute("blogs", blogDao.findAll());
                return "blogs/index";
        }
    }
