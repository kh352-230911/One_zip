package com.sh.onezip.orderproduct.dto;

import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productLog.entity.ProductLog;
import com.sh.onezip.productoption.entity.ProductOption;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class OrderProductDto {
    private Long id;
    private ProductLog productLog;
    private Product product;
    private ProductOption productOption;
    private int purchaseQuantity;
    private int payAmount;
}
