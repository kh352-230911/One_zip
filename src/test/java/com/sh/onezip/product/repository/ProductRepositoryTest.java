package com.sh.onezip.product.repository;

import com.sh.onezip.businessmember.entity.Businessmember;
import com.sh.onezip.businessmember.repository.BusinessmemberRepository;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.productimage.entity.PImageType;
import com.sh.onezip.productimage.entity.ProductImage;
import com.sh.onezip.productimage.repository.ProductImageRepository;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    @Autowired
    ProductImageRepository productImageRepository;
    @Autowired
    ProductOptionRepository productOptionRepository;

    private List<Product> products;

    @DisplayName("productRepository빈은 null이 아닙니다.")
    @Test
    public void test0(){
        assertThat(productRepository)
                .isNotNull();
    }

    @DisplayName("상품에 이미지를 등록할 수 있습니다.")
    @Test
    public void test1(){

        Optional<Product> productOpt = productRepository.findById(1L);
        Product product = productOpt.orElse(null);

        ProductImage productImage = ProductImage.builder()
                .product(product)
                .originalFilename("이미지4 원본이름")
                .imageKey("이미지4")
                .imageUrl("www.이미지4")
                .imageType(PImageType.P)
                .build();

        productImageRepository.save(productImage);
        Optional<ProductImage> productImageOpt = productImageRepository.findById(productImage.getId());
        ProductImage productImage2 = productImageOpt.orElse(null);

        assertThat(productImage2.getId()).isEqualTo(productImage.getId());
    }


    @DisplayName("상품에 등록된 이미지를 조회할 수 있습니다.")
    @Test
    public void test2(){
        Optional<Product> productOpt = productRepository.findById(1L);
        Product product = productOpt.orElse(null);

        List<Optional<ProductImage>> productOpt2 = productImageRepository.findByProductId(product.getId());
        assertThat(productOpt2)
                .allSatisfy((_productImageOpt) -> {
                    ProductImage productImage = _productImageOpt.orElse(null);
                    assertThat(productImage.getProduct().getId()).isEqualTo(1L);
                    System.out.println(productImage.getOriginalFilename());
                });
    }

    @DisplayName("페이징 처리")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void test3(int page){
        // 한 페이지당 노출 상품 수
        final int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());

        Page<Product> pageResult = productRepository.findAll(pageable);

        // 페이징 결과와 페이지 내용을 출력.
        // 현재 페이지에 대한 회원 데이터가 리스트 형태로 반환 됨.
        List<Product> content = pageResult.getContent();
        // 페이징 결과에서 전체 페이지수를 반환
        int totalPages = pageResult.getTotalPages();
        // 페이징 결과에서 전체 요소 수를 반환
        long totalElements = pageResult.getTotalElements();
        // 페이징 결과에서 현재 페이지 번호를 반환
        int number = pageResult.getNumber();
        // 페이징 결과에서 현재 페이지의 요소수를 반환
        int numberOfElements = pageResult.getNumberOfElements();

        assertThat(content).isSortedAccordingTo(Collections.reverseOrder());
        assertThat(totalPages).isEqualTo(1);
        assertThat(totalElements).isEqualTo(6);
        assertThat(number).isEqualTo(page);
        assertThat(numberOfElements)
                .isEqualTo(content.size())
                .isLessThanOrEqualTo(pageSize);

    }

//    @DisplayName("상품에 옵션을 등록할 수 있습니다.")
//    @Test
//    public void test4(){
//        Optional<Product> productOpt = productRepository.findById(1L);
//        Product product = productOpt.orElse(null);
//        ProductOption productOption = ProductOption.builder()
//                .product(product)
//                .optionName("옵션명1,옵션명2,옵션명3")
//                .totalStock(10)
//                .optionCost(1000)
//                .neOption(true)
//                .build();
//        productOptionRepository.save(productOption);
//        Optional<ProductOption> productOptionOpt = productOptionRepository.findById(1L);
//        ProductOption productOption2 = productOptionOpt.orElse(null);
//        assertThat(productOption2.op).isEqualTo()
//
//
//    }




}