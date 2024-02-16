package com.sh.onezip.productanswer.entity;

import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.productquestion.entity.ProductQuestion;
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
@Table(name = "tb_qanwers")
public class ProductAnswer {
    @Id
    @GeneratedValue(generator = "seq_qanwers_generator")
    @SequenceGenerator(
            name = "seq_qanwers_generator",
            sequenceName = "seq_tb_qanwers",
            initialValue = 50,
            allocationSize = 1)
    @Column
    private Long id;
    @OneToOne
    @JoinColumn(name = "pquestions_no")
    private ProductQuestion productQuestion;
    @ManyToOne
    @JoinColumn(name = "biz_member_id")
    private Businessmember businessmember;
    private String aContent;
    @CreationTimestamp
    private LocalDate aRegdate;


}
