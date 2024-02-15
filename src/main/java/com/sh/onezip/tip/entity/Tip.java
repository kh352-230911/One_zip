package com.sh.onezip.tip.entity;


import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.zip.entity.Zip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tip")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tip {
    @Id
    @GeneratedValue(generator = "seq_tip_id_generator")
    @SequenceGenerator(
            name = "seq_tip_id_generator",
            sequenceName = "seq_tip_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zip_id")
    private Zip zip;
    private String tipTitle;
    private String tipContent;
    private Long tipCount;
    @CreationTimestamp
    private LocalDateTime regDate;
    private String type;
    @OneToMany
    @JoinColumn(name = "ref_id") // attachment.board_id 컬럼 참조
    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();
}
