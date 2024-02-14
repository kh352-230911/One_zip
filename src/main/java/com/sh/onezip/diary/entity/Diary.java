package com.sh.onezip.diary.entity;

import com.sh.onezip.zip.entity.Zip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_diary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary {
    @Id
    @GeneratedValue(generator = "seq_tb_diary_id_generator")
    @SequenceGenerator(
            name = "seq_tb_diary_id_generator",
            sequenceName = "seq_tb_diary_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "zip_id")
    private Zip zip;
    private String diaryTitle;
    private String diaryContent;
    @CreationTimestamp
    private LocalDateTime regDate;
}
