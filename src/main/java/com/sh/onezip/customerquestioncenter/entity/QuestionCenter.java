package com.sh.onezip.customerquestioncenter.entity;

import com.sh.onezip.business.entity.BizAccess;
import com.sh.onezip.customeranswercenter.entity.AnswerCenter;
import com.sh.onezip.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert // null이 아닌 필드만 등록
@DynamicUpdate // 영속성컨텍스트의 엔티티와 달라진 필드만 수정
@Table(name = "tb_qone")
@ToString(exclude = {"answerCenter"})
public class QuestionCenter {
    @Id
    @GeneratedValue(generator = "seq_QuestionCenter_id_generator")
    @SequenceGenerator(
            name = "seq_QuestionCenter_id_generator",
            sequenceName = "seq_tb_qone_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id") // tb_qone member_id = tb_member id
    private Member member;
    @Column
    String qoneContent;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate qoneRegdate;
    @Column
    @Enumerated(EnumType.STRING)
    private AnswerCheck answerYn;

    @OneToOne(mappedBy = "questionCenter" ,fetch = FetchType.LAZY)
    private AnswerCenter answerCenter;
    // HBK end
}