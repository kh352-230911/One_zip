package com.sh.onezip.product.controller;

import com.sh.onezip.common.HelloMvcUtils;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.repository.ProductRepository;
import com.sh.onezip.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/productList.do")
    public void productList(Model model,
                            @RequestParam(name = "price", required = false, defaultValue = "0") int price,
                            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                            HttpServletRequest httpServletRequest
                            ){
        int limit = 6;
        Pageable pageable = PageRequest.of(page - 1, limit);

        String url = httpServletRequest.getRequestURI() + "?price=" + price;

        Page<ProductListDto> productPage = productService.productListDtoFindAllByPrice(pageable, price);
        List<ProductListDto> productListDtos = productService.productListDtoFindAllByPrice(price);

        // 1: 현재 페이지 번호
        // 2: 한 페이지당 표시할 개체 수
        // 3: 전체 개체수
        // 4: 요청 url
        String pagebar = HelloMvcUtils.getPagebar(
                page, limit, productListDtos.size() , url);

        model.addAttribute("pagebar", pagebar);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalCount", productListDtos.size());
    }


}
