package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleController {

    @GetMapping("/dashboard/search")
    public String googleCSE() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            return "gsearch";
        } else {
            return "redirect:/login";
        }
    }
}
