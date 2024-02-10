package com.sh.onezip.product.controller;

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
    // size: 페이지당 아이템 수
    // page: 가져올 페이지 번호
//    public void productList(@PageableDefault(size = 5, page = 0) Pageable pageable,
//                            Model model, HttpServletRequest httpServletRequest){
//        Page<ProductListDto> productPage = productService.findAll(pageable);
//        String url = httpServletRequest.getRequestURI();
//        // Pageable에서 가져올 페이지 번호인 page가 0이라면,
//        // 사용자에게 보이는 페이지 번호는 1이어야 함.
//        int realPage = pageable.getPageNumber() + 1;
//        try {
//            realPage = Integer.parseInt(httpServletRequest.getParameter("page"));
//        } catch (NumberFormatException ignore) {}
//        // 1: 현재 페이지 번호
//        // 2: 한 페이지당 표시할 개체 수
//        // 3: 전체페이지 수
//        // 4: 요청 url
//        String pagebar = HelloMvcUtils.getPagebar(
//                realPage, productPage.getSize(), productPage.getTotalPages() , url);
//        System.out.println(pagebar + "pagebar");
//        model.addAttribute("pagebar", pagebar);
//        model.addAttribute("products", productPage.getContent());
//        model.addAttribute("totalCount", productPage.getTotalElements());
//        System.out.println("productList");
//    }

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
        ProductDetailDto productDetailDto = productService.findById(id);
        model.addAttribute("product", productDetailDto);
        log.debug("product = {}", productDetailDto);
    }

}
