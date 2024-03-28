package com.sh.onezip.productquestion.entity;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_pquestion")
@ToString(exclude = "product")
//@ToString(exclude = {"member", "product"})
public class ProductQuestion implements Comparable<ProductQuestion> {
    @Id
    @GeneratedValue(generator = "seq_pquestions_generator")
    @SequenceGenerator(
            name = "seq_pquestions_generator",
            sequenceName = "seq_tb_pquestions",
            initialValue = 1,
            allocationSize = 1)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @JoinColumn
    private String qContent;
    @CreationTimestamp
    @Column(name = "q_regdate")
    private LocalDate qRegdate;
    @OneToOne(mappedBy = "productQuestion")
    private ProductAnswer productAnswer;

    @Override
    public int compareTo(ProductQuestion other) {
        return (int) (this.id - other.id);
    }

}
