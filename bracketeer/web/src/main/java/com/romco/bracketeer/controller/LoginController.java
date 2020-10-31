package com.romco.bracketeer.controller;

import com.romco.bracketeer.service.UserService;
import com.romco.bracketeer.util.Mappings;
import com.romco.bracketeer.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class LoginController {
    // == fields
    private final UserService userService;

    // == constructors
    @Autowired
    public LoginController(UserService service) {
        this.userService = service;
    }

    // == model attributes
    
    // == request handlers
    @GetMapping(Mappings.UserManagement.LOGIN)
    public String login(Model model) {
        return ViewNames.UserManagement.LOGIN;
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("isLoginError", true);
        return ViewNames.UserManagement.LOGIN;
    }

    @GetMapping(Mappings.UserManagement.REGISTER)
    public String showRegister() {
        log.info("in /register");
        return ViewNames.UserManagement.REGISTER;
    }

    @PostMapping(Mappings.UserManagement.REGISTER)
    public String register(
                           @RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "passwordConfirmation") String passwordConfirmation,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "emailConfirmation") String emailConfirmation
                          ) {

        log.info("username is " + username);
        log.info("in post register");
        userService.registerUser(username, password, email);
        // TODO log in right away
        
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(true);
        
        
        
        // TODO if
        return ViewNames.UserManagement.LOGIN;
    }
}
