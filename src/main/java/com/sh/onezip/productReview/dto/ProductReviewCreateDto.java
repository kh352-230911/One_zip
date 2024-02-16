package com.sh.onezip.productReview.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductReviewCreateDto {
    private Long id;
    private String memberId;
    private Long productNo;
//    @NotEmpty(message = "내용은 필수 입력 사항입니다.")
    private String reviewContent;
    private LocalDate reviewRegdate;
    private int starPoint;
}
