package com.sh.onezip.product.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.dto.ProductPurchaseInfoDto;
import com.sh.onezip.productReview.dto.ProductReviewCreateDto;
import com.sh.onezip.productReview.dto.ProductReviewDto;
import com.sh.onezip.productReview.entity.ProductReview;
import com.sh.onezip.productReview.service.ProductReviewService;
import com.sh.onezip.productquestion.dto.ProductQuestionCreateDto;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.service.ProductService;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sh.onezip.common.HelloMvcUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductReviewService productReviewService;

    @GetMapping("/productList.do")
    public void productList(Model model, HttpServletRequest httpServletRequest){

        int realPage = 1;
        int refPrice = 0;
        int limit = 5;
        try {
            refPrice = Integer.parseInt(httpServletRequest.getParameter("price"));
        } catch (NumberFormatException ignore) {}
        try {
            realPage = Integer.parseInt(httpServletRequest.getParameter("page"));
        } catch (NumberFormatException ignore) {}
        Pageable pageable = PageRequest.of(realPage - 1, limit);

        String url = httpServletRequest.getRequestURI() + "?price=" + refPrice;
        System.out.println("List의 URL" + url);
        
//        Page<ProductListDto> productPage = productService.productListDtoFindAll(pageable); 기존 코드
//        List<ProductListDto> productListDtos = productService.productListDtoFindAll(); 기존 코드

        Page<ProductListDto> productPage = productService.productListDtoFindAllByPrice(pageable, refPrice);
        List<ProductListDto> productListDtos = productService.productListDtoFindAllByPrice(refPrice);

        // 1: 현재 페이지 번호
        // 2: 한 페이지당 표시할 개체 수
        // 3: 전체 개체수
        // 4: 요청 url
        String pagebar = HelloMvcUtils.getPagebar(
                realPage, limit, productListDtos.size() , url);
        model.addAttribute("pagebar", pagebar);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalCount", productListDtos.size());
    }

    @GetMapping("/productDetail.do")
    public void productDetail(@RequestParam("id") Long id, Model model) {
        ProductDetailDto productDetailDto = productService.productDetailDtofindById(id);
        model.addAttribute("product", productDetailDto);
        log.debug("product = {}", productDetailDto);

    }

    @GetMapping("/productPurchaseInfo.do")
    public void productPurchaseInfo(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("productId") Long id,
            @RequestParam("productQuantity") int productQuantity,
            Model model){
        Member member = memberDetails.getMember();
        ProductPurchaseInfoDto productPurchaseInfoDto = productService.productPurchaseInfoDtofindById(id);
        productPurchaseInfoDto.setMember(member);
        productPurchaseInfoDto.setProductQuantity(productQuantity);
        int totalPrice = productPurchaseInfoDto.getProductPrice() * productQuantity;
        double discountRate = (double)productPurchaseInfoDto.getDiscountRate()/100;
        productPurchaseInfoDto.setTotalProductPrice(totalPrice);
        int totalDiscountPrice = (int)((totalPrice) * (discountRate));
        productPurchaseInfoDto.setTotalDiscountPrice(totalDiscountPrice);
        productPurchaseInfoDto.setSellPrice(totalPrice - totalDiscountPrice);
        model.addAttribute("productPurchaseInfoDto", productPurchaseInfoDto);
    }

    @GetMapping("/productGiftInfo.do")
    public void productGiftInfo(){

    }

    @GetMapping("/productQna.do")
    public void productQna(@RequestParam("id") Long id,Model model, HttpServletRequest httpServletRequest){
        try {
            id = Long.parseLong(httpServletRequest.getParameter("id"));
        } catch (NumberFormatException ignore) {}
        Optional<Product> productOpt = productService.findById(id);
        Product product = productOpt.orElse(null);
        List<ProductQuestion> productQuestions = productService.pquestionFindByProductid(product.getId());
//        String url = httpServletRequest.getRequestURI();
        String url = httpServletRequest.getRequestURI() + "?id=" +id;
        int realPage = 1;
        int limit = 5;
        try {
            realPage = Integer.parseInt(httpServletRequest.getParameter("page"));
        } catch (NumberFormatException ignore) {}

        Pageable pageable = PageRequest.of(realPage - 1, limit);
        Page<ProductQuestionDto> productQuestionPage = productService.productQuestionFindAllByProductId(pageable, product.getId());
        List<ProductQuestionDto> productQuestionDtos = productService.productQuestionDtoFindAllByProductId(product.getId());
        // 1: 현재 페이지 번호
        // 2: 한 페이지당 표시할 개체 수
        // 3: 전체 개체수
        // 4: 요청 url
        String pagebar = HelloMvcUtils.getPagebar(
                realPage, limit, productQuestions.size() , url);
        model.addAttribute("pagebar", pagebar);
        model.addAttribute("questions", productQuestionPage.getContent());
        model.addAttribute("productId", id);
        model.addAttribute("totalCount", productQuestionDtos.size());

    }

    @GetMapping("/productReview.do")
    public void productReview(@RequestParam("id") Long id,
                              Model model,
                              @AuthenticationPrincipal MemberDetails memberDetails,
                              HttpServletRequest httpServletRequest){
        try {
            id = Long.parseLong(httpServletRequest.getParameter("id"));
        } catch (NumberFormatException ignore) {}
        Optional<Product> productOpt = productService.findById(id);
        Product product = productOpt.orElse(null);
        int realPage = 1;
        int limit = 5;
        Member member = memberDetails.getMember();

        List<ProductReview> productReviews = productService.productReviewFindByProductid(product.getId());
        String url = httpServletRequest.getRequestURI() + "?id=" +id;
        try {
            realPage = Integer.parseInt(httpServletRequest.getParameter("page"));
        } catch (NumberFormatException ignore) {}

        Pageable pageable = PageRequest.of(realPage - 1, limit);
        Page<ProductReviewDto> productReviewPage = productService.productReviewFindAllByProductId(pageable, product.getId());
        List<ProductReviewDto> productReviewDtos = productService.productReviewDtoFindAllByProductId(product.getId());

        // 1: 현재 페이지 번호
        // 2: 한 페이지당 표시할 개체 수
        // 3: 전체 개체수
        // 4: 요청 url
        String pagebar = HelloMvcUtils.getPagebar(
                realPage, limit, productReviews.size() , url);
//        System.out.println(productQuestionDtos + "productQuestionDtos***");
        model.addAttribute("pagebar", pagebar);
//        model.addAttribute("productQuestionDtos", productQuestionDtos);
        model.addAttribute("reviews", productReviewPage.getContent());
        model.addAttribute("totalCount", productReviewDtos.size());
        model.addAttribute("productId", id);
        model.addAttribute("member", member);
    }

    @GetMapping("/productReviewCreatePage.do")
    public void productReviewCreatePage(@RequestParam("id") Long id,
                                        @AuthenticationPrincipal MemberDetails memberDetails,
                                        Model model){
        Member member = memberDetails.getMember();

        model.addAttribute("id", id);
        model.addAttribute("Member", member);

    }

    @PostMapping("/productReviewCreate.do")
    public String productCreateReview(@RequestParam("productNo") Long productId,
                                      @Valid ProductReviewCreateDto productReviewCreateDto,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal MemberDetails memberDetails,
                                      Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Member member = memberDetails.getMember();
        productReviewCreateDto.setProductNo(productId);
        productReviewCreateDto.setMemberId(member.getMemberId());
        productReviewCreateDto.setReviewRegdate(LocalDate.now());
        productService.createReview(productReviewCreateDto);

//        redirectAttributes.addAttribute("msg","dev등록에 성공했습니다.");
        return "redirect:/product/productReview.do?id=" + productId;
    }

    @GetMapping("/productQnaCreatePage.do")
    public void productQnaCreatePage(@RequestParam("id") Long id,
                                     @AuthenticationPrincipal MemberDetails memberDetails,
                                     Model model){
        Member member = memberDetails.getMember();
        model.addAttribute("id", id);
        model.addAttribute("Member", member);

    }

    @PostMapping("/productQnaCreate.do")
    public String productQnaCreate(@RequestParam("productNo") Long productId,
                                   @Valid ProductQuestionCreateDto productQuestionCreateDto,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal MemberDetails memberDetails,
                                   Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Optional<Product> productOpt = productService.findById(productId);
        Product product = productOpt.orElse(null);

        Member member = memberDetails.getMember();
        productQuestionCreateDto.setProduct(product);
        productQuestionCreateDto.setMemberId(member.getMemberId());
        productQuestionCreateDto.setQRegdate(LocalDate.now());
        productService.createQuestion(productQuestionCreateDto);

        return "redirect:/product/productQna.do?id=" + productId;
    }

    @PostMapping("/reviewfurcate.do")
    public String reviewfurcate(@RequestParam("action") String action,
                                @RequestParam("reviewerId") String reviewerId,
                                @RequestParam("productId") Long productId,
                                @RequestParam("reviewId") Long reviewId,
                                Model model,
                                RedirectAttributes redirectAttributes){
        model.addAttribute("reviewerId", reviewerId);
        model.addAttribute("productId", productId);
        if ("update".equals(action)) {
            return "redirect:/product/productReview.do?id=" + productId;
        } else if ("delete".equals(action)) {
            productReviewService.deleteById(reviewId);
            return "redirect:/product/productReview.do?id=" + productId;
        }
        return "redirect:/product/productList.do?id=" + productId;
    }

}
