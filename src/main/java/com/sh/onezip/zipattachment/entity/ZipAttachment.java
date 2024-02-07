package com.sh.onezip.zipattachment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_zip_attachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert // null이 아닌 필드만 등록
@DynamicUpdate // 영속성컨텍스트의 엔티티와 달라진 필드만 수정
public class ZipAttachment {
    @Id
//    @GeneratedValue(generator = "seq_tb_zip_attachment_id_generator")
    @SequenceGenerator(
            name = "seq_tb_zip_attachment_id_generator",
            sequenceName = "seq_tb_zip_attachment_id",
            initialValue = 1,
            allocationSize = 1
    )
//    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "zip_id")
    private Long zipId;
    private String originalName;
    private String key;
    private String url;
    @CreationTimestamp
    private LocalDateTime regDate;
    @Enumerated(EnumType.STRING)
    private Type type;
}
