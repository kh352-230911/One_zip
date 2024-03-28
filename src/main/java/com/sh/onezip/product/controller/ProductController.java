package com.sh.onezip.product.controller;

import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.common.HelloMvcUtils;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.dto.ProductPurchaseInfoDto;
import com.sh.onezip.product.dto.useProductPurchaseInfoDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.product.service.ProductService;
import com.sh.onezip.productoption.service.ProductOptionService;
import com.sh.onezip.productquestion.dto.ProductQuestionCreateDto;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productquestion.service.ProductQuestionService;
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
import org.springframework.web.bind.annotation.*;
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
    AttachmentService attachmentService;
    @Autowired
    ProductOptionService productOptionService;
    @Autowired
    ProductQuestionService productQuestionService;

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

        String pagebar = HelloMvcUtils.getPagebar(
                page, limit, productListDtos.size() , url);

        model.addAttribute("pagebar", pagebar);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalCount", productListDtos.size());
    }
    @GetMapping("/productDetail.do")
    public void productDetail(@RequestParam("id") Long id,
                              Model model) {
        ProductDetailDto productDetailDto = productService.productDetailDtofindById(id);
        List<ProductOption> productOptions = productOptionService.findAllByProductId(productDetailDto.getId());
        List<Attachment> attachmentList = attachmentService.findProductAttachmentToList(productDetailDto.getId());
        productDetailDto.setProductOptions(productOptions);
        productDetailDto.setAttachmentList(attachmentList);

        model.addAttribute("product", productDetailDto);
    }

    @PostMapping("/productPurchaseInfo.do")
    public void productPurchaseInfo(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Valid useProductPurchaseInfoDto useProductPurchaseInfoDto,
            Model model){
        Member member = memberDetails.getMember();
        List<ProductOption> productOptions = productOptionService.findAllByProductId(useProductPurchaseInfoDto.getProductId());

        Long Optionid = 0L;
        Optionid = Long.parseLong(useProductPurchaseInfoDto.getSelectOption().split("#")[0]);
        String[] selectOptionArr = useProductPurchaseInfoDto.getSelectOption().split("#"); //      index-0: option id, index-1: option additional cost
        Long optionId = Long.parseLong(selectOptionArr[0]); // 선택 옵션 고유번호
        int additionalCost = Integer.parseInt(selectOptionArr[1]); // 선택 옵션 추가 요금

        ProductOption productOption = productOptionService.findById(Optionid);

        ProductPurchaseInfoDto productPurchaseInfoDto = productService.productPurchaseInfoDtofindById(useProductPurchaseInfoDto.getProductId());

        productPurchaseInfoDto.setOptionId(optionId);
        productPurchaseInfoDto.setAdditionalCost(additionalCost);

        int totalOptionCost = 0;
        if(productOption != null){
            productPurchaseInfoDto.setProductOption(productOption);
            totalOptionCost = productPurchaseInfoDto.getProductOption().getOptionCost() * useProductPurchaseInfoDto.getProductQuantity();
            productPurchaseInfoDto.setTotalOptionCost(totalOptionCost);
        }

        productPurchaseInfoDto.setMember(member);
        productPurchaseInfoDto.setProductQuantity(useProductPurchaseInfoDto.getProductQuantity());

        productPurchaseInfoDto.setProductOptionLists(productOptions);

        int totalPrice = productPurchaseInfoDto.getProductPrice() * useProductPurchaseInfoDto.getProductQuantity();

        productPurchaseInfoDto.setTotalProductPrice(totalPrice);
        int totalDiscountPrice = (int)((totalPrice) * (productPurchaseInfoDto.getDiscountRate()));
        productPurchaseInfoDto.setTotalDiscountPrice(totalDiscountPrice);
        productPurchaseInfoDto.setSellPrice(totalPrice - totalDiscountPrice + totalOptionCost);
        model.addAttribute("productPurchaseInfoDto", productPurchaseInfoDto);
    }

    @GetMapping("/productQna.do")
    public void productQna(@RequestParam("id") Long id,
                           @RequestParam(value = "page", required = false, defaultValue = "1")  int page,
                           Model model,
                           @AuthenticationPrincipal MemberDetails memberDetails,
                           HttpServletRequest httpServletRequest){
        Product product = productService.findById(id);
        List<ProductQuestion> productQuestions = productQuestionService.pquestionFindByProductid(product.getId());
        String url = httpServletRequest.getRequestURI() + "?id=" +id;
        int limit = 5;

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<ProductQuestionDto> productQuestionPage = productQuestionService.productQuestionDtoFindAllByProductId(pageable, product.getId());
        List<ProductQuestionDto> productQuestionDtos = productQuestionService.productQuestionDtoFindAllByProductId(product.getId());

        String pagebar = HelloMvcUtils.getPagebar(
                page, limit, productQuestions.size() , url);

        Member member = memberDetails.getMember();

        model.addAttribute("pagebar", pagebar);
        model.addAttribute("questions", productQuestionPage.getContent());
        model.addAttribute("productId", id);
        model.addAttribute("member", member);
        model.addAttribute("totalCount", productQuestionDtos.size());

    }

    @GetMapping("/productQnaCreatePage.do")
    public void productQnaCreatePage(@RequestParam("id") Long id,
                                     @RequestParam(value = "questionId", required = false, defaultValue = "0") Long questionId,
                                     @RequestParam(value = "productId", required = false, defaultValue = "0") Long productId,
                                     @AuthenticationPrincipal MemberDetails memberDetails,
                                     Model model){
        Member member = memberDetails.getMember();

        model.addAttribute("id", id);
        model.addAttribute("Member", member);
        if(questionId != 0L){
            model.addAttribute("questionId", questionId);
        }
        model.addAttribute("productId", productId);

    }

    @PostMapping("/productQnaCreate.do")
    public String productQnaCreate(@RequestParam("productNo") Long productId,
                                   @RequestParam(value = "questionId", required = false, defaultValue = "0") Long questionId,
                                   @Valid ProductQuestionCreateDto productQuestionCreateDto,
                                   BindingResult bindingResult,
                                   @AuthenticationPrincipal MemberDetails memberDetails
                                    ) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Product product = productService.findById(productId);

        if(questionId != 0L){
            productQuestionCreateDto.setId(questionId);
        }

        Member member = memberDetails.getMember();

        productQuestionCreateDto.setProduct(product);
        productQuestionCreateDto.setMember(member);
        productQuestionCreateDto.setQRegdate(LocalDate.now());
        productQuestionService.createQuestion(productQuestionCreateDto);

        return "redirect:/product/productQna.do?id=" + productId;
    }


}
