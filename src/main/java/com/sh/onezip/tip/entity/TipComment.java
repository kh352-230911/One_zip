package com.sh.onezip.tip.entity;

import com.sh.onezip.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tip_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TipComment {
    @Id
    @GeneratedValue(generator = "seq_tip_comment_id_generator")
    @SequenceGenerator(
            name = "seq_tip_comment_id_generator",
            sequenceName = "seq_tip_comment_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tip_id")
    private Tip tip;
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
    private String commentContent;
    @CreationTimestamp
    private LocalDateTime regDate;
}
