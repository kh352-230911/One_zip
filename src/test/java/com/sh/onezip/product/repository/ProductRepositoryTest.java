package com.sh.onezip.product.repository;

import com.sh.onezip.businessmember.entity.Businessmember;
import com.sh.onezip.businessmember.repository.BusinessmemberRepository;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BusinessmemberRepository businessmemberRepository;

    @DisplayName("productRepository빈은 null이 아닙니다.")
    @Test
    public void test0(){
        assertThat(productRepository).isNotNull();
    }

    @DisplayName("사업자 회원은 상품을 등록하고 등록한 상품을 조회할 수 있습니다.")
    @Test
    public void test1(){

        Businessmember bizman = businessmemberRepository.findByBizMemberId("biz1234");

        Product product = Product.builder()
                .businessmember(bizman)
                .productName("홍찰찰의 찰보리 빵")
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
    public void test2(){
        Businessmember bizman = businessmemberRepository.findByBizMemberId("biz1234");
        List<Product> products = productRepository.findByBusinessmemberBizMemberId(bizman.getBizMemberId());
        assertThat(products)
                .allSatisfy((product-> {
                    assertThat(product).isNotNull();
                    assertThat(product.getBizMemberId()).isEqualTo("biz1234");
                }));
    }


}