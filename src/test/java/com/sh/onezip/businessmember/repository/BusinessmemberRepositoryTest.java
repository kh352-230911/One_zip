package com.sh.onezip.businessmember.repository;

import com.sh.onezip.businessmember.entity.BizAccess;
import com.sh.onezip.businessmember.entity.Businessmember;
import com.sh.onezip.businessmember.service.BusinessmemberService;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@Transactional
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BusinessmemberRepositoryTest {

    @Autowired
    BusinessmemberRepository businessmemberRepository;
    @Autowired
    ProductRepository productRepository;

    @DisplayName("BusinessmemberRepository빈은 null이 아닙니다.")
    @Test
    public void test0() {
        assertThat(businessmemberRepository).isNotNull();
    }

    @DisplayName("사업자 회원가입")
    @Test
    public void test1() {
        // given
        Businessmember businessmember = Businessmember.builder()
                .bizMemberId("moneylove2")
                .bizPassword("qwerty")
                .bizName("홍박사")
                .bizPhone("010-1234-7777")
                .bizAddr("경기도 성남시 분당구")
                .bizLicense("제출 완료")
                .bizRegNo("13SDF98")
                .bizRegStatus(BizAccess.D)
                .build();
        // when & then
        businessmemberRepository.save(businessmember);
        Businessmember businessmember2 = businessmemberRepository.findByBizMemberId(businessmember.getBizMemberId());
        assertThat(businessmember2).isNotNull();
        assertThat(businessmember2.getBizName()).isEqualTo("홍박사");

    }

    //    @DisplayName("사업자는 회원 가입 후 추후 변경 된 사업자 정보를 수정 할 수 있습니다.")
//    @Test
//    public void test2() {
//        // given
//        final Businessmember businessmember = Businessmember.builder()
//                .bizName("홍박사")
//                .bizAddr("경기도 성남시 분당구")
//                .bizRegNo("13DFG3489")
//                .build();
//        businessmemberRepository.save(businessmember);
//        // when
//        String newBizName = "이박사";
//        String newBizAddr = "서울시 강북구";
//        String newBizRegNo = "12QWE8893";
//        businessmember.setBizName(newBizName);
//        businessmember.setBizAddr(newBizAddr);
//        businessmember.setBizRegNo(newBizRegNo);
//        businessmemberRepository.save(businessmember);
//
//        // then
//        Optional<Businessmember> maybeBusinessmember = businessmemberRepository.findByBizName(businessmember.getBizName());
//        Businessmember businessmember1 = maybeBusinessmember.orElse(null);
//        assertThat(businessmember1)
//                .isNotNull()
//                .satisfies((_businessmember) -> {
//                    assertThat(_businessmember.getBizMemberId()).isEqualTo(businessmember.getBizName());
//                    assertThat(_businessmember.getBizName()).isEqualTo(newBizName);
//                    assertThat(_businessmember.getBizAddr()).isEqualTo(newBizAddr);
//                    assertThat(_businessmember.getBizRegNo()).isEqualTo(newBizRegNo);
//                });
//    }
    @DisplayName("사업자는 상품을 등록 및 조회를 할 수 있습니다.")
    @Test
    public void test3() {
        Businessmember businessmember = businessmemberRepository.findByBizMemberId("moneylove");

        Product product = Product.builder()
                .businessmember(businessmember)
                .productName("깨강정")
                .productTypecode(ProductType.O)
                .productPrice(3_000)
                .discountRate(10)
                .build();

        productRepository.save(product);
        Optional<Product> productOpt = productRepository.findById(product.getId());
        Product product2 = productOpt.orElse(null);

        assertThat(product2.getId())
                .isNotNull()
                .isEqualTo(product.getId());
    }

    @DisplayName("사업자는 등록 된 상품 내역을 수정 할 수 있습니다.")
    @Test
    public void test4() {
        Businessmember businessmember = Businessmember.builder().bizMemberId("moneylove").build();
        List<Product> products = productRepository.findByBusinessmemberBizMemberId(businessmember.getBizMemberId());
        for (Product product : products) {
            if (product.getProductName().equals("맛도리 닭갈비")) {
                product.setProductName("진짜 맛없는 닭갈비");
                product.setProductPrice(1_000);
                productRepository.save(product);
            }
        }
        List<Product> afterProduct = productRepository.findByBusinessmemberBizMemberId(businessmember.getBizMemberId());
        for (Product product : afterProduct) {
            assertThat(product.getProductName()).isNotEqualTo("맛도리 닭갈비");
        }
    }

    @DisplayName("사업자는 등록 된 상품 내역을 삭제 할 수 있습니다.")
    @Test
    public void test5() {
        String bizMemberId = "moneylove";
        Businessmember businessmember = Businessmember.builder().bizMemberId(bizMemberId).build();

        List<Product> products = productRepository.findByBusinessmemberBizMemberId(businessmember.getBizMemberId());
        for (Product product : products) {
            if (product.getBusinessmember().getBizMemberId().equals(bizMemberId) && product.getProductName().equals("맛도리 닭갈비")) {
                productRepository.delete(product);
            }
        }
    }
}
//        List<Product> deletedProducts = productRepository.findByBusinessmemberBizMemberId(businessmember.getBizMemberId());
//        assertThat(deletedProducts).noneMatch(product -> product.getProductName().equals("진짜 맛없는 닭갈비"));


// 사업자는 고객이 주문한 주문 전체 세부내역을 볼 수 있습니다.
// 사업자는 고객이 작성한 문의글의 전체 내역을 볼 수 있습니다.
// 사업자는 고객이 작성한 문의글에 답변을 달 수 있습니다.
// 사업자는 고객이 작성한 문의글에 답변을 수정 할 수 있습니다.
// 사업자는 상품을 구매한 고객이 작성한 리뷰글의 전체 내역을 볼 수 있습니다.
// 사업자는 상품을 구매한 고객이 작성한 리뷰글에 댓글을 달 수 있습니다.
// 사업자는 상품을 구매한 고객이 작성한 리뷰글에 댓글을 수정 할 수 있습니다.



