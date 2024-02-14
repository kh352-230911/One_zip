package com.sh.onezip.businessproduct.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.businessproduct.service.BusinessmemberService;
import com.sh.onezip.common.HelloMvcUtils;
import com.sh.onezip.product.dto.BusinessProductCreateDto;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/businessproduct")
@Slf4j
@Validated
public class BusinessProductController {
    @Autowired
    BusinessmemberService businessmemberService;
    @Autowired
    ProductService productService;

    @GetMapping("/businessproductlist.do")
// Model: Spring MVC에서 Controller에서 View로 데이터를 전달하는 데 사용되는 인터페이스
// HttpServletRequest: HTTP 요청 정보를 제공하는 클래스
    public void businessproductlist(Model model, HttpServletRequest httpServletRequest) {

        String bizMemberId = httpServletRequest.getParameter("bizMemberId");
        // 현재 요청의 URI 가져옴 (현재 사용자가 접속한 페이지의 URL을 나타낸다)
        String url = httpServletRequest.getRequestURI();
        int realPage = 1;
        int limit = 5;
        try {
            realPage = Integer.parseInt(httpServletRequest.getParameter("page"));
        } catch (NumberFormatException ignore) {
        }
        Pageable pageable = PageRequest.of(realPage - 1, limit);
        // findAll(pageable)호출하여 페이지네이션된 상품 목록
        Page<ProductListDto> businessproductPage = productService.findAllBiz(pageable);
        // 상픔을 등록한 사업자의 상품 전체 목록을 보여주도록 productService.findAllbusinessproduct()
        List<ProductListDto> businessproductLists = productService.findByBusinessmemberBizMemberId(bizMemberId);
        // (현재 페이지 번호, 페이지당 표시할 개체 수, 전체 페이지 수, 요청 URL을 인자로 받아 페이지바를 생성)
        String pagebar = HelloMvcUtils.getPagebar(
                realPage, limit, businessproductLists.size(), url);
        // model.addAttribute()를 사용하여 View로 전달할 데이터를 추가
        // pagebar, business(페이지네이션된 상품 목록), totalCount(전체 상품 수)를 모델에 추가
        model.addAttribute("pagebar", pagebar); // view , controller
        model.addAttribute("business", businessproductPage.getContent());
        model.addAttribute("totalCount", businessproductLists.size());
    }

    @GetMapping("/businessproductcreate.do")
    public void businessproductcreate() {
    }

    @PostMapping("/businessproductcreate.do")
    public String businessproductcreate(
            // 유효성검사흫 위한 BusinessCreateDto
            @Valid BusinessProductCreateDto businessProductCreateDto,
            // 유효성 검사 결과를 담는 BindingResult 객체
            BindingResult bindingResult,
            // 리다이렉트 시 데이터 전달을 위한 RedirectAttributes 객체
            RedirectAttributes redirectAttributes
    ) throws IOException {
        if(bindingResult.hasErrors()){
            // 첫 번째 오류 메시지를 가져와서 예외를 던짐
            // 유효성 검사를 통과하지 못했을 경우
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        // ModelMapper 인스턴스 생성
//        ModelMapper modelMapper = new ModelMapper();
//        // ModelMapper 설정 변경
//        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        // Businessmember 객체 생성 및 bizMemberId 설정
        Businessmember businessmember = new Businessmember();
        businessmember.setBizMemberId("moneylove");
        // BusinessProductCreateDto에 bizMemberId 설정
//        businessProductCreateDto.setBizMemberId(businessmember.getBizMemberId());
        businessProductCreateDto.setBusinessmember(businessmember);

        System.out.println(businessmember);
        System.out.println(businessProductCreateDto);
        productService.businessproductcreate(businessProductCreateDto);

        // 리다이렉트후에 사용자피드백
        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 게시글을 성공적으로 등록했습니다. 🎈🎈🎈");
        return "redirect:/businessproduct/businessproductlist.do";
    }


    @GetMapping("/businessproductdetail.do")
    public void businessproductdetail(@RequestParam ("id") Long id, Model model) {
        ProductDetailDto productDetailDto = productService.productdetailDtofindById(id);
        model.addAttribute("businessproduct", productDetailDto);
    }
    @GetMapping("/businessproductupdate.do")
    public void businessproductupdate(){}
    }

//    @PostMapping("/businessproductcreate.do")
//    public String businessproductcreate(){}
//}
//    @GetMapping("/businessproductdetail.do")
//    public void businessproductdetail(@RequestParam("id")Long id, Model model){
//
//    }



