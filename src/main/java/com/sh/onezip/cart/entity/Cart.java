//package com.sh.onezip.cart.entity;
//
//import com.sh.onezip.member.entity.Member;
//import com.sh.onezip.product.entity.Product;
//import com.sh.onezip.productquestion.entity.ProductQuestion;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Builder
//@Table(name="tb_cart")
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class Cart {
//    @Id
//    @GeneratedValue(generator = "seq_tb_cart_id_generator")
//    @SequenceGenerator(
//            name = "seq_tb_cart_id_generator",
//            sequenceName = "seq_tb_cart_id",
//            initialValue = 1,
//            allocationSize = 50)
//    private Long id;
//    @Column
//    private String productName;
//    @Column
//    private int cartQuantity; // 수량
//    @Column
//    private String cartStatus; // Y / N
//    @Column
//    private int totalStock; // 재고
//    @Column
//    private String poptionName; // 옵션명
//    @Column
//    private int optionCost; // 옵션 추가 가격
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_no")
//    private Product product;
//    @OneToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//
//
//
//}
