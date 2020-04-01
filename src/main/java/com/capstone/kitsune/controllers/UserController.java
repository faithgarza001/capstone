package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.Post;
import com.capstone.kitsune.models.User;
import com.capstone.kitsune.repositories.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private UserRepo users;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepo users, PasswordEncoder passwordEncoder){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    //sign-up form
    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    //Saving user
    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);
        return "redirect:/login";
    }

    @GetMapping("/account/{username}/edit")
    public String showAccountEditForm(Model model, @PathVariable String username){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            User user = users.findByUsername(username);
            model.addAttribute("user", user);
            return "users/edit";
        } else {
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/account/{username}/edit")
    public String accountEdit(@PathVariable String username, @RequestParam String password, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName){
        User user = users.findByUsername(username);
        String hash = this.passwordEncoder.encode(password);
        user.setPassword(hash);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        users.save(user);
        return "redirect:/dashboard";
    }

}
