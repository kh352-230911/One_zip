package com.sh.onezip.zip.entity;

import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "tb_zip")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "seq_tb_zip_id_generator")
    @SequenceGenerator(
            name = "seq_tb_zip_id_generator",
            sequenceName = "seq_tb_zip_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;
    private String content;
    @CreationTimestamp
    private LocalDate regDate;
    @OneToMany
    @JoinColumn(name = "ref_id")
    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();
    private String colorCode;
    private int dayCount;
    private int totalCount;
    @Enumerated(EnumType.STRING)
    private Type type;
}
