package com.sh.onezip.zip.dto;

import com.sh.onezip.zip.entity.Zip;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ZipCreateDto {
    @NotEmpty(message = "소개글은 필수 입력 사항입니다.")
    private String content;
    @NotEmpty(message = "배경색상은 필수 입력 사항입니다.")
    private String colorCode;

    public Zip toZip() {
        return Zip.builder()
                .content(content)
                .colorCode(colorCode)
                .build();
    }
}
