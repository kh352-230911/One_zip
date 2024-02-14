package com.sh.onezip.zip.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ZipUpdateDto {
    @NotNull(message = "내용은 필수 입력값입니다.")
    private String content;
    @NotNull(message = "색상은 필수 입력값입니다.")
    private String colorCode;
}
