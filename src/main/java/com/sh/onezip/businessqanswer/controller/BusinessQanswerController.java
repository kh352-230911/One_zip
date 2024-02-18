package com.sh.onezip.businessqanswer.controller;

import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.businessproduct.service.BusinessmemberService;
import com.sh.onezip.common.HelloMvcUtils;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.service.ProductService;
import com.sh.onezip.productanswer.dto.ProductAnswerCreateDto;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productanswer.service.ProductAnswerService;
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
import java.util.Optional;

@Controller
@RequestMapping("/businessmanagement")
@Slf4j
@Validated
public class BusinessQanswerController {
    @Autowired
    BusinessmemberService businessmemberService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductQuestionService productQuestionService;

    @GetMapping("/businessqanswerlist.do")
    public void businessqanswerlist(@RequestParam("bizMemberId") String bizMemberId, Model model, HttpServletRequest httpServletRequest) {
        // 하드코딩
        Businessmember businessmember = new Businessmember();
        businessmember.setBizMemberId("moneylove");
        // 요청 파라미터로부터 사업자 아이디(bizMemberId)를 가져옵니다.
        try {
            bizMemberId = httpServletRequest.getParameter("bizMemberId");
        } catch (NumberFormatException ignore) {
        }
        // 회원 문의 내역(사업자 : moneylove)
        List<ProductQuestionDto> productQuestionLists = productService.findByQuestion(businessmember.getBizMemberId());
        System.out.println(productQuestionLists + "!!!");
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
        Page<ProductQuestionDto> productQuestionPage = productService.findAllQuestion(pageable, businessmember.getBizMemberId());
        System.out.println(productQuestionPage + "리스트가 잘나오나?");
        // 페이지 바 생성
        String pagebar = HelloMvcUtils.getPagebar(realPage, limit, productQuestionLists.size(), url);
        System.out.println(pagebar + "페이지바");
        model.addAttribute("pagebar", pagebar); // view , controller
        model.addAttribute("questions", productQuestionPage.getContent()); // 사업자 로그인 시 전체 질문 목록
        model.addAttribute("totalCount", productQuestionLists.size()); // 사업자 로그인 시 전체 질문 수
    }

    @GetMapping("/businessqanswerdetail.do")
    public void businessqanswerdetail(@RequestParam("id") Long id, Model model) {
        ProductQuestionDto productQuestionDto = productQuestionService.findByProductQuestionAnswerId(id);
        model.addAttribute("question", productQuestionDto);
    }
}
//    @PostMapping("/businessqanswerdetail.do")
//    public String businessqanswerdetail(@RequestParam("id") Long id, Model model,
//                                        @Valid ProductQuestionCreateDto productQuestionCreateDto,
//                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
//
//        if (bindingResult.hasErrors()) {
//            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
//        }
////        productQuestionService.findByProductQuestionAnswerId(id);
//        ProductQuestion productQuestion = new ProductQuestion();
//        productQuestionCreateDto.setProductAnswer(productQuestion.getProductAnswer());
//        productQuestionService.createAnswer(productQuestionCreateDto);
//        // 리다이렉트 후 사용자 피드백 설정
//        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 답글을 성공적으로 등록했습니다. 🎈🎈🎈");
//        return "redirect:/businessmanagement/businessqanswerdetail.do?id=" + id;
//    }

//    @PostMapping("/businessqanswerdetail.do")
//    public String businessqanswerdetail(@RequestParam("id") Long id, Model model,
//                                        @Valid ProductQuestionDto productQuestionDto, BindingResult bindingResult,
//                                        RedirectAttributes redirectAttributes) throws IOException {
//        if (bindingResult.hasErrors()) {
//            // 유효성 검사 실패 시 처리
//            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
//        }
//
//        // 질문에 대한 정보 가져오기
//        Optional<ProductQuestion> optionalProductQuestion = productQuestionService.findByDetailProductQuestionAnswerId(id);
//
//        if (optionalProductQuestion.isPresent()) {
//            ProductQuestion productQuestion = optionalProductQuestion.get();
//
//            // 답변 객체 가져오기
//            ProductAnswer productAnswer = productQuestion.getProductAnswer();
//
//            if (productAnswer != null) {
//                // 답변이 이미 존재하는 경우 업데이트
//                productAnswer.setAContent(productQuestionDto.getProductAnswer().getAContent());
//            } else {
//                // 답변이 존재하지 않는 경우 생성
//                productAnswer = new ProductAnswer();
//                productAnswer.setAContent(productQuestionDto.getProductAnswer().getAContent());
//                productAnswer.setProductQuestion(productQuestion);
//            }
//
//            // 리다이렉트 후 사용자 피드백 설정
//            redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 답글을 성공적으로 등록했습니다. 🎈🎈🎈");
//            return "redirect:/businessmanagement/businessqanswerdetail.do?id=" + id;
//        } else {
//            throw new RuntimeException("해당 질문을 찾을 수 없습니다.");
//        }
//    }

//    @PostMapping("/businessqanswerdetail.do")
//    public String businessqanswerdetail(@RequestParam("id") Long id, Model model,
//                                        @Valid ProductQuestionDto productQuestionDto,
//                                        BindingResult bindingResult,
//                                        RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            StringBuilder message = new StringBuilder();
//            bindingResult.getAllErrors().forEach((err) -> {
//                message.append(err.getDefaultMessage() + " ");
//            });
//            throw new RuntimeException(message.toString());
//        }
//
//        // productAnswer를 초기화하고, productQuestionDto에서 가져와서 할당
//        ProductAnswer productAnswer = null;
//        if (productQuestionDto != null) {
//            productAnswer = productQuestionDto.getProductAnswer();
//        }
//
//        // productAnswer가 null이면 처리할 작업 추가
//        if (productAnswer == null) {
//            // 처리할 작업 추가
//        } else {
//            // productAnswer를 사용하여 원하는 작업 수행
//            // 답변 객체의 ID 설정
//            productAnswer.setId(id);
//            // 수정하겠어.
//            ProductAnswer updatedAnswer = productAnswerService.qanswerupdate(productAnswer);
//            redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 답글을 성공적으로 수정했습니다. 🎈🎈🎈");
//            return "redirect:/businessmanagement/businessqanswerdetail.do?id=" + updatedAnswer.getId();
//        }
//
//}

//    @PostMapping("/businessqanswerdetail.do")
//    // 나는 Dto 클래스의 id를 불러올거다. (질문 고유번호 말이지.)
//    public String businessqanswerdetail(@RequestParam("id") Long id, Model model,
//                                        @Valid ProductQuestionDto productQuestionDto,
//                                        BindingResult bindingResult,
//                                        RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            StringBuilder message = new StringBuilder();
//            bindingResult.getAllErrors().forEach((err) -> {
//                message.append(err.getDefaultMessage() + " ");
//            });
//            throw new RuntimeException(message.toString());
//        }
//
//        ProductQuestion productQuestion = new ProductQuestion();
//        productQuestion.setId(id);
//        // 수정할 답변 가져올게
//        ProductAnswer productAnswer = productQuestionDto.getProductAnswer();
//        System.out.println(productAnswer + " 왜 안 나 오 니 ");
//        // 답변 객체가 null이 아닌 경우에만 setId 호출
//        if (productAnswer != null) {
//            // 답변 객체의 ID 설정
//            productAnswer.setId(id);
//            // 수정하겠어.
//            ProductAnswer updatedAnswer = productAnswerService.qanswerupdate(productAnswer);
//            System.out.println(updatedAnswer + " 폭 력 쓸 꽈 ! ");
//            redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 답글을 성공적으로 수정했습니다. 🎈🎈🎈");
//            return "redirect:/businessmanagement/businessqanswerdetail.do?id=" + updatedAnswer.getId();
//        } else {
//            // 답변 객체가 null이면 예외를 처리하거나 다른 작업을 수행할 수 있습니다.
//            throw new RuntimeException("답변 객체가 null입니다.");
//        }
//    }
//}





//        // 답변을 수정해볼거야. (Dto 안에는 ProductAnswer도 들어있어.)
//        ProductQuestion productQuestion = new ProductQuestion();
//        productQuestionDto.setProductAnswer(productQuestion.getProductAnswer());
//        ProductQuestion updateaContent = productAnswerService.qanswerupdate(productQuestion.getProductAnswer());
//        System.out.println(productQuestionDto);
//        // 리다이렉트후에 사용자피드백
//        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 답글을 성공적으로 수정했습니다. 🎈🎈🎈");
//        return "redirect:/businessmanagement/businessqanswerdetail.do?id=" + updateaContent.getId();

