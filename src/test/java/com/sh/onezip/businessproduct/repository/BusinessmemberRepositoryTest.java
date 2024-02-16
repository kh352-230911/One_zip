package com.sh.onezip.businessproduct.repository;

import com.sh.onezip.businessproduct.entity.BizAccess;
import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.product.repository.ProductRepository;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productanswer.repository.ProductAnswerRepository;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productquestion.repository.ProductQuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//@Transactional
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BusinessmemberRepositoryTest {

    @Autowired
    BusinessmemberRepository businessmemberRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductQuestionRepository productQuestionRepository;
    @Autowired
    ProductAnswerRepository productAnswerRepository;

    @DisplayName("BusinessmemberRepository빈은 null이 아닙니다.")
    @Test
    public void test0() {
        assertThat(businessmemberRepository).isNotNull();
    }

    @DisplayName("사업자 회원 등록 및 조회")
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

    @DisplayName("사업자는 상품을 등록 할 수 있습니다.")
    @Test
    public void test2() {
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
    public void test3() {
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
    public void test4() {
        String bizMemberId = "moneylove";
        Businessmember businessmember = Businessmember.builder().bizMemberId(bizMemberId).build();

        List<Product> products = productRepository.findByBusinessmemberBizMemberId(businessmember.getBizMemberId());
        for (Product product : products) {
            if (product.getBusinessmember().getBizMemberId().equals(bizMemberId) && product.getProductName().equals("맛도리 닭갈비")) {
                productRepository.delete(product);
            }
        }
    }
    @DisplayName("사업자는 등록된 상품 내역을 조회할 수 있습니다. - 사업자 id로 조회")
    @Test
    public void test5(){
        String bizMemberId = "moneylove";
        // moneylove가 상품을 등록했어요.
        List<Product> products = productRepository.findByBusinessmemberBizMemberId(bizMemberId);
        assertThat(products).allSatisfy(product -> {
            assertThat(product).isNotNull();
            assertThat(product.getBusinessmember().getBizMemberId()).isEqualTo(bizMemberId);
        });
    }
    @DisplayName("사업자는 고객이 주문한 주문 전체 세부내역을 볼 수 있습니다.")
    @Test
    public void test6(){
        // 주문일자, 주문상품 정보, 상품 수량

    }
    @DisplayName("사업자는 고객이 작성한 문의글의 전체 내역을 볼 수 있습니다.")
    @Test
    public void test7(){
        // tb_product 상품고유번호 id = tb_pquestions 상품고유번호 id2 비교해보기
        Long id = 142L;
        List<ProductQuestion> productQuestions = productRepository.findByAllBusinessProductQuestion(id);
        assertThat(productQuestions).allSatisfy(productQuestion -> {
            assertThat(productQuestion).isNotNull();
            assertThat(productQuestion.getId()).isEqualTo(id);
        });
    }
    @DisplayName("사업자는 고객이 작성한 문의글에 답변을 달 수 있습니다.")
    @Test
    public void test8(){
        // 사업자 로그인 했음 moneylove
        String bizMemberId = "moneylove2";
        Businessmember businessmember = Businessmember.builder().bizMemberId(bizMemberId).build();
        // moneylove가 등록한 상품고유번호는 152번이야.
        Long id = 11L;
        Product product = Product.builder().id(id).build();
        // moneylove는 상품질문고유번호 19번에 답변을 달거야.
        Long id2 =3L;
        ProductQuestion productQuestion = productAnswerRepository.findByProductQuestionId(id2);
        System.out.println(productQuestion + "!!!");
        ProductAnswer productAnswer = ProductAnswer.builder()
                .productQuestion(productQuestion)
                .businessmember(businessmember)
                .aRegdate(LocalDate.now())
                .aContent("152번에 대한 답변테스트")
                .build();
        productAnswerRepository.save(productAnswer);
    }
    //        Long id2 = 19L;
//        ProductQuestion productQuestion = ProductQuestion.builder().id(id2).build();
//
////        ProductAnswer productAnswer = productAnswerRepository.findByBusinessProductQuestionAnswer(productQuestion.getId());
//
//    @DisplayName("사업자는 상품을 등록 할 수 있습니다.")
//    @Test
//    public void test2() {
//        Businessmember businessmember = businessmemberRepository.findByBizMemberId("moneylove");
//
//        Product product = Product.builder()
//                .businessmember(businessmember)
//                .productName("깨강정")
//                .productTypecode(ProductType.O)
//                .productPrice(3_000)
//                .discountRate(10)
//                .build();
//
//        productRepository.save(product);
//        Optional<Product> productOpt = productRepository.findById(product.getId());
//        Product product2 = productOpt.orElse(null);
//
//        assertThat(product2.getId())
//                .isNotNull()
//                .isEqualTo(product.getId());
//    }
    @DisplayName("사업자는 고객이 작성한 문의글에 답변을 수정 할 수 있습니다.")
    @Test
    public void test9(){

    }
    @DisplayName("사업자는 상품을 구매한 고객이 작성한 리뷰글의 전체 내역을 볼 수 있습니다.")
    @Test
    public void test10(){

    }
    @DisplayName("사업자는 상품을 구매한 고객이 작성한 리뷰글에 댓글을 달 수 있습니다.")
    @Test
    public void test11(){

    }
    @DisplayName("사업자는 상품을 구매한 고객이 작성한 리뷰글에 댓글을 수정 할 수 있습니다.")
    @Test
    public void test12(){

    }
    @DisplayName("사업자는 상품 등록 시 옵션을 추가 할 수 있습니다.")
    @Test
    public void test13(){

    }
}


//    @DisplayName("사업자는 등록된 상품 개별 내역을 조회할 수 있습니다. - 사업자가 올린 상품 개별 조회") // 상품 bizmemberId랑 biz bizmemberId
//    @Test
//    public void test6(){
//        Long id =22L;
//        List<Product> products = productRepository.findByIdBiz(id);
//        assertThat(products).allSatisfy(product -> {
//            assertThat(product).isNotNull();
//            assertThat(product.getBusinessmember().getBizMemberId()).isEqualTo(id);
//        });
//    }
