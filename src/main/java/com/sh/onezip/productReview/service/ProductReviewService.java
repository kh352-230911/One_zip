package com.sh.onezip.productReview.service;

import com.sh.onezip.productReview.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductReviewService {
    @Autowired
    ProductReviewRepository productReviewRepository;

    public void deleteById(Long reviewId) {
        productReviewRepository.deleteById(reviewId);
    }
}
