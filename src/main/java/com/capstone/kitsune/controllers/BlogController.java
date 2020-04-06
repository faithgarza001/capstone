package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.BlogRepo;
import com.capstone.kitsune.repositories.CategoryRepo;
import com.capstone.kitsune.repositories.PostRepo;
import com.capstone.kitsune.services.BlogsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

@Controller
public class BlogController extends BlogsService {
    private BlogRepo blogDao;
    private CategoryRepo categoryDao;
    private PostRepo postDao;

    public BlogController(BlogRepo blogDao, CategoryRepo categoryDao, PostRepo postDao) {
        this.blogDao = blogDao;
        this.categoryDao = categoryDao;
        this.postDao = postDao;
    }

    @GetMapping("/blogs")//@GetMapping: defines a method that should be invoked when a GET request is received for the specified URI
    public String getBlogs(Model model){
        model.addAttribute("posts", blogDao.findAll());
        return "blogs/index";
    }

    //Create form for a blogs
    @GetMapping("/dashboard/blogs/create")
    public String showCreateBlogForm(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            List<Category> categories = categoryDao.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("blog", new Blog());
            return "blogs/create";
        } else {
            return "redirect:/login";
        }
    }

    //Saving a new blog to the database
    @PostMapping("/dashboard/blogs/create")
    public String postNewBlog(@RequestParam String blogTitle, @RequestParam String handle, @RequestParam String[] categories) {
        Blog newBlog = new Blog();
        long[] selectedCategoryIds = new long[categories.length];
        for (int i = 0; i < categories.length; i++) {
            selectedCategoryIds[i] = Long.parseLong(categories[i]);
        }
        List<Category> categoriesList = categoryDao.findByidIn(selectedCategoryIds);
        newBlog.setCategories(categoriesList);
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newBlog.setBlogTitle(blogTitle);
        //If handle input is not null, set handle to @RequestParam, else set handle to random 6 digit alphanumeric string
        if (handle.equals("")) {
            newBlog.setHandle(getAlphaNumericString(6));
        } else {
            newBlog.setHandle(handle);
            newBlog.setCategories(categoriesList);
            newBlog.setUser(loggedIn);
            blogDao.save(newBlog);
            return "redirect:/dashboard/blogs";
        }
        newBlog.setCategories(categoriesList);
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
            List<Category> categories = categoryDao.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("blog", blog);
            return "blogs/edit";
        } else {
            return "redirect:/login";
        }
    }

    //Saving the edit to the database
    @PostMapping("/dashboard/blogs/{id}/edit")
    public String saveBlogEdit(@PathVariable long id, @RequestParam String blogTitle, @RequestParam String handle, @RequestParam List<Category> categories) {
        Blog blog = blogDao.getOne(id);
        blog.setBlogTitle(blogTitle);
        blog.setCategories(categories);
        blog.setHandle(handle);
        blogDao.save(blog);
        return "redirect:/dashboard/blogs/{id}";
    }

    // Viewing All Blogs
    @GetMapping("/dashboard/blogs")
    public String getAllBlogs(Model model) {
        model.addAttribute("blogs", blogDao.findAll());
        return "blogs/index";
    }

    //Viewing All User's Blogs
    @GetMapping("/dashboard/blogs/myblogs")
    public String getMyBlogs(Model model, Principal principal) {
        // Getting logged in user
        User loggedIn = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = "";
        if (principal != null) {
            // Setting username based on principal
            userName = principal.getName();
        }
        //Setting authorized username to be used in myblogs view
        model.addAttribute("userName", userName);
        // SUPPOSED to get all blogs that match the logged in user's id (blogs user_id == users id)
        model.addAttribute("blogs", blogDao.findByUserId(loggedIn.getId()));
        return "blogs/myblogs";
    }

    //Viewing One User Blog
    @GetMapping("/dashboard/blogs/{id}")
    public String getOneBlog(@PathVariable long id, Model model, Principal principal) {
        String userName = "";
        if (principal != null) {
            userName = principal.getName();
        }
        model.addAttribute("userName", userName);
        model.addAttribute("blog", blogDao.getOne(id));
        // Find all posts with the same blog_id as blogDao.getOne(id)
        model.addAttribute("posts", postDao.findByBlogId(id));
        return "blogs/show";
    }

    @PostMapping("/dashboard/blogs/{id}/delete")
    public String deleteBlog(@PathVariable long id){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == blogDao.getOne(id).getUser().getId()) {
            blogDao.deleteById(id);
        }
        return "redirect:/dashboard/blogs";
    }

    @PostMapping("/dashboard/blogs/{id}/following")
    public String following(@PathVariable long id){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            List<Blog> followed = loggedInUser.getFollowing();
            followed.add(blogDao.getOne(id));
            loggedInUser.setFollowing(followed);
        }else{
            return "redirect:/login";
        }
        return "redirect:/dashboard/blogs/{id}";
    }
}
