package com.sh.onezip.product.repository;

import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

//    @Query("FROM Product p JOIN FETCH p.businessmember WHERE p.biz_member_id = :bizMemberId")
//    @Query("select p, b from Product p left join fetch p.businessmember b") // 모든 사업자가 올린 상품이 조회됨
    @Query("FROM Product p WHERE p.businessmember.bizMemberId = :bizMemberId") // 지정한 사업자가 올린 상품만 조회 됨
    List<Product> findByBusinessmemberBizMemberId(String bizMemberId);

    @Query("select p, b from Product p left join fetch p.businessmember b")
    Optional<Product> findByProduct(Businessmember businessmember);

    @Query("from Product p left join fetch p.businessmember b left join p.productImages order by p.id desc")
    Page<Product> findAll(Pageable pageable);

    @Query("from Product p left join fetch p.businessmember order by p.productName desc")
    Page<Product> findAllBiz(Pageable pageable);

//    @Query("FROM Product p WHERE p.businessmember.bizMemberId = :id")
//    List<Product> findByIdBiz(Long id);
}
