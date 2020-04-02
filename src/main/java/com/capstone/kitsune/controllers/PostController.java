package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Category;
import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.CategoryRepo;
import com.capstone.kitsune.repositories.PostRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {
    private PostRepo postDao;
    private CategoryRepo categoryDao;

    public PostController(PostRepo postDao, CategoryRepo categoryDao) {
        this.postDao = postDao;
        this.categoryDao = categoryDao;
    }


    //Create form for a post
    @GetMapping("/dashboard/posts/create")
    public String showCreateForm(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            List<Category> categories = categoryDao.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("post", new Post());
            return "posts/create";
        } else {
            return "redirect:/login";
        }
    }

    //Saving the post to the database
    @PostMapping("/dashboard/posts/create")
    public String postNewPost(@RequestParam String textTitle, @RequestParam String textBody, @RequestParam String[] categories) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //convert string[] ids to long[] ids
        long[] selectedCategoryIds = new long[categories.length];
        for(int i = 0; i < categories.length; i++) {
            selectedCategoryIds[i] = Long.parseLong(categories[i]);
        }

        List<Category> categoriesList = categoryDao.findByidIn(selectedCategoryIds);

        Post post = new Post(textTitle, textBody, loggedInUser, categoriesList);
        postDao.save(post);
        return "redirect:/dashboard";
    }

    //Editing a post form
    @GetMapping("/dashboard/posts/{id}/edit")
    public String editPostForm(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            Post post = postDao.getOne(id);
            model.addAttribute("post", post);
            return "posts/edit";
        } else {
            return "redirect:/dashboard";
        }
    }

    //Saving the edit to the database
    @PostMapping("/dashboard/posts/{id}/edit")
    public String savePostEdit(@PathVariable long id, @RequestParam String textTitle, @RequestParam String textBody) {
        Post post = postDao.getOne(id);
        post.setTextTitle(textTitle);
        post.setTextBody(textBody);
        postDao.save(post);
        return "redirect:/dashboard";
    }

    //Deleting a post
    @PostMapping("/dashboard/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == postDao.getOne(id).getUser().getId()) {
            postDao.deleteById(id);
        }
        return "redirect:/dashboard";
    }
}
