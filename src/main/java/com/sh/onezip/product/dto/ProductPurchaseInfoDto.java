package com.sh.onezip.product.dto;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.productoption.entity.ProductOption;
import lombok.Data;

import java.util.List;

@Data
public class ProductPurchaseInfoDto {
    private Long id;

    private String productName;
    private int productQuantity;
    private int productPrice;
    private double discountRate;
    private int totalProductPrice;
    private int totalDiscountPrice;
    private int totalOptionCost;

    private List<ProductOption> productOptionLists;
    private ProductOption productOption;
    private int additionalCost;
    private Long optionId;
    private int sellPrice;
    private Member member;
}
