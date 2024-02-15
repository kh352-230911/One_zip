package com.sh.onezip.diary.dto;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DiaryListDto {
    private Long id;
    private Long zipId;
    private String diaryTitle;
    private String diaryContent;
    private LocalDateTime regDate;
}
