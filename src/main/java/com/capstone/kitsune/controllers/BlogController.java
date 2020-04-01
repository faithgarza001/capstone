package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.BlogRepo;
import com.capstone.kitsune.repositories.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

@Controller
public class BlogController {
    private BlogRepo blogDao;
    private UserRepo userDao;

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

    //Saving a new blog to the database
    @PostMapping("/blogs/create")
    public String postNewBlog(@RequestParam String blogTitle, @RequestParam String handle) {
        Blog newBlog = new Blog();
        newBlog.setBlogTitle(blogTitle);
        //If handle input is not null, set handle to @RequestParam, else set handle to "hardCodedHandle"
        newBlog.setHandle(Objects.requireNonNullElse(handle, "hardCodedHandle"));
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newBlog.setUser(loggedIn);
        blogDao.save(newBlog);
        return "redirect:/dashboard/blogs/myblogs";
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
        return "redirect:/dashboard/blogs";
    }

    // Viewing All Blogs
    @GetMapping("/dashboard/blogs")
    public String getAllBlogs(Model model) {
        model.addAttribute("blogs", blogDao.findAll());
        return "blogs/index";
    }

//    //Viewing All User's Blogs
//    @GetMapping("/dashboard/blogs/myblogs")
//    public String getPost(Model model, Principal principal){
////        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userName = "";
//        if (principal != null) {
//            userName = principal.getName();
////            userDao.findByUsername(userName);
//        }
//        model.addAttribute("userName", userName);
//        model.addAttribute("blog",blogDao.findAll());
//        return "blogs/myblogs";
//    }
}
