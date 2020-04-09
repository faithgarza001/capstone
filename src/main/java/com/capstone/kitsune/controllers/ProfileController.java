package com.capstone.kitsune.controllers;

import com.capstone.kitsune.repositories.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {


    private UserRepo userRepo;

    public ProfileController(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @RequestMapping("/account")
    public String profile(Model model){

        return "profile";
    }

}
