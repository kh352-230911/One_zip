package com.sh.onezip.attachment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_attachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
    @Id
    @GeneratedValue(generator = "seq_tb_attachment_id_generator")
    @SequenceGenerator(
            name = "seq_tb_attachment_id_generator",
            sequenceName = "seq_tb_attachment_id",
            initialValue = 1,
            allocationSize = 50
    )
    private Long id;
    @Column(name = "ref_id", nullable = false)
    private Long refId;
    private String refType;
    private String originalFilename;
    private String key;
    private String url;
    @CreationTimestamp
    private LocalDateTime regDate;
}

