package com.sh.onezip.productquestion.dto;

import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductQuestionDto{
    private Long id;
    private String memberId;
    private Product product;
    private String qContent;
    private String qRegdate;
    private ProductAnswer productAnswer;
    private Businessmember businessmember;
}
