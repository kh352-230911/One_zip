package com.sh.onezip.product.entity;

import com.sh.onezip.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import com.sh.onezip.productoption.entity.ProductOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tb_product")
public class Product implements Comparable<Product>{

    // KMJ start

    @Id
    @GeneratedValue(generator = "seq_tb_product_id_generator")
    @SequenceGenerator(
            name = "seq_tb_product_id_generator",
            sequenceName = "seq_tb_product_id",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Column
    private String productName;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductType productTypecode;
    @Column
    private int productPrice;
    @Column(name = "discount_rate")
    private double discountRate;
    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDate regDate;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductOption> productOptions = new ArrayList<>();

    @Override
    public int compareTo(Product other) {
        return (int)(this.id - other.id);
    }

    // KMJ end

}
