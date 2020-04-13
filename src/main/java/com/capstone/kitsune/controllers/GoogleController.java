package com.capstone.kitsune.controllers;

import com.capstone.kitsune.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
//@PropertySource("file:/srv/kitsune.rocks/application.properties")
@Scope("session")
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
    public String youtube(HttpServletRequest request, Model model, @RequestParam(defaultValue = "", name="button") String videoID) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser != null) {

            System.out.println("VID_ID:|" + videoID+"|");
            if(videoID != null && !(videoID.isBlank() || videoID.isEmpty()))
            {
                request.getSession().setAttribute("videoEmbedCode", videoID);


                return "redirect:/dashboard/posts/create";
            }
            model.addAttribute("key", youtubeAPIKey);
            return "search/youtube";
        } else {
            return "redirect:/login";
        }
    }
}
