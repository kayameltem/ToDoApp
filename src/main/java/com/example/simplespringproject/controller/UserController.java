package com.example.simplespringproject.controller;

import com.example.simplespringproject.dto.UserDto;
import com.example.simplespringproject.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller responsible for handling user-related requests such as registration and login.
 */
@Controller
public class UserController {

    private final IUserService userService;

    /**
     * Constructs a new UserController with the specified user service.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Handles GET requests to the registration page.
     *
     * @param userDto the user DTO used for registration
     * @return the name of the registration view
     */
    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto){
        return "register";
    }

    /**
     * Handles POST requests to save a new user.
     *
     * @param userDto the user DTO containing user registration information
     * @param model the model to add attributes to
     * @return the name of the login view
     */
    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user")UserDto userDto, Model model){
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfully!");
        return "login";
    }

    /**
     * Handles GET requests to the login page.
     *
     * @return the name of the login view
     */
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

}
