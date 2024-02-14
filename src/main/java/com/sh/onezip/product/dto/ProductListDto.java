package com.sh.onezip.product.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductListDto {
    private Long id;
    private String productName;
    private String bizName;
    // 할인적용 후 최종 가격(리스트 노출 가격)
    private int sellPrice;
    // 사업자 등록 상품 리스트에 쓰려고 추가로 넣어놨음
    private int productPrice;
    private LocalDate regDate;
}
