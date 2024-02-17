package com.sh.onezip.businessqanswer.controller;

import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.businessproduct.service.BusinessmemberService;
import com.sh.onezip.common.HelloMvcUtils;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.service.ProductService;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/businessmanagement")
@Slf4j
@Validated
public class BusinessQanswerController {
    @Autowired
    BusinessmemberService businessmemberService;
    @Autowired
    ProductService productService;

    @GetMapping("/businessqanswerlist.do")
    public void businessqanswerlist(@RequestParam("bizMemberId") String bizMemberId, Model model, HttpServletRequest httpServletRequest)  {
        // 하드코딩
        Businessmember businessmember = new Businessmember();
        businessmember.setBizMemberId("moneylove");
        // 요청 파라미터로부터 사업자 아이디(bizMemberId)를 가져옵니다.
        try {
            bizMemberId = httpServletRequest.getParameter("bizMemberId");
        } catch (NumberFormatException ignore) {
        }
        // 회원 문의 내역(사업자 : moneylove)
        List<ProductQuestionDto> productQuestionLists = productService.findByQuestion(businessmember.getBizMemberId());
        System.out.println(productQuestionLists + "!!!");
        // 페이지 관련 처리
        String url = httpServletRequest.getRequestURI() + "?bizMemberId=" + bizMemberId;
        System.out.println(url + "나왔나..?");
        int realPage = 1;
        int limit = 5;
        try {
            realPage = Integer.parseInt(httpServletRequest.getParameter("page"));
            System.out.println(realPage + "진짜일까..?");
        } catch (NumberFormatException ignore) {
        }
        Pageable pageable = PageRequest.of(realPage - 1, limit);
        System.out.println(pageable + "잘넘어가나");
        // 해당 상품의 리스트를 페이지네이션하여 가져옵니다.
        Page<ProductQuestionDto> productQuestionPage = productService.findAllQuestion(pageable, businessmember.getBizMemberId());
        System.out.println(productQuestionPage + "리스트가 잘나오나?");
        // 페이지 바 생성
        String pagebar = HelloMvcUtils.getPagebar(realPage, limit, productQuestionLists.size(), url);
        System.out.println(pagebar + "페이지바");
        model.addAttribute("pagebar", pagebar); // view , controller
        model.addAttribute("questions", productQuestionPage.getContent()); // 사업자 로그인 시 전체 질문 목록
        model.addAttribute("totalCount", productQuestionLists.size()); // 사업자 로그인 시 전체 질문 수
    }
    @GetMapping("/businessqanswerdetail.do")
    public void businessqanswerdetail(){

    }
}
