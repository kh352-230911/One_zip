//package com.sh.onezip.cart.controller;
//
//import com.sh.onezip.auth.vo.MemberDetails;
//import com.sh.onezip.cart.dto.CartDto;
////import com.sh.onezip.cart.entity.Cart;
//import com.sh.onezip.cart.service.CartService;
//import com.sh.onezip.member.entity.Member;
//import com.sh.onezip.member.service.MemberService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//@Controller
//@RequestMapping("/product")
//@Slf4j
//@Validated
//
//public class CartController {
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    CartService cartService;
//
//    // 한보경 코드 start
//
//
//    @GetMapping("/cart.do")
//    private String cart(@RequestParam(name = "memberId", required = false) String memberId, Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
//        // 로그인 유저 = 장바구니 접속 유저가 같는지?
//        if (memberId != null && memberDetails != null && memberDetails.getMember() != null && memberDetails.getMember().getMemberId().equals(memberId)) {
//            Member member = memberService.findByMemberId(memberId);
//            // 회원의 장바구니 가져오기
//            Cart cart = member.getCart();
//
//            // 장바구니 모두 가져오기
//            List<CartDto> carts = cartService.findAllCartlist(cart);
//
//            // 장바구니에 담긴 상품 총 금액
//            int totalPrice = 0;
//            for (CartDto cartlist : carts) {
//                totalPrice += cartlist.getCartQuantity() * cartlist.getProduct().getProductPrice();
//            }
//            System.out.println(memberId + " ???? ");
//            model.addAttribute("totalprice", totalPrice); // 총 금액
//            model.addAttribute("totalCount", cart.getCartStatus()); // 총 수량
//            model.addAttribute("Cartlist", cart); // 장바구니 리스트
//            model.addAttribute("memberId", memberService.findByMemberId(memberId)); // 회원아이디
//
////        } else {
////            // memberId가 null이거나 로그인한 회원의 memberId와 일치하지 않는 경우에 대한 처리
////            return "redirect:/common/error.do"; // 에러 페이지로 이동
////        }
//        }
//        return "redirect:/product/cart.do?memberId=" + memberId;
//    }
//
////    @PostMapping("/cart.do")
////    private String cart(@RequestParam("memberId") String memberId, @RequestParam("productName") String productName, int cartQuantity){
////    Member member = memberService.findByMemberId(memberId);
////    Cart cart = cartService.cartView(productName);
////    cartService.addCart(member, cart, cartQuantity);
////        return "redirect:/product/cart.do?memberId=" + memberId;
////    }
//
//    // 한보경 코드 end
//
//    // 김명준 코드 start
//
//
//
//    // 김명준 코드 end
//
//}
//
