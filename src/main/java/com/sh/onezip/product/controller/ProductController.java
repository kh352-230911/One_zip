package com.sh.onezip.product.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.dto.ProductPurchaseInfoDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sh.onezip.common.HelloMvcUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/productList.do")
    public void productList(Model model, HttpServletRequest httpServletRequest){
        String url = httpServletRequest.getRequestURI();
        int realPage = 1;
        int limit = 10;
        try {
            realPage = Integer.parseInt(httpServletRequest.getParameter("page"));
        } catch (NumberFormatException ignore) {}
        Pageable pageable = PageRequest.of(realPage - 1, limit);
        Page<ProductListDto> productPage = productService.findAll(pageable);
        List<ProductListDto> productListDtos = productService.findAll();
        // 1: 현재 페이지 번호
        // 2: 한 페이지당 표시할 개체 수
        // 3: 전체페이지 수
        // 4: 요청 url
        String pagebar = HelloMvcUtils.getPagebar(
                realPage, limit, productListDtos.size() , url);
        System.out.println(pagebar + "pagebar");
        model.addAttribute("pagebar", pagebar);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalCount", productListDtos.size());
        System.out.println("productList");
    }

    @GetMapping("/productDetail.do")
    public void productDetail(@RequestParam("id") Long id, Model model) {
        ProductDetailDto productDetailDto = productService.ProductDetailDtofindById(id);
        model.addAttribute("product", productDetailDto);
        log.debug("product = {}", productDetailDto);
    }

    @PostMapping("/productPurchaseInfo.do")
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

    @PostMapping("/productGiftInfo.do")
    public void productGiftInfo(){

    }

}
