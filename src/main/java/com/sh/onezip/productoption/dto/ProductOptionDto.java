package com.sh.onezip.productoption.dto;

import lombok.Data;

@Data
public class ProductOptionDto {
    private Long id;
    private Long productId;
    private String optionName;
    private int totalStock;
    private int optionCost;
    private boolean neOption;
}
