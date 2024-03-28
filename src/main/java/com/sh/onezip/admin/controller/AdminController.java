package com.sh.onezip.admin.controller;

import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.authority.service.AuthorityService;
import com.sh.onezip.business.dto.BusinessAllDto;
import com.sh.onezip.business.entity.BizAccess;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.business.service.BusinessService;
import com.sh.onezip.customeranswercenter.entity.AnswerCenter;
import com.sh.onezip.customeranswercenter.service.AnswerCenterService;
import com.sh.onezip.customerquestioncenter.entity.AnswerCheck;
import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.customerquestioncenter.service.QuestionCenterService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import com.sh.onezip.stomp.dto.Type;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    MemberService memberService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    BusinessService businessService;
    @Autowired
    QuestionCenterService questionCenterService;
    @Autowired
    AnswerCenterService answerCenterService;
    @Autowired
    AttachmentService attachmentService;

    // HBK start
    @GetMapping("/memberList.do")
    public void listMembers(@PageableDefault(size = 8, page = 0) Pageable pageable, Model model) {
        // 승인되면 사업자로 권한 변경되어야함
        Page<Member> memberPage = memberService.findAllMembers(pageable);
        Page<Business> businessPage = businessService.findAllBizmembers(pageable);

        for (Business business : businessPage.getContent()) {
            // 사업자 등록 상태가 승인(A)인 경우에만 처리
            if (business.getBizRegStatus() == BizAccess.A) {
                // 해당 사업자에 연관된 회원의 권한을 업데이트함
                Member member = business.getMember();
                // 이미 ROLE_BUSINESS 권한을 가진 회원인 경우에도 처리
                boolean hasBusinessRole = member.getAuthorities().stream()
                        .anyMatch(authority -> authority.getUserType() == RoleAuth.ROLE_BUSINESS);
                if (!hasBusinessRole) {
                    // 새로운 권한을 추가함
                    Authority authority = new Authority();
                    authority.setMember(member);
                    authority.setUserType(RoleAuth.ROLE_BUSINESS);
                    member.getAuthorities().add(authority);
                }
            }
        }

        model.addAttribute("members", memberPage.getContent()); // 회원 목록을 나타내는 리스트
        model.addAttribute("totalCount", memberPage.getTotalElements()); // 전체 회원수
        model.addAttribute("memberCount", calculateMemberCount(memberPage.getContent(), RoleAuth.ROLE_USER)); // 일반 회원 수
        model.addAttribute("businessCount", calculateMemberCount(memberPage.getContent(), RoleAuth.ROLE_BUSINESS)); // 사업자 회원 수
        model.addAttribute("adminCount", calculateMemberCount(memberPage.getContent(), RoleAuth.ROLE_ADMIN)); // 관리자 회원 수
        model.addAttribute("size", memberPage.getSize()); // 페이지당 표시되는 회원 수
        model.addAttribute("number", memberPage.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", memberPage.getTotalPages()); // 전체 페이지 수

    }

    /**
     * 주어진 회원 목록에서 주어진 역할을 가진 회원 수를 계산
     *
     * @param members 회원 목록
     * @param role    계산할 회원 역할 (userType enum에서 사업자/ 관리자/ 일반회원 구별)
     *                filter 연산을 사용하여 회원 중에서 특정 역할을 가진 회원만 필터링
     *                anyMatch 메서드를 사용하여 회원의 권한 목록 중에서 특정 역할을 가진 권한이 있는지 확인
     *                stream 여러 데이터 요소를 활용 할 때 유용
     * @return 주어진 역할을 가진 회원 수
     */
    // 회원 유형별 회원 수를 계산하는 메소드
    private long calculateMemberCount(List<Member> members, RoleAuth role) {
        // 회원 목록을 스트림으로 변환하여 각 회원에 대해 필터링하고 주어진 역할을 가진 회원인지 확인
        return members.stream()
                // member entity안 Authority 객체를 불러옴 (변수명 : authorities)
                .filter(member -> member.getAuthorities().stream()
                        .anyMatch(authority -> authority.getUserType() == role))
                .count(); // 일치하는 회원 수를 계산하여 반환
    }

    @PostMapping("/memberList.do")
    public String memberList(@RequestParam Long id,
                             RedirectAttributes redirectAttributes) {
        memberService.deleteById(id);
        return "redirect:/admin/memberList.do";
    }


    @GetMapping("/businessmemberList.do")
    public void businessMemberLists(@PageableDefault(size = 8, page = 0) Pageable pageable, Model model) {

        Page<BusinessAllDto> businessPage = businessService.findAllBizmember(pageable);

        // 각 사업자 회원의 상태를 처리
        for (BusinessAllDto business : businessPage.getContent()) {
            // 각 회원의 상태에 따라 처리
            if (business.getBusiness().getBizRegStatus() == BizAccess.W) {
                // 대기 상태면 변경 없음
                business.getBusiness().setBizRegStatus(BizAccess.W);
            } else if (business.getBusiness().getBizRegStatus() == BizAccess.A) {
                // 승인 상태면 권한도 변경해줘야함
                business.getBusiness().setBizRegStatus(BizAccess.A);
            } else if (business.getBusiness().getBizRegStatus() == BizAccess.D) {
                // 반려 상태면 변경 없음
                business.getBusiness().setBizRegStatus(BizAccess.D);
            }
        }
        model.addAttribute("bizmembers", businessPage.getContent()); // 회원 목록을 나타내는 리스트
        model.addAttribute("totalCount", businessPage.getTotalElements()); // 사업자 전체 회원 수
        model.addAttribute("businessACount", calculateBizMemberCount(businessPage.getContent(), BizAccess.A)); // 사업자 승인 회원 수
        model.addAttribute("businessWCount", calculateBizMemberCount(businessPage.getContent(), BizAccess.W)); // 사업자 회원 대기 처리 수
        model.addAttribute("businessDCount", calculateBizMemberCount(businessPage.getContent(), BizAccess.D)); // 사업자 회원 반려 처리 수
        model.addAttribute("size", businessPage.getSize()); // 페이지당 표시되는 회원 수
        model.addAttribute("number", businessPage.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", businessPage.getTotalPages()); // 전체 페이지 수
    }

    /**
     * 주어진 회원 목록에서 주어진 역할을 가진 회원 수를 계산
     *
     * @param bizmembers 회원 목록
     * @param access     계산할 회원 역할 (userType enum에서 사업자/ 관리자/ 일반회원 구별)
     *                   filter 연산을 사용하여 회원 중에서 특정 역할을 가진 회원만 필터링
     *                   anyMatch 메서드를 사용하여 회원의 권한 목록 중에서 특정 역할을 가진 권한이 있는지 확인
     *                   stream 여러 데이터 요소를 활용 할 때 유용
     * @return 주어진 역할을 가진 회원 수
     */
    // 회원 유형별 회원 수를 계산하는 메소드
    private long calculateBizMemberCount(List<BusinessAllDto> bizmembers, BizAccess access) {
        // 회원 목록을 스트림으로 변환하여 각 회원에 대해 필터링하고 주어진 역할을 가진 회원인지 확인
        return bizmembers.stream()
                .filter(business -> business.getBusiness().getBizRegStatus() == access) // 해당 권한을 가진 회원만 필터링
                .count(); // 일치하는 회원 수를 계산하여 반환
    }

    @PostMapping("/businessmemberList.do")
    public String businessmemberList(@RequestParam Long id,
                                           RedirectAttributes redirectAttributes) {
        businessService.deleteById(id);
        attachmentService.deleteByphotoId(id);
        return "redirect:/admin/businessmemberList.do";
    }

    @GetMapping("/businessmemberDetailList.do")
    public void businessmemberDetailList(@RequestParam Long id, Model model){
        BusinessAllDto adminbusiness = businessService.findBizAmember(id);
        model.addAttribute("bizimage", attachmentService.findByIdWithType(id,"SP"));
        model.addAttribute("bizmember", adminbusiness);
    }


    @PostMapping("/businessmemberDetailList.do")
    public String businessmemberDetailList(@RequestParam Long id,
                                           @RequestParam String bizRegStatus,
                                           RedirectAttributes redirectAttributes) {
        // 1. 매개변수 확인
        System.out.println("ID: " + id);
        System.out.println("BizRegStatus: " + bizRegStatus);

        Optional<Business> businessOptional = businessService.findById(id);
        // 사업자 고유번호가 있는지 확인
        if (businessOptional.isPresent()) {
            Business business = businessOptional.get();
            Member member = business.getMember();
            if (bizRegStatus.equals("승인")) {
                business.setBizRegStatus(BizAccess.A);
                // 권한을 변경하여 저장
                Authority authority = new Authority();
                authority.setMember(member);
                authority.setUserType(RoleAuth.ROLE_BUSINESS);
                member.getAuthorities().clear(); // 지우고
                member.getAuthorities().add(authority); // 새로운 권한으로 변경
                memberService.updateMember(member);
            } else if (bizRegStatus.equals("반려")) {
                business.setBizRegStatus(BizAccess.D);
                // 권한을 변경하여 저장
                Authority authority = new Authority();
                authority.setMember(member);
                authority.setUserType(RoleAuth.ROLE_USER);
                member.getAuthorities().clear();
                member.getAuthorities().add(authority);
                memberService.updateMember(member);
            }
            businessService.updateBizAccess(business);
        } else {
            // 오류 페이지로 이동
        }
        // 리다이렉트 후 메시지 전달
        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 권한정보를 정상적으로 수정하였습니다. 🎈🎈🎈");
        return "redirect:/admin/businessmemberDetailList.do?id=" + id;
    }


    @GetMapping("/customerACenterList.do")
    public void customerCenterLists(@PageableDefault(size = 8, page = 0) Pageable pageable, Model model) {

        Page<QuestionCenter> questionCenterPages = questionCenterService.findAllQuestions(pageable);

        // 각 질문의 답변 여부를 확인하고 "N"으로 설정하여 모델에 추가 답변 완료시 Y로 변경
        List<QuestionCenter> questions = questionCenterPages.getContent();
        for (QuestionCenter question : questions) {
            if (question.getAnswerYn() == null) {
                question.setAnswerYn(AnswerCheck.N);
            }
        }
        model.addAttribute("questions", questionCenterPages.getContent()); // 질문 목록을 나타내는 리스트
        model.addAttribute("totalCount", questionCenterPages.getTotalElements()); // 전체 질문수
        model.addAttribute("size", questionCenterPages.getSize()); // 페이지당 표시되는 질문 수
        model.addAttribute("number", questionCenterPages.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", questionCenterPages.getTotalPages()); // 전체 페이지 수
        model.addAttribute("answerN", calculateAnswerCount(questionCenterPages.getContent(), AnswerCheck.N)); // 미 답변 수
        model.addAttribute("answerY", calculateAnswerCount(questionCenterPages.getContent(), AnswerCheck.Y)); // 답변 완료 수


    }

    private long calculateAnswerCount(List<QuestionCenter> content, AnswerCheck answerCheck) {
        return content.stream()
                .filter(answer -> answer.getAnswerYn() == answerCheck) // 해당 권한을 가진 회원만 필터링
                .count(); // 일치하는 회원 수를 계산하여 반환
    }

    @PostMapping("/customerACenterList.do")
    public String customerCenterList(@RequestParam Long id,
                                     RedirectAttributes redirectAttributes) {
        questionCenterService.deleteByQId(id);
        return "redirect:/admin/customerACenterList.do";
    }

    @GetMapping("/customerACenterDetailList.do")
    public void customerACenterDetailList(@RequestParam Long id, Model model) {
        QuestionCenter questionCenter = questionCenterService.findByQId(id);
        AnswerCenter answerCenter = questionCenter.getAnswerCenter(); // AnswerCenter 조회
        if (questionCenter.getAnswerYn() == null) {
            questionCenter.setAnswerYn(AnswerCheck.N);
        }
        model.addAttribute("question", questionCenter);
        model.addAttribute("answer", answerCenter); // AnswerCenter 모델에 추가
    }
    @PostMapping("/customerACenterDetailList.do")
    public String customerACenterDetailList(@RequestParam Long id,
                                            @RequestParam String aoneContent,
                                            @RequestParam String memberId,
                                            RedirectAttributes redirectAttributes) {

        // 문의글 고유번호로 문의 정보 조회
        QuestionCenter questionCenter = questionCenterService.findByQId(id);
        System.out.println("1: " + questionCenter);

        // Member 객체도 가져오기
        Member member = memberService.findByMemberId(memberId);
        // 새로운 답변 객체를 생성
        AnswerCenter answerCenter = new AnswerCenter();
        // 답변 객체에 답변 내용을 설정
        answerCenter.setAoneContent(aoneContent);
        // 답변 객체에 해당 문의글을 설정
        answerCenter.setQuestionCenter(questionCenter);
        System.out.println("2: " + answerCenter);
        answerCenter.setMember(member);

        // 답변 등록
        answerCenterService.createAnswer(answerCenter);
        System.out.println("3: " + answerCenter);

        // 문의글의 답변 여부를 업데이트 ("Y"로 변경)
        questionCenter.setAnswerYn(AnswerCheck.Y);
        // 문의글 엔티티를 저장하여 업데이트된 정보를 데이터베이스에 반영
        questionCenterService.saveQuestionCenter(questionCenter);

        // 리다이렉트 후 메시지 전달
        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 게시글을 성공적으로 등록했습니다. 🎈🎈🎈");
        return "redirect:/admin/customerACenterDetailList.do?id=" + id;
    }

    @PostMapping("/customerACenterUpdateList.do")
    public String customerACenterUpdateList(@RequestParam Long id,
                                            @RequestParam String newAoneContent,
                                            @RequestParam Long memberId, // 회원 ID 파라미터 추가(회원고유번호)
                                            RedirectAttributes redirectAttributes) {

        // 답변 고유번호로 기존 답변을 찾음
        Optional<AnswerCenter> answerCenter = answerCenterService.findById(id);

        // Optional이 존재하는지 확인
        if (answerCenter.isPresent()) {
            // Optional에서 값 가져오기
            AnswerCenter newAnswer = answerCenter.get();
            newAnswer.setAoneContent(newAoneContent);

//            // 회원 ID에 해당하는 회원 객체 가져오기
//            Member member = memberService.findByAOneMemberId(memberId);

            // 가져온 회원 객체를 답변에 설정
//            newAnswer.setMember(member);
            answerCenterService.updateAnswerCenter(newAnswer);
        } else {
            // 에러 페이지로 이동 (해당 답변을 찾지 못한 경우 또는 멤버를 찾지 못한 경우)
        }

        // 리다이렉트 후 메시지 전달
        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 게시글을 성공적으로 수정했습니다. 🎈🎈🎈");
        return "redirect:/admin/customerACenterDetailList.do?id=" + id;
    }

    @GetMapping("/bizEmailSend.do")
    public void bizEmailSend(@RequestParam Long id, Model model){

    }
// HBK end
}




