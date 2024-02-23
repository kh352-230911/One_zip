package com.sh.onezip.productReview.service;

import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productReview.entity.ProductReview;
import com.sh.onezip.productReview.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductReviewService {
    @Autowired
    ProductReviewRepository productReviewRepository;

    public void deleteById(Long reviewId) {
        productReviewRepository.deleteById(reviewId);
    }

    public ProductReview findById(Long reviewId) {
        Optional<ProductReview> productReviewOpt = productReviewRepository.findById(reviewId);
        return productReviewOpt.orElse(null);

    }
}
