package com.sh.onezip.product.dto;

import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.productimage.entity.ProductImage;
import com.sh.onezip.productoption.entity.ProductOption;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDetailDto {

    private Long id;
    private String productName;
    private String businessmember;
    private ProductType productTypeCode;
    private int productPrice;
    private int sellPrice;
    private LocalDate regDate;
    private List<ProductOption> productOptions;
    private String bizMemberId;
    private List<ProductImage> productImages;

}
