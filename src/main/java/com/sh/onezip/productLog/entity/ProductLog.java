package com.sh.onezip.productLog.entity;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.orderproduct.entity.OrderProduct;
import com.sh.onezip.payment.entity.Payment;
import com.sh.onezip.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Builder
@Table(name = "tb_plog")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "payment")
public class ProductLog {
    @Id
    @GeneratedValue(generator = "seq_tb_plog_generator")
    @SequenceGenerator(
            name = "seq_tb_plog_generator",
            sequenceName = "seq_tb_plog",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    // 02-24 변경점
//    @JoinColumn(name = "memberId")
//    @Column(name = "member_id")
//private String memberId;
    @ManyToOne(fetch = FetchType.LAZY) // 02-17: EAGER->LAZY
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(name = "purchase_date")
    private String purchaseDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_state")
    private ShppingState shppingState;
    @Enumerated(EnumType.STRING)
    @Column(name = "refund_check",  columnDefinition = "VARCHAR2(1)")
    private RefundCheck refundCheck;
    @Column(name = "memo")
    private String memo;
    @Column(name = "fixedDate")
    private LocalDate fixedDate;
    @Column(name = "arrAddr")
    private String arrAddr;
    @Column(name = "total_pay_amount")
    private int totalPayAmount;

    @OneToOne(mappedBy = "productLog", fetch = FetchType.LAZY)
    private Payment payment;


//    @OneToMany(mappedBy = "productLog")
//    @Builder.Default
//    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "productLog", fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();


}
