package com.sh.onezip.product.repository;

import com.sh.onezip.businessmember.entity.Businessmember;
import com.sh.onezip.businessmember.repository.BusinessmemberRepository;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
//import com.sh.onezip.productimage.repository.ProductImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BusinessmemberRepository businessmemberRepository;
//    @Autowired
//    ProductImageRepository productImageRepository;

    private List<Product> products;

    @DisplayName("productRepository빈은 null이 아닙니다.")
    @Test
    public void test0(){
        assertThat(productRepository)
                .isNotNull();
    }


//    @DisplayName("상품에 등록된 이미지를 조회할 수 있습니다.")
//    @Test
//    public void test1(){
//        productRepository.findById("")
//    }


}