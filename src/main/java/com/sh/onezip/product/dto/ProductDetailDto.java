package com.sh.onezip.product.dto;

import com.sh.onezip.product.entity.ProductType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductDetailDto {
    private Long id;
    private String productName;
    private String businessmember;
    private ProductType productTypeCode;
    private int productPrice;
    private int sellPrice;
    private LocalDate regDate;
}
