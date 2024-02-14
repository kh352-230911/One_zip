package com.sh.onezip.productoption.entity;

import com.sh.onezip.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_poption")
public class ProductOption {
    @Id
//    @GeneratedValue(generator = "seq_poption_generator")
//    @SequenceGenerator(
//            name = "seq_poption_generator",
//            sequenceName = "seq_tb_poption",
//            initialValue = 1,
//            allocationSize = 1)
//
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_no")
    private Product product;
    @Column(name = "option_name")
    private String optionName;
    @Column(name = "total_stock")
    private int totalStock;
    @Column(name = "option_cost")
    private int optionCost;
    @Column(name = "ne_option")
    private boolean neOption;
}
