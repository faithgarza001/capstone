package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.PostRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    private PostRepo postDao;

    public PostController(PostRepo postDao){
        this.postDao = postDao;
    }

    //Create form for a post
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

    //Saving the post to the database
    @PostMapping("/dashboard/posts/create")
    public String postNewPost(@RequestParam String title, @RequestParam String body) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post(title, body);
        postDao.save(post);
        return "redirect:/dashboard";
    }

    //Editing a post form
    @GetMapping("/dashboard/posts/{id}/edit")
    public String editPostForm(@PathVariable long id, Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            Post post = postDao.getOne(id);
            model.addAttribute("post", post);
            return "posts/edit";
        }else{
            return "redirect:/dashboard";
        }
    }

    //Saving the edit to the database
    @PostMapping("/dashboard/posts/{id}/edit")
    public String savePostEdit(@PathVariable long id, @RequestParam String title, @RequestParam String body){
        Post post = postDao.getOne(id);
        post.setTextTitle(title);
        post.setTextBody(body);
        postDao.save(post);
        return "redirect:/dashboard";
    }
}
