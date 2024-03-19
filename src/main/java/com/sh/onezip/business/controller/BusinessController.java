package com.sh.onezip.business.controller;

import com.sh.onezip.business.service.BusinessService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequestMapping("/business")
@Slf4j
public class BusinessController {
    @Autowired
    BusinessService businessService;
    @Autowired
    MemberService memberService;

    @GetMapping("/createbusiness.do")
    public void createbusiness() {

    }
}
