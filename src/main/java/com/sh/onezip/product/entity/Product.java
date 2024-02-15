package com.sh.onezip.product.entity;

import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.productimage.entity.ProductImage;
import com.sh.onezip.productoption.entity.ProductOption;
import jakarta.persistence.*;
import lombok.*;
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
@ToString(exclude = "productImages")
public class Product implements Comparable<Product> {
    @Id
    @GeneratedValue(generator = "seq_tb_product_id_generator")
    @SequenceGenerator(
            name = "seq_tb_product_id_generator",
            sequenceName = "seq_tb_product_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @Column
    private String productName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "biz_member_Id")
    private Businessmember businessmember;
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

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @Builder.Default
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @Builder.Default
    private List<ProductOption> productOptions = new ArrayList<>();

    @Override
    public int compareTo(Product other) {
        return (int) (this.id - other.id);
    }

}
