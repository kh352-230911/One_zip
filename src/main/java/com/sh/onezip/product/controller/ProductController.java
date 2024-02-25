package com.sh.onezip.product.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.cart.entity.Cart;
import com.sh.onezip.cart.service.CartService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import com.sh.onezip.orderproduct.service.OrderProductService;
import com.sh.onezip.product.dto.ProductCartCreateDto;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.dto.ProductPreVerifyDto;
import com.sh.onezip.product.dto.ProductPurchaseInfoDto;
import com.sh.onezip.productLog.entity.ProductLog;
import com.sh.onezip.productLog.entity.RefundCheck;
import com.sh.onezip.productLog.entity.ShppingState;
import com.sh.onezip.productLog.service.ProductLogService;
import com.sh.onezip.productReview.dto.ProductReviewCreateDto;
import com.sh.onezip.productReview.dto.ProductReviewDto;
import com.sh.onezip.productReview.entity.ProductReview;
import com.sh.onezip.productReview.service.ProductReviewService;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.service.ProductOptionService;
import com.sh.onezip.productquestion.dto.ProductQuestionCreateDto;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.service.ProductService;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productquestion.repository.ProductQuestionRepository;
import com.sh.onezip.productquestion.service.ProductQuestionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.sh.onezip.common.HelloMvcUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductReviewService productReviewService;
    @Autowired
    ProductQuestionService productQuestionService;
    @Autowired
    ProductLogService productLogService;
    @Autowired
    OrderProductService orderProductService;
    @Autowired
    ProductOptionService productOptionService;
    @Autowired
    CartService cartService;

    @Autowired
    MemberService memberService;

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

        model.addAttribute("selectedPriceRange", String.valueOf(refPrice));
        System.out.println("Selected Price Range: " + refPrice);
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
    public void productDetail(@RequestParam("id") Long id,
                              Model model) {
        ProductDetailDto productDetailDto = productService.productDetailDtofindById(id);
        List<ProductOption> productOptions = productOptionService.findAllByProductId(productDetailDto.getId());
        productDetailDto.setProductOptions(productOptions);

        model.addAttribute("product", productDetailDto);
        log.debug("product = {}", productDetailDto);
    }

    @PostMapping("/productPurchaseInfo.do")
    public void productPurchaseInfo(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("productId") Long id,
            @RequestParam("productQuantity") int productQuantity,
            @RequestParam(value = "selectOption", required = false) String selectOption,
            HttpServletRequest httpServletRequest,
            Model model){
        Member member = memberDetails.getMember();
        List<ProductOption> productOptions = productOptionService.findAllByProductId(id);
        Product product = productService.findById(id).orElse(null);

        Long Optionid = 0L;
        try {
            Optionid = Long.parseLong(httpServletRequest.getParameter("selectOption").split("#")[0]);
        } catch (Exception e) {}

//         index-0: option id, index-1: option additional cost
        String[] selectOptionArr = selectOption.split("#");
        Long optionId = Long.parseLong(selectOptionArr[0]); // 선택 옵션 고유번호
        int additionalCost = Integer.parseInt(selectOptionArr[1]); // 선택 옵션 추가 요금

        ProductOption productOption = productOptionService.findById(Optionid);


        ProductPurchaseInfoDto productPurchaseInfoDto = productService.productPurchaseInfoDtofindById(id);

        productPurchaseInfoDto.setOptionId(optionId);
        productPurchaseInfoDto.setAdditionalCost(additionalCost);

        int totalOptionCost = 0;
        if(productOption != null){
            productPurchaseInfoDto.setProductOption(productOption);
            totalOptionCost = productPurchaseInfoDto.getProductOption().getOptionCost() * productQuantity;
            productPurchaseInfoDto.setTotalOptionCost(totalOptionCost);
        }

        productPurchaseInfoDto.setMember(member);
        productPurchaseInfoDto.setProductQuantity(productQuantity);

        productPurchaseInfoDto.setProductOptionLists(productOptions);

        int totalPrice = productPurchaseInfoDto.getProductPrice() * productQuantity;

        double discountRate = (double)productPurchaseInfoDto.getDiscountRate()/100;
        productPurchaseInfoDto.setTotalProductPrice(totalPrice);
        int totalDiscountPrice = (int)((totalPrice) * (discountRate));
        productPurchaseInfoDto.setTotalDiscountPrice(totalDiscountPrice);
        productPurchaseInfoDto.setSellPrice(totalPrice - totalDiscountPrice + totalOptionCost);
        model.addAttribute("productPurchaseInfoDto", productPurchaseInfoDto);
    }

    @GetMapping("/productQna.do")
    public void productQna(@RequestParam("id") Long id,
                           Model model,
                           @AuthenticationPrincipal MemberDetails memberDetails,
                           HttpServletRequest httpServletRequest){
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

        Member member = memberDetails.getMember();

        model.addAttribute("pagebar", pagebar);
        model.addAttribute("questions", productQuestionPage.getContent());
        model.addAttribute("productId", id);
        model.addAttribute("member", member);
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
                                        HttpServletRequest httpServletRequest,
                                        Model model){
        Member member = memberDetails.getMember();
        Long reviewId = 0L;
        Long productId = 0L;
        try {
            reviewId = Long.parseLong(httpServletRequest.getParameter("reviewId"));
        } catch (NumberFormatException ignore) {}
        try {
            productId = Long.parseLong(httpServletRequest.getParameter("productId"));
        } catch (NumberFormatException ignore) {}
        model.addAttribute("id", id);
        model.addAttribute("Member", member);
        if(reviewId != 0L) {
            model.addAttribute("reviewId", reviewId);
        }
        model.addAttribute("productId", productId);

    }

    @PostMapping("/productReviewCreate.do")
    public String productCreateReview(@RequestParam("productNo") Long productId,
                                      @Valid ProductReviewCreateDto productReviewCreateDto,
                                      BindingResult bindingResult,
                                      HttpServletRequest httpServletRequest,
                                      @AuthenticationPrincipal MemberDetails memberDetails,
                                      Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Long reviewId = 0L;

        try {
            reviewId = Long.parseLong(httpServletRequest.getParameter("reviewId"));
        } catch (NumberFormatException ignore) {}

        Optional<Product> productOpt = productService.findById(productId);
        Product product = productOpt.orElse(null);

        ProductReview productReview = null;

        if(reviewId != 0L){
            productReviewCreateDto.setId(reviewId);
        }

        Member member = memberDetails.getMember();
        productReviewCreateDto.setProduct(product);
        productReviewCreateDto.setMemberId(member.getMemberId());
        productReviewCreateDto.setReviewRegdate(LocalDate.now());
        productService.createReview(productReviewCreateDto);

//        redirectAttributes.addAttribute("msg","dev등록에 성공했습니다.");
        return "redirect:/product/productReview.do?id=" + productId;
    }

    @GetMapping("/productQnaCreatePage.do")
    public void productQnaCreatePage(@RequestParam("id") Long id,
                                     @AuthenticationPrincipal MemberDetails memberDetails,
                                     HttpServletRequest httpServletRequest,
                                     Model model){
        Member member = memberDetails.getMember();
        Long questionId = 0L;
        Long productId = 0L;
        try {
            questionId = Long.parseLong(httpServletRequest.getParameter("questionId"));
        } catch (NumberFormatException ignore) {}
        try {
            productId = Long.parseLong(httpServletRequest.getParameter("productId"));
        } catch (NumberFormatException ignore) {}
        model.addAttribute("id", id);
        model.addAttribute("Member", member);
        if(questionId != 0L){
            model.addAttribute("questionId", questionId);
        }
        model.addAttribute("productId", productId);

    }

    @PostMapping("/productQnaCreate.do")
    public String productQnaCreate(@RequestParam("productNo") Long productId,
                                   @Valid ProductQuestionCreateDto productQuestionCreateDto,
                                   BindingResult bindingResult,
                                   HttpServletRequest httpServletRequest,
                                   @AuthenticationPrincipal MemberDetails memberDetails,
                                   Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Long questionId = 0L;

        try {
            questionId = Long.parseLong(httpServletRequest.getParameter("questionId"));
        } catch (NumberFormatException ignore) {}

        Optional<Product> productOpt = productService.findById(productId);
        Product product = productOpt.orElse(null);

        ProductQuestion productQuestion = null;

        if(questionId != 0L){
            productQuestionCreateDto.setId(questionId);
        }

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
//        model.addAttribute("reviewerId", reviewerId);
//        model.addAttribute("productId", productId);
        if ("update".equals(action)) {
            ProductReview productReview = productReviewService.findById(reviewId);
            redirectAttributes.addFlashAttribute("reviewId", reviewId);
            redirectAttributes.addFlashAttribute("productReview", productReview);
            return "redirect:/product/productReviewCreatePage.do?id=" + productId;
        } else if ("delete".equals(action)) {
            productReviewService.deleteById(reviewId);
            return "redirect:/product/productReview.do?id=" + productId;
        }
        return "redirect:/product/productList.do?id=" + productId;
    }

    @PostMapping("/qnafurcate.do")
    public String qnafurcate(@RequestParam("action") String action,
                             @RequestParam("questionId") Long questionId,
                             @RequestParam("questionerId") String questionerId,
                             @RequestParam("productId") Long productId,
                             Model model,
                             RedirectAttributes redirectAttributes){
        model.addAttribute("questionId", questionId);
        model.addAttribute("productId", productId);
        if ("update".equals(action)) {
            ProductQuestion productQuestion = productQuestionService.findQuestuinById(questionId);
            redirectAttributes.addFlashAttribute("questionId", questionId);
            redirectAttributes.addFlashAttribute("productQuestion", productQuestion);
            return "redirect:/product/productQnaCreatePage.do?id=" + productId;
        } else if ("delete".equals(action)) {
            productQuestionService.deleteQuestionById(questionId);
            return "redirect:/product/productQna.do?id=" + productId;
        }
        return "redirect:/product/productList.do?id=" + productId;
    }

    @PostMapping("/productPayment.do")
    public void productPayment(@RequestParam ("sellPrice") int sellPrice,
                               @RequestParam ("productName") String productName,
                               @RequestParam ("shippingRequest") String shippingRequest,
                               @RequestParam ("productId") Long productId,
                               @RequestParam ("productQuantity") int productQuantity,
                               @AuthenticationPrincipal MemberDetails memberDetails,
                               HttpServletRequest httpServletRequest,
                               Model model){
        Member member = memberDetails.getMember();

        Long productOptId = 0L;

        try {
            productOptId = Long.parseLong(httpServletRequest.getParameter("productOptId"));
        } catch (NumberFormatException ignore) {}

        ProductLog newProductLog = ProductLog
                .builder()
                .memberId(member.getMemberId())
                .purchaseDate(LocalDate.now().toString())
                .shppingState(ShppingState.R)
                .refundCheck(RefundCheck.N)
                .memo(shippingRequest)
                .arrAddr(member.getMemberAddr())
                .totalPayAmount(sellPrice)
                .build();
        ProductLog productLog = productLogService.createProductLog(newProductLog);
        model.addAttribute("member", member);
        model.addAttribute("productName", productName);
        model.addAttribute("productLog", productLog);
        model.addAttribute("productOptId", productOptId);
        model.addAttribute("productId", productId);
        model.addAttribute("productQuantityd", productQuantity);

    }

    @PostMapping("/productPreverify.do")
    public String productPreverify(@RequestBody Map<String, String> requestData,
                                   @AuthenticationPrincipal MemberDetails memberDetails){
        System.out.println("requestData: Controller" + requestData);
        Member member = memberDetails.getMember();
        productService.preVerify(requestData, member);

        return "";
    }

    @PostMapping("/productPostverify.do")
    public ResponseEntity<?>  productPostverify(@RequestBody Map<String, String> requestData,
                                  @AuthenticationPrincipal MemberDetails memberDetails){
        Member member = memberDetails.getMember();
        boolean satisfyVerify = productService.postVerify(requestData, member);
            return ResponseEntity.ok(Map.of("result", satisfyVerify));
    }

    @PostMapping("/productOrderReverse.do")
    public void productOrderReverse(@RequestBody Map<String, String> requestData){
        orderProductService.orderRollBack(requestData);
    }

//    @PostMapping("/productPayment.do")
//    public void productPayment(@RequestParam ("sellPrice") String sellPrice,
//                               @RequestParam ("productName") String productName,
//                               @RequestParam ("shippingRequest") String shippingRequest,
//                               @RequestParam ("productId") Long productId,
//                               @RequestParam ("productQuantity") int productQuantity,
//                               @AuthenticationPrincipal MemberDetails memberDetails,
//                               HttpServletRequest httpServletRequest,
//                               Model model){
//        Member member = memberDetails.getMember();
//
//        Long productOptId = 0L;
//
//        try {
//            productOptId = Long.parseLong(httpServletRequest.getParameter("productOptId"));
//        } catch (NumberFormatException ignore) {}
//
//        ProductLog newProductLog = ProductLog
//                .builder()
//                .memberId(member.getMemberId())
//                .purchaseDate(LocalDate.now().toString())
//                .shppingState(ShppingState.R)
//                .refundCheck(RefundCheck.N)
//                .memo(shippingRequest)
//                .arrAddr(member.getMemberAddr())
//                .totalPayAmount(Integer.parseInt(sellPrice))
//                .build();
//        ProductLog productLog = productLogService.createProductLog(newProductLog);
//        model.addAttribute("member", member);
//        model.addAttribute("productName", productName);
//        model.addAttribute("productLog", productLog);
//        model.addAttribute("productOptId", productOptId);
//        model.addAttribute("productId", productId);
//        model.addAttribute("productQuantity", productQuantity);
//
//    }

    @PostMapping("/productCart.do")
    public void productCart(ProductCartCreateDto productCartCreateDto,
                            @AuthenticationPrincipal MemberDetails memberDetails,
                            Model model){
        Member member = memberDetails.getMember();
        Cart cart = cartService.convertToCart(productCartCreateDto);
        List<Cart> loginMemberCartList = cartService.findAllByMemberId(member.getMemberId());
        cart.setMember(memberDetails.getMember());
        loginMemberCartList.add(cart);
        model.addAttribute("loginMemberCartList", loginMemberCartList);
    }

//    선물하기/ 구매하기 분기 처리 RequestMapping Method
//    @PostMapping("/productDetailFurcate.do")
//    public String productDetailFurcate(@RequestParam("action") String action,
//                                     @RequestParam("productId") Long productId,
//                                     RedirectAttributes redirectAttributes){
//        if ("purchase".equals(action)) {
//            return "redirect:product/productPurchaseInfo.do?id=" + ;
//        } else if ("gift".equals(action)) {
//            return "redirect:/product/productQna.do?id=" + productId;
//        }
//
//    }

}
