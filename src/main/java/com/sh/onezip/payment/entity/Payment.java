package com.sh.onezip.payment.entity;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.productLog.entity.ProductLog;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@Table(name = "tb_payment")
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@ToString(exclude = {"productLog, member"})
public class Payment {
    @Id
    @GeneratedValue(generator = "seq_tb_payment_generator")
    @SequenceGenerator(
            name = "seq_tb_payment_generator",
            sequenceName = "seq_tb_payment",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @OneToOne
    @JoinColumn(name = "plog_id")
    private ProductLog productLog;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String buyerTel;
    private String buyerAddr;
    private String buyerPostcode;
    private String merchantUid;
    private int amount;
}
