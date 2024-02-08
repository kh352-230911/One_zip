package com.sh.onezip.zip.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ZipCreateDto {
    @NotEmpty(message = "소개글은 필수 입력 사항입니다.")
    private String content;
    private String colorCode;
}
