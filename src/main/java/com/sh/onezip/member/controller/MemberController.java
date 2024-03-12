package com.sh.onezip.member.controller;

import com.sh.onezip.auth.service.AuthService;
import com.sh.onezip.member.dto.MemberCreateDto;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/member")
@Slf4j
@Validated
public class MemberController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MemberService memberService;
    @Autowired
    AuthService authService;
//    @Autowired
//    private ModelMapper modelMapper;

    @GetMapping("/createMember.do")
    public void createMember(){
    }
    /**
     * 1. dto 유효성 검사
     * 2. dto -> entity
     * 3. 비밀번호 암호화처리 (PasswordEncoder)
     * 4. 리다이렉트 후에 사용자 메세지
     *
     * @param memberCreateDto
     * @param redirectAttributes
     * @return
     */

    @Transactional
    @PostMapping("/createMember.do")
    public String createMember(
            @Valid MemberCreateDto memberCreateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        log.debug("memberCreateDto = {}", memberCreateDto);

        // MemberCreateDto -> Member 변환
        Member member = memberCreateDto.toMember();
        System.out.println("flag1");
        String encodePassword = passwordEncoder.encode(member.getPassword());
        System.out.println("flag2");
        member.setPassword(encodePassword);
        System.out.println("flag3");
        member = memberService.createMember(member);
        System.out.println("flag4");

        redirectAttributes.addFlashAttribute("msg", "회원가입이 완료되었습니다.");
        return "redirect:/";
    }

//    @GetMapping("/memberDetail.do")
//    public void memberDetail(Authentication authentication, @AuthenticationPrincipal MemberDetails memberDetails){
//        log.debug("authentication = {}", authentication);
//        log.debug("memberDetails = {}", memberDetails);
//    }
@PostMapping("/checkIdDuplicate.do")
public ResponseEntity<?> checkIdDuplicate(@RequestParam("memberId") String memberId) {
    Map<String, Object> resultMap = Map.of(
            "available",
            memberService.findByMemberId(memberId) == null
    );
    return ResponseEntity.ok(resultMap);
}
    @GetMapping("/selectMemberType.do")
    public void selectMemberType() {

    }

}
