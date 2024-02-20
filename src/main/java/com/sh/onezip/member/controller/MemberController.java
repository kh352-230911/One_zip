package com.sh.onezip.member.controller;

import com.sh.onezip.auth.service.AuthService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.dto.MemberCreateDto;
import com.sh.onezip.member.dto.MemberDetailDto;
import com.sh.onezip.member.dto.MemberUpdateDto;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/createMember.do")
    public void createMember() {
    }

    @PostMapping("/createMember.do")
    public String createMember(
            @Valid MemberCreateDto memberCreateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        log.debug("memberCreateDto = {}", memberCreateDto);

        // MemberCreateDto -> Member 변환
        Member member = memberCreateDto.toMember();
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        // 업무로직
        member = memberService.createMember(member);
        // 리다이렉트후 메세지처리
        redirectAttributes.addFlashAttribute("msg", "🎉🎉🎉 회원가입을 축하드립니다. 🎉🎉🎉");
        return "redirect:/";
    }

    @GetMapping("/memberDetail.do")
    public String memberDetail(@AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        log.debug("memberDetails = {}", memberDetails);

        Member member = memberDetails.getMember(); // 접속한 회원의 멤버 객체

        // ModelMapper 또는 다른 매핑 방식을 사용하여 Member 엔티티를 MemberDetailDto로 변환
        MemberDetailDto memberDetailDto = modelMapper.map(member, MemberDetailDto.class);

        // 전체 주소에서 기본 주소와 상세 주소 추출
        String fullAddress = member.getMemberAddr();
        String baseAddress = extractBaseAddress(fullAddress); // 기본 주소 추출
        String detailAddress = extractDetailAddress(fullAddress); // 상세 주소 추출

        // 추출된 기본 주소와 상세 주소를 MemberDetailDto 객체에 설정
        memberDetailDto.setMemberAddr(baseAddress); // 기본 주소 설정
        memberDetailDto.setMemberDetailAddr(detailAddress); // 상세 주소 설정

        // 모델에 MemberDetailDto 객체 추가
        model.addAttribute("member", memberDetailDto);

        return "member/memberDetail"; // 뷰 이름 반환
    }


//    private String extractDetailAddress(String fullAddress) {
//        if (fullAddress == null || fullAddress.isEmpty()) {
//            return ""; // 빈 문자열 반환
//        }
//        String[] parts = fullAddress.split("#");
//        return parts.length > 1 ? parts[1] : ""; // 상세 주소 반환 또는 빈 문자열
//    }

    private String extractBaseAddress(String fullAddress) {
        if (fullAddress == null || fullAddress.isEmpty()) {
            return ""; // 빈 문자열 반환
        }
        String[] parts = fullAddress.split("#");
        return parts.length > 0 ? parts[0] : ""; // 기본 주소 반환 또는 빈 문자열
    }

    private String extractDetailAddress(String fullAddress) {
        if (fullAddress == null || fullAddress.isEmpty()) {
            return ""; // 빈 문자열 반환
        }
        String[] parts = fullAddress.split("#");
        return parts.length > 1 ? parts[1] : ""; // 상세 주소 반환 또는 빈 문자열
    }



    @PostMapping("/updateMember.do")
    public String updateMember(@Valid MemberUpdateDto memberUpdateDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal MemberDetails memberDetails,
                               RedirectAttributes redirectAttributes) {
        log.debug("memberUpdateDto = {}", memberUpdateDto);
        if(bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();
            bindingResult.getAllErrors().forEach((err) -> {
                message.append(err.getDefaultMessage() + " ");
            });
            throw new RuntimeException(message.toString());
        }

        // entity 업데이트
        Member member = memberDetails.getMember();
        member.setName(memberUpdateDto.getName());
        member.setMemberAddr(memberUpdateDto.getMemberAddr());
        member.setHobby(memberUpdateDto.getHobby());
        member.setMbti(memberUpdateDto.getMbti());

        memberService.updateMember(member);

        // security Authentication 갱신
        authService.updateAuthentication(member.getMemberId());

        redirectAttributes.addFlashAttribute("msg", "회원정보가 성공적으로 변경되었습니다. 🎊");

        return "redirect:/member/memberDetail.do";
    }

    @PostMapping("/checkIdDuplicate.do")
    public ResponseEntity<?> checkIdDuplicate(@RequestParam("memberId") String memberId) {
        Map<String, Object> resultMap = Map.of(
                "available",
                memberService.findByMemberId(memberId) == null
        );
        return ResponseEntity.ok(resultMap);
    }


    }
