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
@Table(name = "tb_pquestions")
@ToString(exclude = "product")
//@ToString(exclude = {"member", "product"})
public class ProductQuestion implements Comparable<ProductQuestion>{
    @Id
    @GeneratedValue(generator = "seq_pquestions_generator")
    @SequenceGenerator(
            name = "seq_pquestions_generator",
            sequenceName = "seq_tb_pquestions",
            initialValue = 1,
            allocationSize = 1)
    @Column
    private Long id;
    @JoinColumn(name = "member_id")
    private String memberId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id2")
    private Product product;
    //    private String productNo;
    @JoinColumn
    private String qContent;
    @CreationTimestamp
    @Column(name = "q_regdate")
    private LocalDate qRegdate;
    @OneToOne(mappedBy = "productQuestion")
    private ProductAnswer productAnswer;

    @Override
    public int compareTo(ProductQuestion other) {
        return (int)(this.id - other.id);
    }

}
