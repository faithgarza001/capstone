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
            return "search/gsearch";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/dashboard/search/videos")
    public String youtube() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            return "search/youtube";
        } else {
            return "redirect:/login";
        }
    }
}
