package com.sh.onezip.productanswer.entity;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_qanwer")
@ToString(exclude = "productQuestion")
public class ProductAnswer {
    @Id
    @GeneratedValue(generator = "seq_qanwers_generator")
    @SequenceGenerator(
            name = "seq_qanwers_generator",
            sequenceName = "seq_tb_qanwers",
            initialValue = 1,
            allocationSize = 1)
    @Column
    private Long id;
    @OneToOne
    @JoinColumn(name = "pquestions_id")
    private ProductQuestion productQuestion;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column
    private String aContent;
    @CreationTimestamp
    private LocalDate aRegdate;

}

