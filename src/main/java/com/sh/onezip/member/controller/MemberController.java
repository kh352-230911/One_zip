package com.sh.onezip.member.controller;

import com.sh.onezip.auth.service.AuthService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.dto.MemberCreateDto;
import com.sh.onezip.member.dto.MemberDetailDto;
import com.sh.onezip.member.dto.MemberUpdateDto;
import com.sh.onezip.member.entity.Address;
import com.sh.onezip.member.entity.AddressType;
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

//    @Transactional
//    @PostMapping("/createMember.do")
//    public String createMember(
//            @Valid MemberCreateDto memberCreateDto,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes) {
//        if(bindingResult.hasErrors()) {
//            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
//            log.debug("message = {}", message);
//            throw new RuntimeException(message);
//        }
//        log.debug("memberCreateDto = {}", memberCreateDto);
//
//        Member member = memberCreateDto.toMember();
//        String encodePassword = passwordEncoder.encode(member.getPassword());
//        member.setPassword(encodePassword);
////        member = memberService.createMember(member);
//
//        Address address = memberCreateDto.toAddress(member);
//        address.setRecipientName(member.getName());
//        address.setAddressType(AddressType.D);
//
//        System.out.println(address);
//        member = memberService.createMember(member, address);
//
//        redirectAttributes.addFlashAttribute("msg", "회원가입이 완료되었습니다.");
//        return "redirect:/";
//    }

    @Transactional
    @PostMapping("/createMember.do")
    public String createMember(
            @Valid MemberCreateDto memberCreateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        log.debug("memberCreateDto = {}", memberCreateDto);

        // Member 엔터티 생성 및 비밀번호 인코딩
        Member member = memberCreateDto.toMember();
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // Address 엔터티 생성
        Address address = memberCreateDto.toAddress(member);
        // DTO에서 받은 recipientName과 recipientPhone 설정
        address.setRecipientName(memberCreateDto.getName());
        address.setRecipientPhone(memberCreateDto.getPhone());
        // AddressType.D 설정
        address.setAddressType(AddressType.D);

        // Member와 Address 엔터티 저장
        memberService.createMember(member, address);

        // 회원가입 성공 메시지를 리다이렉트 어트리뷰트에 추가
        redirectAttributes.addFlashAttribute("msg", "회원가입이 완료되었습니다.");
        return "redirect:/";
    }

    @GetMapping("/memberDetail.do")
    public String memberDetail(Authentication authentication, @AuthenticationPrincipal MemberDetails memberDetails, Model model){
        Member member = memberDetails.getMember(); // 접속한 회원의 멤버 객체

        MemberDetailDto memberDetailDto = modelMapper.map(member, MemberDetailDto.class);

        System.out.println(member);
        model.addAttribute("member", memberDetailDto);
        return "member/memberDetail";
    }
@PostMapping("/checkIdDuplicate.do")
public ResponseEntity<?> checkIdDuplicate(@RequestParam("memberId") String memberId) {
    Map<String, Object> resultMap = Map.of(
            "available",
            memberService.findByMemberId(memberId) == null
    );
    return ResponseEntity.ok(resultMap);
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
        member.setNickname(memberUpdateDto.getNickname());
        member.setHobby(memberUpdateDto.getHobby());
        member.setMbti(memberUpdateDto.getMbti());
        memberService.updateMember(member);

        // security Authentication 갱신
        authService.updateAuthentication(member.getMemberId());

        redirectAttributes.addFlashAttribute("msg", "회원정보가 성공적으로 변경되었습니다. 🎊");

        return "redirect:/member/memberDetail.do";
    }

    @GetMapping("/selectMemberType.do")
    public void selectMemberType() {

    }



    // 여기까지가 HSH 코드
}
