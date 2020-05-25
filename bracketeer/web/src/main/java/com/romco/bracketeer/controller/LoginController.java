package com.romco.bracketeer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class LoginController {
    // == fields

    // == constructors

    // == model attributes

    // == request handlers
    @GetMapping("/login")
    public String login(Model model) {
        return "login.html";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("isLoginError", true);
        return "login.html";
    }
}