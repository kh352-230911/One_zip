package com.sh.onezip.businessproduct.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.businessproduct.dto.BusinessCreateDto;
import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.businessproduct.service.BusinessmemberService;
import com.sh.onezip.member.dto.MemberCreateDto;
import com.sh.onezip.member.dto.MemberDetailDto;
import com.sh.onezip.member.dto.MemberUpdateDto;
import com.sh.onezip.member.entity.Member;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/business")
@Slf4j
@Validated

    public class BusinessController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    BusinessmemberService businessmemberService;

    @GetMapping("/createbusiness.do")
        public void createbusiness(){}

    @PostMapping("/createbusiness.do")
    public String createbusiness(
            @Valid BusinessCreateDto businessCreateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        log.debug("businessCreateDto = {}", businessCreateDto);

        // BusinessCreateDto -> Businessmember 변환
        Businessmember businessmember = businessCreateDto.toBusiness();
        String encodedPassword = passwordEncoder.encode(businessmember.getBizPassword());
        businessmember.setBizMemberId(encodedPassword);
        // 업무로직
        businessmember = businessmemberService.createBusiness(businessmember);
        // 리다이렉트후 메세지처리
        redirectAttributes.addFlashAttribute("msg", "🎉🎉🎉 회원가입을 축하드립니다. 🎉🎉🎉");
        return "redirect:/";
    }
    @PostMapping("/checkIdDuplicate.do")
    public ResponseEntity<?> checkIdDuplicate(@RequestParam("bizMemberId") String bizMemberId) {
        Map<String, Object> resultMap = Map.of(
                "available",
                businessmemberService.findBybizMemberId(bizMemberId) == null
        );
        return ResponseEntity.ok(resultMap);
    }
    }
