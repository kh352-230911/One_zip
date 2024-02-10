package com.sh.onezip.businessmember.controller;

import com.sh.onezip.businessmember.service.BusinessmemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/businessproduct")
@Slf4j
@Validated
public class BusinessproductListController {
    @Autowired
    BusinessmemberService businessmemberService;

    @GetMapping("/businessproductlist.do")
    public void businessproductlist(){

    }
}

