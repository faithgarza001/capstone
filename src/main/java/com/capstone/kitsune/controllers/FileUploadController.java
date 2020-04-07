package com.capstone.kitsune.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    @RequestMapping("/dashboard/posts/create")
    public String uploadPage(Model model) {
        return "posts/create";
    }

    @RequestMapping("/dashboard/posts/myposts")
    public String upload(Model model, @RequestParam("files") MultipartFile[] files) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("msg", "Successfully uploaded " + fileNames.toString());
        return "posts/myposts";
    }
}
