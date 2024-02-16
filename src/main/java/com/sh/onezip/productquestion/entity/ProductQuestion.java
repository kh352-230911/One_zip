package com.sh.onezip.productquestion.entity;


import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_pquestions")
public class ProductQuestion {
    @Id
    @GeneratedValue(generator = "seq_pquestions_generator")
    @SequenceGenerator(
            name = "seq_pquestions_generator",
            sequenceName = "seq_tb_pquestions",
            initialValue = 1,
            allocationSize = 1)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id2")
    private Product product;
    private String qContent;
    @CreationTimestamp
    @Column(name = "q_regdate")
    private LocalDate qRegdate;


}
