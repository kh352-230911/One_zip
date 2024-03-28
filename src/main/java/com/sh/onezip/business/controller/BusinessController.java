package com.sh.onezip.business.controller;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.attachment.service.S3FileService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.business.dto.BusinessAllDto;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.business.service.BusinessService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
    @Autowired
    S3FileService s3FileService;
    @Autowired
    AttachmentService attachmentService;

    @GetMapping("/productList.do")
    public void productList(@AuthenticationPrincipal MemberDetails memberDetails, @PageableDefault(size = 6, page = 0) Pageable pageable, Model model) {
        // findAllBizIdProduct 에서 member.id(회원고유번호)를 찾고 productListDtoPage Long id(상품고유번호)와 매핑하여 사업자가 등록한 상품을 조회한다.
        Page<ProductListDto> productListDtoPage = productService.findAllBizIdProduct(memberDetails.getMember().getId(), pageable);
        log.debug("productListDtoPage = {}", productListDtoPage);
        model.addAttribute("products", productListDtoPage.getContent()); // 상품 목록
        model.addAttribute("totalCount", productListDtoPage.getTotalElements()); // 전체 상품 수
        model.addAttribute("FOCount", calculateProductCount(productListDtoPage.getContent(), ProductType.FO));
        model.addAttribute("FUCount", calculateProductCount(productListDtoPage.getContent(), ProductType.FU));
        model.addAttribute("size", productListDtoPage.getSize()); // 페이지당 표시되는 상품 수
        model.addAttribute("number", productListDtoPage.getNumber()); // 현재 페이지 번호
        model.addAttribute("totalPages", productListDtoPage.getTotalPages()); // 전체 페이지 수

    }

    private long calculateProductCount(List<ProductListDto> products, ProductType type) {
        return products.stream()
                .filter(ptype -> ptype.getProductTypecode() == type)
                .count();
    }

    @PostMapping("/productList.do")
    public String productList(@RequestParam Long id,
                                     RedirectAttributes redirectAttributes) {
        productService.deleteById(id);
        attachmentService.deleteByphotoId(id);
        return "redirect:/business/productList.do";
    }

    @GetMapping("/productDetailList.do")
    public void productDetailList(@RequestParam Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
    }

    @PostMapping("/productDetailList.do")
    public String productDetailList(
            @Valid ProductDetailDto productDetailDto,
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
                productDetailDto.addAttachmentCreateDto(attachmentCreateDto);
            }
        }

        // 회원 정보 설정
        Member member = memberDetails.getMember();
        productDetailDto.setMemberId(member.getId());

        // DB 저장(사업자 상품 등록, 첨부파일)
        productService.createProductBiz(productDetailDto);

        redirectAttributes.addFlashAttribute("msg", "상품이 등록되었습니다 🎁");
        return "redirect:/business/productList.do";
    }

    @GetMapping("/productUpdateList.do")
    public void productUpdateList(@RequestParam Long id, Model model){
        // 회원 고유번호를 찾고 productListDto랑 매핑
        ProductListDto productListDto = productService.findByBizProductId(id);
        model.addAttribute("product", productListDto);
        model.addAttribute("bizimage", attachmentService.findByIdWithType(id, "SP"));
    }
}
