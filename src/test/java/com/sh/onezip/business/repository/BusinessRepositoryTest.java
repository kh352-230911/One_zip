package com.sh.onezip.business.repository;

import com.sh.onezip.business.entity.BizAccess;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BusinessRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private ProductRepository productRepository;

    // HBK start
    @DisplayName("BusinessRepository빈은 null이 아니다.")
    @Test
    void test0(){
        assertThat(businessRepository).isNotNull();
    }

    @DisplayName("일반회원은 사업자 회원 등록 후 관리자에게 권한을 요청 할 수 있습니다.")
    @Test
    void test1(){
    // 1. 일반회원으로 로그인을 한 회원 찾기
    Member member = memberRepository.findByMemberId("biztest7");
    // 테스트 중에 기대되는 결과와 실제 결과를 비교하여 테스트를 수행 assertThat()
    assertThat(member).isNotNull();

    // 2. 사업자 등록을 위한 사업자 정보 생성
        Business business = Business.builder()
                .member(member)
                .bizName("(주)고양이")
                .bizRegNo("123-45-876")
                .bizRegStatus(BizAccess.W) // 대기 상태로 설정
                .build();

    // 3. 사업자 등록 정보 저장
     businessRepository.save(business);

}
    @DisplayName("사업자는 추후 변경 된 사업자 정보를 수정 할 수 있습니다.")
    @Test
    void test2(){
        // 사업자회원고유번호로 사업자 회원 찾기
        Optional<Business> business = businessRepository.findById(23L);
        // 테스트 중에 기대되는 결과와 실제 결과를 비교하여 테스트를 수행 assertThat()
        assertThat(business).isPresent(); // businessOptional이 null이 아니어야 함

        // 찾은 사업자 엔티티가 존재하는 경우 .isPresent -> 값이 존재 하는지
        if (business.isPresent()) {
            // 기존의 사업자 엔티티를 가져옴
            Business newbusiness = business.get();

            // 사업자 정보 수정
            newbusiness.setBizName("(주)강아지");
            newbusiness.setBizRegNo("123-77-777");

            // 수정된 사업자 엔티티를 저장
            businessRepository.save(newbusiness);
        }
    }
    @DisplayName("사업자 상품 등록")
    @Test
    void test3(){
        Business business = businessRepository.findByBizName("(주)강아지");
        Member member = memberRepository.findByMemberId("biztest7");
        assertThat(business).isNotNull();

        Product product = Product.builder()
                .member(member)
                .productName("스즈메 의자")
                .productTypecode(ProductType.FU)
                .productPrice(20000)
                .discountRate(10)
                .build();
        productRepository.save(product);
    }
    @DisplayName("사업자 상품 내역 수정")
    @Test
    void test4(){
        Optional<Product> productOptional = productRepository.findById(282L);
        assertThat(productOptional).isPresent();

        if(productOptional.isPresent()) {
            Product newproduct = productOptional.get();

            newproduct.setProductName("JMT맛있는 닭갈비");
            newproduct.setProductTypecode(ProductType.FO);

            productRepository.save(newproduct);
        }
    }
    @DisplayName("사업자 등록 상품 전체 조회")
    @Test
    void test5(){
        // 컨트롤러에서 바로 실행
    }
    @DisplayName("사업자 상품 삭제")
    @Test
    void test6(){
        // 컨트롤러에서 바로 실행
    }
    @DisplayName("상품 문의글 전체 조회")
    @Test
    void test7(){
        // 컨트롤러에서 바로 실행
    }
    @DisplayName("상품 문의글 답변 작성")
    @Test
    void test8(){

    }
    @DisplayName("상품 문의글 답변 수정")
    @Test
    void test9(){

    }
    @DisplayName("상품 리뷰글 전체 조회")
    @Test
    void test10(){
        // 컨트롤러에서 바로 실행
    }
    @DisplayName("상품 구매 주문 접수 처리")
    @Test
    void test11(){

    }
    @DisplayName("상품 구매 배송 완료 처리")
    @Test
    void test12(){

    }
    @DisplayName("주문 접수 및 판매 된 상품 전체 내역")
    @Test
    void test13(){
        // 컨트롤러에서 바로 실행
    }

}
