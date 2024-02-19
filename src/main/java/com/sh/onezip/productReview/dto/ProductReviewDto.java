package com.sh.onezip.productReview.dto;

import com.sh.onezip.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductReviewDto {
    private Long id;
    private String memberId;
    private Long productNo;
    private String reviewTitle;
    private String reviewContent;
    private LocalDate reviewRegdate;
    private int starPoint;
    private Product product;
}
