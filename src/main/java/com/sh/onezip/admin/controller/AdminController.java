package com.sh.onezip.admin.controller;


import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    MemberService memberService;

    @GetMapping("/memberList.do")
    public void listMembers(Model model) {
        List<Member> members = memberService.findAllMembers();
        model.addAttribute("members", members);
    }
//    @PostMapping("/admin/memberList.do/{memberId}") // /member/{id}로 할 수 있도록 공부
//    public String deleteById(@PathVariable String memberId){
//        System.out.println("멤버아이디 url");
//        memberService.deleteByid(memberId);
//
//        return "redirect:/"; // list 로 쓰면 껍데기만 보여짐
//    }

    @PostMapping("/memberList.do")
    public String memberList(@RequestParam String memberId,
                           RedirectAttributes redirectAttributes){
        memberService.deleteById(memberId);
        return "redirect:/admin/memberList.do";
    }

}
