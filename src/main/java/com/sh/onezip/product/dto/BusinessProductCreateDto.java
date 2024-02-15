package com.sh.onezip.product.dto;

import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.product.entity.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BusinessProductCreateDto {

    private Long id;
//    private String bizMemberId;
    private Businessmember businessmember;
    private String productName;
    private int productPrice;
//    private int discountRate;
    // 할인적용 후 최종 가격(리스트 노출 가격)
    private int sellPrice; // convertTo에서 처리
//    private String optionName; Poption#optionName
//    private int optionCost; Poption#optionCost
    @Enumerated(EnumType.STRING)
    private ProductType productTypecode;
//  이미지 url 추후 처리
    private LocalDate regDate;
}
