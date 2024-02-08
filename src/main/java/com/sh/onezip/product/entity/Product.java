package com.sh.onezip.product.entity;

import com.sh.onezip.businessmember.entity.Businessmember;
import com.sh.onezip.productimage.entity.ProductImage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Builder
@Table(name = "tb_product")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "seq_board_id_generator")
    @SequenceGenerator(
            name = "seq_board_id_generator",
            sequenceName = "seq_tb_product_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @Column
    private String productName;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductType productTypecode;
    @Column
    private int productPrice;
    @Column
    private int discountRate;
    @Column
    @CreationTimestamp
    private LocalDate regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biz_member_Id")
    private Businessmember businessmember;

//    @OneToMany(mappedBy = "product" ,fetch = FetchType.EAGER)
//    @Builder.Default
//    private List<ProductImage> productImage = new ArrayList<>();

}
