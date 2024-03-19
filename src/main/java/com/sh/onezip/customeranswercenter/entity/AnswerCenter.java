package com.sh.onezip.customeranswercenter.entity;

import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert // null이 아닌 필드만 등록
@DynamicUpdate // 영속성컨텍스트의 엔티티와 달라진 필드만 수정
@Table(name = "tb_aone")
public class AnswerCenter {
    @Id
    @GeneratedValue(generator = "seq_AnswerCenter_id_generator")
    @SequenceGenerator(
            name = "seq_AnswerCenter_id_generator",
            sequenceName = "seq_tb_aone_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id") // tb_aone member_id = tb_member id
    private Member member;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qone_id")
    private QuestionCenter questionCenter;
    @Column
    String aoneContent;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate aoneRegdate;
    // HBK end
}
