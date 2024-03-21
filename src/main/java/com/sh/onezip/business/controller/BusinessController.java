package com.sh.onezip.business.controller;

import com.sh.onezip.business.entity.Business;
import com.sh.onezip.business.service.BusinessService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/business")
@Slf4j
public class BusinessController {
    @Autowired
    BusinessService businessService;
    @Autowired
    MemberService memberService;
    @Autowired
    ProductService productService;

    @GetMapping("/productList.do")
    public void productList(@RequestParam Long id, @PageableDefault(size =8, page =0) Pageable pageable, Model model){
    Business business = new Business();
    business.setId(id);
    Page<Product> productPage = productService.findAllBizProduct(pageable);
    model.addAttribute("products", productPage.getContent()); // 상품 목록
    model.addAttribute("totalCount", productPage.getContent()); // 전체 상품 수
    model.addAttribute("FOCount", calculateProductCount(productPage.getContent(),ProductType.FO));
    model.addAttribute("FUCount", calculateProductCount(productPage.getContent(),ProductType.FU));
    model.addAttribute("size", productPage.getSize()); // 페이지당 표시되는 상품 수
    model.addAttribute("number", productPage.getNumber()); // 현재 페이지 번호
    model.addAttribute("totalPages", productPage.getTotalPages()); // 전체 페이지 수


    }
    private long calculateProductCount(List<Product> products, ProductType type){
        return products.stream()
                .filter(ptype -> ptype.getProductTypecode() == type)
                .count();
    }
}
