package com.sh.onezip.product.dto;

import com.sh.onezip.product.entity.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class BusinessProductCreateDto {
    private String bizMemberId;
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
}