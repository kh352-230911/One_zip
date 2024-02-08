package com.sh.onezip.diary.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DiaryCreateDto {
    private Long zipId;
    @NotEmpty(message = "제목은 필수입니다")
    private String diaryTitle;
    @NotEmpty(message = "내용은 필수입니다")
    private String diaryContent;
}
