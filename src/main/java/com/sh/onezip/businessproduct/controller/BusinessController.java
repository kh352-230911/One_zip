package com.sh.onezip.businessproduct.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.businessproduct.dto.BusinessCreateDto;
import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.businessproduct.service.BusinessmemberService;
import com.sh.onezip.member.dto.MemberCreateDto;
import com.sh.onezip.member.dto.MemberDetailDto;
import com.sh.onezip.member.dto.MemberUpdateDto;
import com.sh.onezip.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
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
    @GetMapping("/bizlogin.do")
    public void bizlogin(){
    }
//    @PostMapping("/bizlogin.do")
//    public String processLogin(@RequestParam("bizMemberId") String bizMemberId,
//                               @RequestParam("bizPassword") String bizPassword,
//                               RedirectAttributes redirectAttributes) {
//        // 사용자가 제출한 비즈니스 로그인 정보를 검증
//        if (isValidBusinessLogin(bizMemberId, bizPassword)) {
//            // 비즈니스 로그인이 성공한 경우
//            // 로그인 성공 후 처리를 위해 리다이렉트 URL로 이동
//            System.out.println("비즈니스 로그인 성공: " + bizMemberId);
//            return "redirect:/businessproduct/businessproductlist.do?bizMemberId=" + bizMemberId; // 비즈니스 로그인 성공 후 이동할 URL로 변경
//        } else {
//            // 비즈니스 로그인이 실패한 경우
//            // 실패 메시지를 리다이렉트 후에도 유지하기 위해 Flash Attributes를 사용하여 실패 메시지 전달
//            redirectAttributes.addFlashAttribute("error", "사업자 로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.");
//            System.out.println("비즈니스 로그인 실패: " + bizMemberId);
//            return "redirect:/business/bizlogin.do"; // 비즈니스 로그인 페이지로 다시 리다이렉트
//        }
//    }
//
//    // 사용자가 제출한 비즈니스 로그인 정보가 유효한지 검증하는 메서드
//    private boolean isValidBusinessLogin(String bizMemberId, String bizPassword) {
//        // 데이터베이스에서 사용자 정보 조회
////        BusinessUser user = userRepository.findByBizMemberId(bizMemberId);
//        Businessmember businessmember = businessmemberService.findBybizMemberId(bizMemberId);
//        // 사용자 정보가 존재하고, 입력된 비밀번호가 저장된 비밀번호와 일치하는지 확인
//        if (businessmember != null && passwordEncoder.matches(bizPassword, businessmember.getBizPassword())) {
//            return true; // 로그인 성공
//        }
//        return false; // 로그인 실패
//    }
//}

//    @PostMapping("/bizlogin.do")
//    public String processBusinessLogin(HttpServletRequest request, Model model) {
//
//        String bizMemberId = request.getParameter("bizMemberId");
//        String bizPassword = request.getParameter("bizPassword");
//
//        // 아이디로 사업자 회원 정보를 데이터베이스에서 가져옴
//        Businessmember businessmember = businessmemberService.findBybizMemberId(bizMemberId);
//
//        // 회원이 존재하고, 입력된 비밀번호가 일치하는지 확인
//        if (businessmember != null && passwordEncoder.matches(bizPassword, businessmember.getBizPassword())) {
//            // 인증 성공
//            // 세션에 사용자 정보를 저장하거나 다음 페이지로 리다이렉트할 수 있음
//            // 여기서는 간단히 로그를 출력하고 대시보드 페이지로 리다이렉트
//            System.out.println("로그인 성공: " + bizMemberId);
//            return "redirect:/businessproduct/businessproductlist.do?bizMemberId=" + businessmember.getBizMemberId();
//        } else {
//            // 인증 실패
//            // 로그인 페이지로 리다이렉트하고 에러 메시지 표시
//            System.out.println("로그인 실패: " + bizMemberId);
//            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
//            return "redirect:/business/bizlogin.do?error";
//        }
//    }
}

//    @PostMapping("/bizlogin.do")
//    public String processLogin(@RequestParam("bizMemberId") String bizMemberId,
//                               @RequestParam("bizPassword") String bizPassword,
//                               RedirectAttributes redirectAttributes) {
//        // 사용자가 제출한 비즈니스 로그인 정보를 검증
//        if (isValidBusinessLogin(bizMemberId, bizPassword)) {
//            // 비즈니스 로그인이 성공한 경우
//            // 로그인 성공 후 처리를 위해 리다이렉트 URL로 이동
//            return "redirect:/businessproduct/businessproductlist.do?bizMemberId=" + bizMemberId; // 비즈니스 로그인 성공 후 이동할 URL로 변경
//        } else {
//            // 비즈니스 로그인이 실패한 경우
//            // 실패 메시지를 리다이렉트 후에도 유지하기 위해 Flash Attributes를 사용하여 실패 메시지 전달
//            redirectAttributes.addFlashAttribute("error", "사업자 로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.");
//            return "redirect:/business/bizlogin.do"; // 비즈니스 로그인 페이지로 다시 리다이렉트
//        }
//    }
//
//    // 사용자가 제출한 비즈니스 로그인 정보가 유효한지 검증하는 메서드
//    private boolean isValidBusinessLogin(String bizMemberId, String bizPassword) {
//        // 실제 데이터베이스에서 해당 사용자를 조회하여 인증을 수행하고 결과를 반환합니다.
//        Businessmember businessmember = businessmemberService.findBybizMemberId(bizMemberId);
//        if (businessmember == null) {
//            return false; // 사용자가 존재하지 않으면 로그인 실패
//        }
//
//        // 데이터베이스에서 조회한 사용자의 비밀번호와 사용자가 제출한 비밀번호를 비교하여 일치 여부를 반환합니다.
//        return passwordEncoder.matches(bizPassword, businessmember.getBizPassword());
//    }
//}
