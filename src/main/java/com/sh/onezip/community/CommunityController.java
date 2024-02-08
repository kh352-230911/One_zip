package com.sh.onezip.community;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
@Slf4j
public class CommunityController {
    @GetMapping("/home.do")
    public void home(){}

    @GetMapping("/diary.do")
    public void diary(){}
    @GetMapping("/photo.do")
    public void photo(){}
    @GetMapping("/visit.do")
    public void visit(){}


}
