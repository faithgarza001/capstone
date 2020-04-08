package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleController {

    @Value("${youtube.api.key}")
    private String youtubeAPIKey;

    @GetMapping("/dashboard/search/texts")
    public String googleCSE() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            return "search/gsearch";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/dashboard/search/videos")
    public String youtube(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {
            model.addAttribute("key", youtubeAPIKey);
            return "search/youtube";
        } else {
            return "redirect:/login";
        }
    }
}
