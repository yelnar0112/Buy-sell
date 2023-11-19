package com.yelnar.ebook.controller;

import com.yelnar.ebook.models.User;
import com.yelnar.ebook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(Model model, User user){
        model.addAttribute("user", user);
        return "login";
    }


    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") User user){
        userService.createUser(user);
        return "redirect:/registration?success";
    }


    @GetMapping("/hello")
    public String secureUrl(){
        return "hello";
    }
}
