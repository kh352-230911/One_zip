package com.sh.onezip.product.dto;

import com.sh.onezip.member.entity.Member;
import lombok.Data;

@Data
public class ProductCartCreateDto {
    private Long productId; // productId;
    private String selectOption; // 선택된 옵션 값 [0]: optionId, [1]: optionCost
//    private int productQuantity; // 상품 수량
    private int totalPrice; // 총 금액(옵션 금액 포함)
    private int totalStock;
    private int productQuantity;
    private Member member;
//    private ProductOption productOption;

//    private Long id;
//    private String productName;
//    private int cartQuantity; // 수량
//    private String cartStatus; // Y / N
//    private int totalStock; // 재고
//    private String poptionName; // 옵션명
//    private int optionCost; // 옵션 추가 가격
//    private Product product;
//    private Member member;
}
