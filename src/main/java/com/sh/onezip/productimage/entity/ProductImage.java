//package com.sh.onezip.productimage.entity;
//
//import com.sh.onezip.product.entity.Product;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "tb_piamge")
//public class ProductImage {
//    @Id
//    @GeneratedValue(generator = "seq_piamge_generator")
//    @SequenceGenerator(
//            name = "seq_piamge_generator",
//            sequenceName = "seq_piamge",
//            initialValue = 1,
//            allocationSize = 1)
//    private Long id;
//    @Column(name = "product_no")
//    private Long productId;
//    @Column(name = "original_filename")
//    private String originalFilename;
//    @Column(name = "image_key")
//    private String imageKey;
//    @Column(name = "image_url")
//    private String imageUrl;
//    @Column(name = "image_type")
//    @Enumerated(EnumType.STRING)
//    private PImageType imageType;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private Product product;
//
//}
