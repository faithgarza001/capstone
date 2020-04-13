package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Blog;
import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.BlogRepo;
import com.capstone.kitsune.repositories.CategoryRepo;
import com.capstone.kitsune.repositories.PostRepo;
import com.capstone.kitsune.repositories.UserRepo;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@Scope("session")
public class PostController {
    private PostRepo postDao;
    private BlogRepo blogDao;
    private CategoryRepo categoryDao;
    private UserRepo userDao;

    public PostController(PostRepo postDao, BlogRepo blogDao, CategoryRepo categoryDao, UserRepo userDao) {
        this.postDao = postDao;
        this.blogDao = blogDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }

    //Create form for a post
    @GetMapping("/dashboard/posts/create")
    public String showCreateForm(HttpServletRequest request, Model model, @ModelAttribute(name="videoEmbedCode") String videoEmbedCode) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("Vid ID: " + (String)request.getSession().getAttribute("videoEmbedCode"));


        model.addAttribute("videoEmbedCode", (String)request.getSession().getAttribute("videoEmbedCode"));
        HttpSession foo = request.getSession();


        if (loggedInUser != null) {
            List<Category> categories = categoryDao.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("post", new Post());
            model.addAttribute("blogs", blogDao.findByUserId(loggedInUser.getId()));
            return "posts/create";
        } else {
            return "redirect:/login";
        }
    }

    //Saving the post to the database
    @PostMapping("/dashboard/posts/create")
    public String postNewPost(HttpServletRequest request, @RequestParam String textTitle, @RequestParam String textBody, @RequestParam long id, @RequestParam String[] categories, @ModelAttribute(name="videoEmbedCode") String videoEmbedCode, @RequestParam String linkUrl) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Blog blog = blogDao.getOne(id);
        //convert string[] ids to long[] ids
        long[] selectedCategoryIds = new long[categories.length];
        for (int i = 0; i < categories.length; i++) {
            selectedCategoryIds[i] = Long.parseLong(categories[i]);
        }
        List<Category> categoriesList = categoryDao.findByidIn(selectedCategoryIds);
        if(!linkUrl.contains("https://")){
            linkUrl = "https://" + linkUrl;
        }
        Post post = new Post(textTitle, textBody, loggedInUser, blog, categoriesList, (String)request.getSession().getAttribute("videoEmbedCode"), linkUrl);
        postDao.save(post);
        return "redirect:/dashboard/posts";
    }

    // Viewing All Posts in Dashboard
    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        //This will be posts from followed blogs when functionality is complete
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", loggedInUser.getUsername());
        model.addAttribute("users", userDao.getOne(loggedInUser.getId()));
        return "dashboard/index";
    }

    // Viewing All Posts in Dashboard
    @GetMapping("/dashboard/posts")
    public String getAllPost(Model model) {
        //This will be posts from followed blogs when functionality is complete
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    // Viewing All Posts in Dashboard
    @GetMapping("/dashboard/posts/{id}")
    public String getAllPosts(Model model, @PathVariable long id, Principal principal) {
        String userName = "";
        if (principal != null) {
            userName = principal.getName();
        }
        model.addAttribute("userName", userName);
        // Find all posts with the same blog_id as blogDao.getOne(id)
        model.addAttribute("post", postDao.getOne(id));
        return "posts/show";
    }


    //Viewing All User's Posts
    @GetMapping("/dashboard/posts/myposts")
    public String getMyPosts(Model model, Principal principal) {
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
        model.addAttribute("posts", postDao.findByUserId(loggedIn.getId()));
        return "posts/myposts";
    }

    @GetMapping("/posts")//@GetMapping: defines a method that should be invoked when a GET request is received for the specified URI
    public String getPosts(Model model){
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    //Create form for a post
    @GetMapping("/dashboard/posts/create")
    public String showCreateForm(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            List<Category> categories = categoryDao.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("post", new Post());
            model.addAttribute("blogs", blogDao.findByUserId(loggedInUser.getId()));
            return "posts/create";
        } else {
            return "redirect:/login";
        }
    }

    //Saving the post to the database
    @PostMapping("/dashboard/posts/create")
    public String postNewPost(@RequestParam String textTitle, @RequestParam String textBody, @RequestParam long id, @RequestParam String[] categories, @RequestParam String linkUrl) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Blog blog = blogDao.getOne(id);
        //convert string[] ids to long[] ids
        long[] selectedCategoryIds = new long[categories.length];
        for (int i = 0; i < categories.length; i++) {
            selectedCategoryIds[i] = Long.parseLong(categories[i]);
        }
        List<Category> categoriesList = categoryDao.findByidIn(selectedCategoryIds);
        Post post = new Post(textTitle, textBody, loggedInUser, blog, categoriesList);
        post.setLinkUrl(linkUrl);
        postDao.save(post);
        return "redirect:/dashboard";
    }

    //Editing a post form
    @GetMapping("/dashboard/posts/{id}/edit")
    public String editPostForm(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == postDao.getOne(id).getUser().getId()) {
            Post post = postDao.getOne(id);
            List<Category> categories = categoryDao.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("post", post);
            return "posts/edit";
        } else {
            return "redirect:/dashboard";
        }
    }

    //Saving the edit to the database
    @PostMapping("/dashboard/posts/{id}/edit")
    public String savePostEdit(@PathVariable long id, @RequestParam String textTitle, @RequestParam String textBody, @RequestParam String[] categories, @RequestParam String linkUrl) {
        Post post = postDao.getOne(id);
        long[] selectedCategoryIds = new long[categories.length];
        for (int i = 0; i < categories.length; i++) {
            selectedCategoryIds[i] = Long.parseLong(categories[i]);
        }
        List<Category> categoriesList = categoryDao.findByidIn(selectedCategoryIds);
        post.setTextTitle(textTitle);
        post.setTextBody(textBody);
        post.setCategories(categoriesList);
        if(!linkUrl.contains("https://")){
            linkUrl = "https://" + linkUrl;
        }
        post.setLinkUrl(linkUrl);
        postDao.save(post);
        return "redirect:/dashboard";
    }

    //Deleting a post
    @PostMapping("/dashboard/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == postDao.getOne(id).getUser().getId()) {
            Post post = postDao.getOne(id);
            post.setCategories(null);
            postDao.save(post);
            postDao.deleteById(id);
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard/posts/{id}/reblog")
    public String reblogPostForm(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            Post post = postDao.getOne(id);
            List<Category> categories = categoryDao.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("post", post);
            model.addAttribute("blogs", blogDao.findByUserId(loggedInUser.getId()));
            return "posts/reblog";
        } else {
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/dashboard/posts/{id}/reblog")
    public String savePostReblog(@RequestParam long id, @RequestParam String textTitle, @RequestParam String textBody, @RequestParam List<Category> categories, @RequestParam String videoEmbedCode, @RequestParam String linkUrl) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Blog blog = blogDao.getOne(id);
        if(!linkUrl.contains("https://")){
            linkUrl = "https://" + linkUrl;
        }
        Post post2 = new Post(textTitle, textBody, loggedInUser, blog, categories, videoEmbedCode, linkUrl);
        postDao.save(post2);
        return "redirect:/dashboard";
    }

}
