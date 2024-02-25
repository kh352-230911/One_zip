package com.sh.onezip.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @GetMapping("/login.do")
    public void login(){

    }

    @GetMapping("/bizLogin.do")
    public void bizLogin(){

    }
}
