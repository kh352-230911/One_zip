package com.sh.onezip.product.dto;

import com.sh.onezip.businessmember.entity.Businessmember;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.productimage.entity.ProductImage;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductListDto {
    private Long id;
    private String productName;
    private String bizName;
    // 할인적용 후 최종 가격(리스트 노출 가격)
    private int sellPrice;

}
