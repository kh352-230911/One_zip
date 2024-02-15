//package com.sh.onezip.cart.entity;
//
//import com.sh.onezip.member.entity.Member;
//import com.sh.onezip.product.entity.Product;
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
//            allocationSize = 1)
//    private Long id;
//    @Column
//    private String memberId;
//    @Column
//    private int productNo;
//    @Column
//    private String productName;
//    @Column
//    private int cartQuanity;
//    @Column
//    private String cartStatus;
//    @Column
//    private int totalStock;
//    @Column
//    private String poptionName;
//    @Column
//    private int optionCost;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="member_id")
//    private Member member;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name="id")
//    @Builder.Default
//    private List<Product> product = new ArrayList<>();
//}