package com.sh.onezip.productquestion.dto;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
public class ProductQuestionCreateDto {
    private Long id;
    private Member member;
    private Product product;
    @NotBlank(message = "올바른 형식의 문의 글을 작성해 주십시오.")
    private String qContent;
    private Long questionId;
    private LocalDate qRegdate;
    private ProductAnswer productAnswer;
}
