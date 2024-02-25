package com.sh.onezip.product.repository;


import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.product.entity.Product;

import com.sh.onezip.productReview.entity.ProductReview;
import com.sh.onezip.productanswer.dto.ProductAnswerCreateDto;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {


    // 명준 작업 공간 start =================================================================

    Optional<Product> findById(Long id);

//        @Query("FROM Product p JOIN FETCH p.businessmember WHERE p.biz_member_id = :bizMemberId")
    @Query("select p, b from Product p left join fetch p.businessmember b where b.bizMemberId = :bizMemberId")
    List<Product> findByBusinessmemberBizMemberId(String bizMemberId);

    @Query("from Product p left join fetch p.businessmember b left join p.productImages order by p.id desc")
    Page<Product> findAll(Pageable pageable);

    @Query("from Product p left join fetch p.businessmember b left join p.productImages where p.productPrice <= :refPrice order by p.id desc")
    Page<Product> findAllByPriceUnder(Pageable pageable, int refPrice);

    @Query("from Product p left join fetch p.businessmember b left join p.productImages where p.productPrice >= :refPrice order by p.id desc")
    Page<Product> findAllByPriceUpper(Pageable pageable, int refPrice);

    @Query("from Product p where p.productPrice <= :refPrice")
    List<Product> findAllByPriceUnder(int refPrice);

    @Query("from Product p where p.productPrice >= :refPrice")
    List<Product> findAllByPriceUpper(int refPrice);

    @Query("select q from Product p left join fetch ProductQuestion q on p.id = q.product.id where p.id = :id")
    List<ProductQuestion> pquestionFindByProductid(Long id);

    @Query("from ProductQuestion pq where pq.product.id = :productId order by pq.id desc")
    Page<ProductQuestion> productQuestionFindAllByProductId(Pageable pageable, Long productId);

    @Query("from ProductQuestion pq where pq.product.id = :productId")
    List<ProductQuestion> productQuestionFindAllByProductId(@Param("productId") Long productId);

    @Query("from ProductReview pr where pr.product.id = :id")
    List<ProductReview> reviewFindAllByProductId(Long id);

    @Query("from ProductReview pr where pr.product.id = :productId order by pr.id desc")
    Page<ProductReview> productReviewFindAllByProductId(Pageable pageable, Long productId);

    @Query("from ProductReview pr where pr.product.id = :productId")
    List<ProductReview> productReviewFindAllByProductId(@Param("productId") Long productId);

    @Query("from ProductReview pr where pr.product.id = :id")
    List<ProductReview> productReviewFindByProductid(Long id);


    // 명준 작업 공간 end =================================================================

    // 보경 작업 공간 start =================================================================

    @Query("from Product p left join fetch p.businessmember WHERE p.businessmember.bizMemberId = :bizMemberId order by p.productName desc")
    Page<Product> findAllBiz(Pageable pageable,String bizMemberId);

    @Query("SELECT p FROM ProductQuestion p WHERE p.product.businessmember.bizMemberId = :bizMemberId")
    List<ProductQuestion> findByAllBusinessProductQuestion(String bizMemberId);

    @Query("select p, b from Product p left join fetch p.businessmember b")
    Optional<Product> findByProduct(Businessmember businessmember);

    @Query("SELECT p FROM ProductQuestion p LEFT JOIN FETCH p.product WHERE p.product.businessmember.bizMemberId = :bizMemberId ORDER BY p.qRegdate DESC")
    Page<ProductQuestion> findAllQuestion(Pageable pageable, String bizMemberId);

    @Query("SELECT p FROM ProductReview p LEFT JOIN FETCH p.product WHERE p.product.businessmember.bizMemberId = :bizMemberId ORDER BY p.reviewRegdate DESC")
    Page<ProductReview> findAllReview(Pageable pageable, String bizMemberId);

    @Query("SELECT p FROM ProductReview p WHERE p.product.businessmember.bizMemberId = :bizMemberId")
    List<ProductReview> findByAllReview(String bizMemberId);

    // 보경 작업 공간 end =================================================================

}
