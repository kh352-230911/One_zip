package com.sh.onezip.businessproduct.controller;

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
import java.time.LocalDate;
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
    public void businessproductlist(@RequestParam("bizMemberId") String bizMemberId, Model model, HttpServletRequest httpServletRequest) {
        // 하드코딩
        Businessmember businessmember = new Businessmember();
        businessmember.setBizMemberId("moneylove");
        // 요청 파라미터로부터 사업자 아이디(bizMemberId)를 가져옵니다.
        try {
            bizMemberId = httpServletRequest.getParameter("bizMemberId");
        } catch (NumberFormatException ignore) {
        }
        // 사업자가 올린 전체 상품 목록
        List<ProductListDto> businessproductLists = productService.findByBusinessmemberBizMemberId(businessmember.getBizMemberId());
        System.out.println(businessproductLists + "상품을 등록한 사업자");
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
        Page<ProductListDto> businessproductPage = productService.findAllBiz(pageable, businessmember.getBizMemberId());
        System.out.println(businessproductPage + "리스트가 잘나오나?");
        // 페이지 바 생성
        String pagebar = HelloMvcUtils.getPagebar(realPage, limit, businessproductLists.size(), url);
        System.out.println(pagebar + "페이지바");
        model.addAttribute("pagebar", pagebar); // view , controller
        model.addAttribute("business", businessproductPage.getContent()); // 사업자가 올린 상품 목록
        model.addAttribute("totalCount", businessproductLists.size()); // 전체 상품 수
    }

    @GetMapping("/businessproductcreate.do")
    public void businessproductcreate() {
    }

    @PostMapping("/businessproductcreate.do")
    public String businessproductcreate(
            // 유효성검사를 위한 BusinessCreateDto
            @Valid BusinessProductCreateDto businessProductCreateDto,
            // 유효성 검사 결과를 담는 BindingResult 객체
            BindingResult bindingResult,
            // 리다이렉트 시 데이터 전달을 위한 RedirectAttributes 객체
            RedirectAttributes redirectAttributes
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            // 첫 번째 오류 메시지를 가져와서 예외를 던짐
            // 유효성 검사를 통과하지 못했을 경우
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        // Businessmember 객체 생성 및 bizMemberId 설정
        Businessmember businessmember = new Businessmember();
        businessmember.setBizMemberId("moneylove");
        // BusinessProductCreateDto에 bizMemberId 설정
        businessProductCreateDto.setBusinessmember(businessmember);
        productService.businessproductcreate(businessProductCreateDto);

        // 리다이렉트후에 사용자피드백
        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 게시글을 성공적으로 등록했습니다. 🎈🎈🎈");
        return "redirect:/businessproduct/businessproductlist.do?bizMemberId=" + businessmember.getBizMemberId();
    }


    @GetMapping("/businessproductdetail.do")
    public void businessproductdetail(@RequestParam("id") Long id, Model model) {
        ProductDetailDto productDetailDto = productService.productdetailDtofindById(id);
        model.addAttribute("businessproduct", productDetailDto);
    }
    @PostMapping("/businessproductdetail.do")
    public String businessproductdetail
            (@RequestParam("id") Long id, Model model,
             @Valid BusinessProductCreateDto businessProductCreateDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        System.out.println(businessProductCreateDto + "잘불러오는감 dto");
        // 상품 id 하드 코딩
        Product product = new Product();
        System.out.println(product + "id 불러오는감?");
        if(bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();
            bindingResult.getAllErrors().forEach((err) -> {
                message.append(err.getDefaultMessage() + " ");
            });
            throw new RuntimeException(message.toString());
        }
        // Businessmember 객체 생성 및 bizMemberId 설정
        Businessmember businessmember = new Businessmember();
        businessmember.setBizMemberId("moneylove");

        businessProductCreateDto.setBusinessmember(businessmember);
        businessProductCreateDto.setRegDate(LocalDate.now());
        Product updateBizProduct = productService.businessproductupdate(businessProductCreateDto);
        System.out.println(businessProductCreateDto + "값을 받아오는감?");

        // 리다이렉트후에 사용자피드백
        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 게시글을 성공적으로 수정했습니다. 🎈🎈🎈");
        return "redirect:/businessproduct/businessproductdetail.do?id=" + updateBizProduct.getId();
    }

    // 삭제
    @PostMapping("/businessproductlist.do")
    public String businessproductlist (@RequestParam("id") Long id, @RequestParam("bizMemberId") String bizMemberId, Model model, RedirectAttributes redirectAttributes){
    Businessmember businessmember = new Businessmember();
        businessmember.setBizMemberId(bizMemberId);
        System.out.println(businessmember);
    Product product = new Product();
        System.out.println(product);
    product.setId(id);
        System.out.println(id);
    productService.deleteproductlist(product);
        redirectAttributes.addFlashAttribute("msg", "상품을 성공적으로 삭제했습니다.🤠");
        return "redirect:/businessproduct/businessproductlist.do?bizMemberId=" + businessmember.getBizMemberId();
    }

}
