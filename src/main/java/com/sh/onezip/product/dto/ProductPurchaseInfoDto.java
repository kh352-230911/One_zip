package com.sh.onezip.product.dto;

import com.sh.onezip.member.entity.Member;
import lombok.Data;

@Data
public class ProductPurchaseInfoDto {
    private Long id;
    private String productName;
    private int productQuantity;
    private int productPrice;
    private int totalProductPrice;
    private int discountRate;
    private int totalDiscountPrice;
    private int sellPrice;
    private Member member;
}
