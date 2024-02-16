package com.sh.onezip.productReview.repository;

import com.sh.onezip.productReview.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
}
