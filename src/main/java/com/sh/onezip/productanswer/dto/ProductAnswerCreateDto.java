package com.sh.onezip.productanswer.dto;

import com.sh.onezip.productquestion.entity.ProductQuestion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductAnswerCreateDto {
    private ProductQuestion productQuestion;
    private Long id;
    private String bizMemberId;
    private String aContent;
    private LocalDate aRegdate;
}
