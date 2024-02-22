package com.sh.onezip.productReview.dto;

import com.sh.onezip.product.entity.Product;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductReviewCreateDto {
    private Long id;
    private String memberId;
    private Product product;
    //    @NotEmpty(message = "내용은 필수 입력 사항입니다.")
    private String reviewContent;
    private LocalDate reviewRegdate;
    private int starPoint;
}
