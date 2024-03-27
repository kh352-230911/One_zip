package com.sh.onezip.member.controller;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.dto.AttachmentDetailDto;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.attachment.service.S3FileService;
import com.sh.onezip.auth.service.AuthService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.business.dto.BusinessAllDto;
import com.sh.onezip.business.dto.BusinessCreateDto;
import com.sh.onezip.business.entity.BizAccess;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.business.service.BusinessService;
import com.sh.onezip.member.dto.MemberCreateDto;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.service.NotificationService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

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
    BusinessService businessService;
    @Autowired
    private S3FileService s3FileService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private NotificationService notificationService;

//    @Autowired
//    private ModelMapper modelMapper;

    @GetMapping("/createMember.do")
    public void createMember() {
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
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        log.debug("memberCreateDto = {}", memberCreateDto);

        // MemberCreateDto -> Member 변환
        Member member = memberCreateDto.toMember();
        String encodePassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodePassword);
        member = memberService.createMember(member);

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

    // 여기까지가 HSH 코드

    // HBK start
    @GetMapping("/createbusiness.do")
    public void createbusiness() {

    }

    @GetMapping("/createbizmember.do")
    public void createbizmember(@RequestParam Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
    }

    //    @PostMapping(value = "/createbizmember.do", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/createbizmember.do")
//    @ResponseBody
    public String createbizmember(
            @Valid BusinessCreateDto businessCreateDto,
            BindingResult bindingResult,
            @RequestParam("upFile") List<MultipartFile> upFiles,
            @AuthenticationPrincipal MemberDetails memberDetails,
            RedirectAttributes redirectAttributes)
            throws IOException {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        // 첨부파일 S3에 저장
        for (MultipartFile upFile : upFiles) {
            if (upFile.getSize() > 0) {
                AttachmentCreateDto attachmentCreateDto = s3FileService.upload(upFile);
                log.debug("attachmentCreateDto = {}", attachmentCreateDto);
                businessCreateDto.addAttachmentCreateDto(attachmentCreateDto);
            }
        }

        // 회원 정보 설정
        Member member = memberDetails.getMember();
        businessCreateDto.setMember(member);

        // DB 저장 (사업자 신규 등록, 첨부파일)
        businessCreateDto.setBizRegStatus(BizAccess.W);
        businessService.createBusiness(businessCreateDto);

        redirectAttributes.addFlashAttribute("msg", "사업자 회원으로의 가입이 정상적으로 접수되었습니다. 관리자가 승인한 후에 이메일로 사업자 변경 권한 여부를 알려드리겠습니다.");
        return "redirect:/";
    }

    @GetMapping("/updatebizmember.do")
    public void updatebizmember(@RequestParam Long id, Model model) {
        BusinessAllDto businessId = businessService.findByBId(id);
        model.addAttribute("biz", businessId);
        model.addAttribute("bizimage", attachmentService.findByIdWithType(id, "SP"));
    }

    @PostMapping("/updatebizmember.do")
    public String updatebizmember(
            @Valid BusinessAllDto businessAllDto,
            BindingResult bindingResult,
            @RequestParam("upFile") List<MultipartFile> upFiles,
            @AuthenticationPrincipal MemberDetails memberDetails,
            RedirectAttributes redirectAttributes)
            throws IOException {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        // 첨부파일 S3에 저장
        for (MultipartFile upFile : upFiles) {
            if (upFile.getSize() > 0) {
                AttachmentCreateDto attachmentCreateDto = s3FileService.upload(upFile);
                log.debug("attachmentCreateDto = {}", attachmentCreateDto);
                businessAllDto.addAttachmentCreateDto(attachmentCreateDto);
            }
        }
        // 회원 정보 설정
        Member member = memberDetails.getMember();
        businessAllDto.setMember(member);

//        // 사업자 고유번호 수정되지 않도록 처리
//        BusinessAllDto bizId = businessService.findByBId(businessAllDto.getId());
//        // 사업자 등록 번호는 그대로 유지 해야 하므로 작성
//        businessAllDto.setBizRegNo(bizId.getBizRegNo());

        // DB 저장 (사업자 수정, 첨부파일)
        // 사업자 수정의 경우 관리자가 수정사항을 검열해야함(사업자 등록 번호 uq key -> 변경 될 시 재 검열 (사업자 대기 회원으로 변경)
        businessAllDto.setBizRegStatus(BizAccess.W);
        businessService.updateBusiness(businessAllDto);

        redirectAttributes.addFlashAttribute("msg", "사업자 정보 수정이 완료되었으며, 사업자 대기 회원으로 전환되었습니다. 수정 내용은 관리자의 검토 후 이메일로 안내될 예정입니다.");
        return "redirect:/";

    }

@GetMapping("/fileDownload.do")
public ResponseEntity<?> fileDownload(@RequestParam("id") Long id, @RequestParam("refType") String refType)
        throws UnsupportedEncodingException {
    // 알림 업무로직
    notificationService.notifyFileDownload(id);
    // 파일다운로드 업무로직
    AttachmentDetailDto attachmentDetailDto = attachmentService.findByIdWithType(id, refType);
    return s3FileService.download(attachmentDetailDto);
    }
}
// HBK end

