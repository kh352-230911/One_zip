package com.sh.onezip.orderproduct.entity;

import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productLog.entity.ProductLog;
import com.sh.onezip.productoption.entity.ProductOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@Table(name = "tb_order_product")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(generator = "seq_tb_order_product_generator")
    @SequenceGenerator(
            name = "seq_tb_order_product_generator",
            sequenceName = "seq_tb_order_product",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plog_no")
    private ProductLog productLog;
    @OneToOne
    @JoinColumn(name = "product_no")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poption_no")
    private ProductOption productOption;
    @Column(name = "purchase_quantity")
    private int purchaseQuantity;
    @Column(name = "pay_amount")
    private int payAmount;

}
