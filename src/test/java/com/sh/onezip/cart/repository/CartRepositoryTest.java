//package com.sh.onezip.cart.repository;
//
//import com.sh.onezip.product.repository.ProductRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class CartRepositoryTest {
//
//    @Autowired
//    ProductRepository productRepository;
//    @Autowired
//    CartRepository cartRepository;
//
//    @DisplayName("CartRepository빈은 null이 아닙니다.")
//    @Test
//    public void test0(){
//        assertThat(cartRepository).isNotNull();
//    }
//}
//// 회원은 장바구니 페이지에서 상품명, 수량, 재고 수량,  옵션 명, 총 금액을 확인할 수 있습니다.
//// 회원은 구매하고 싶은 상품을 장바구니에 담을 수 있습니다.
//// 회원은 장바구니에 담아놓은 상품을 삭제 할 수 있습니다.
//// 회원은 장바구니에 담은 상품의 내역을 수정 할 수 있습니다.
//
