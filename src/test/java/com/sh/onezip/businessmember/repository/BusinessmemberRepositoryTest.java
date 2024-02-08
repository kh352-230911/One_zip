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

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BusinessmemberRepositoryTest {

    @Autowired
    BusinessmemberRepository businessmemberRepository;
    @Autowired
    ProductRepository productRepository;

    @DisplayName("BusinessmemberRepository빈은 null이 아닙니다.")
    @Test
    public void test0(){
        assertThat(businessmemberRepository).isNotNull();
    }

    @DisplayName("사업자 회원 등록 및 조회")
    @Test
    public void test1(){
        Businessmember businessmember = Businessmember.builder()
                .bizMemberId("biz1234")
                .bizPassword("1234")
                .bizName("홍찰찰")
                .bizPhone("010-1234-1234")
                .bizAddr("경기도 용인시")
                .bizLicense("제출 완료")
                .bizRegNo("13DFG3489")
                .bizRegStatus(BizAccess.D)
                .build();
        businessmemberRepository.save(businessmember);
        Businessmember businessmember2 = businessmemberRepository.findByBizMemberId(businessmember.getBizMemberId());
        assertThat(businessmember2).isNotNull();
        assertThat(businessmember2.getBizName()).isEqualTo("홍찰찰");

    }

    @DisplayName("사업자 회원은 상품을 등록하고 등록한 상품을 조회할 수 있습니다.")
    @Test
    public void test2(){

        Businessmember bizman = businessmemberRepository.findByBizMemberId("biz1234");

        Product product = Product.builder()
                .businessmember(bizman)
                .productName("홍찰찰의 찰비빔면")
                .productTypecode(ProductType.O)
                .productPrice(1_000)
                .discountRate(10)
                .build();

        productRepository.save(product);
        Optional<Product> productOpt = productRepository.findById(product.getId());
        Product product2 = productOpt.orElse(null);

        assertThat(product2.getId())
                .isNotNull()
                .isEqualTo(product.getId());
    }

    @DisplayName("사업자 회원은 상품을 등록하고 등록한 상품을 조회할 수 있습니다.")
    @Test
    public void test3(){

        Businessmember bizman = businessmemberRepository.findByBizMemberId("biz1234");

        Product product = Product.builder()
                .businessmember(bizman)
                .productName("홍찰찰의 찰비빔면")
                .productTypecode(ProductType.O)
                .productPrice(1_000)
                .discountRate(10)
                .build();

        productRepository.save(product);
        Optional<Product> productOpt = productRepository.findById(product.getId());
        Product product2 = productOpt.orElse(null);

        assertThat(product2.getId())
                .isNotNull()
                .isEqualTo(product.getId());
    }

    @DisplayName("사업자의 Id로 사업자가 등록한 상품의 정보를 읽어올 수 있습니다.")
    @Test
    public void test4(){
        Businessmember bizman = businessmemberRepository.findByBizMemberId("biz1234");
        List<Product> products = productRepository.findByBusinessmemberBizMemberId(bizman.getBizMemberId());
        assertThat(products)
                .allSatisfy((product-> {
                    assertThat(product).isNotNull();
                    assertThat(product.getBusinessmember().getBizMemberId()).isEqualTo("biz1234");
                    System.out.println(product);
                }));
    }


}