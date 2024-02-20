package com.sh.onezip.businessproduct.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business")
@Slf4j
@Validated

    public class BusinessController {
    @GetMapping("/createbusiness.do")
        public void createbusiness(){}

    }
