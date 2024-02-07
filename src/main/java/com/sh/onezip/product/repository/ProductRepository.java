package com.sh.onezip.product.repository;

import com.sh.onezip.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    @Query("FROM Product p JOIN FETCH p.businessmember WHERE p.bizMemberId = :bizMemberId")
    List<Product> findByBusinessmemberBizMemberId(String bizMemberId);
}
