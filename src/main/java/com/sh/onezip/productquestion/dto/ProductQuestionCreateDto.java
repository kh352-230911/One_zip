package com.sh.onezip.productquestion.dto;

import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
public class ProductQuestionCreateDto {
    private Long id;
    private String memberId;
    private Product product;
    private String qContent;
    private Long questionId;
    private LocalDate qRegdate;
    private ProductAnswer productAnswer;
}
