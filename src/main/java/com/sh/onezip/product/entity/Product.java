package com.sh.onezip.product.entity;

import com.sh.onezip.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

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
    private String productName;
    private ProductType productTypecode;
    private int productPrice;
    private double discountRate;
    @CreationTimestamp
    private LocalDate regDate;

    @Override
    public int compareTo(Product other) {
        return (int)(this.id - other.id);
    }

    // KMJ end

}
