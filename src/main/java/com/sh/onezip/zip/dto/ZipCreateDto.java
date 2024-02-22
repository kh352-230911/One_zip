package com.sh.onezip.zip.dto;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.zip.entity.Type;
import com.sh.onezip.zip.entity.Zip;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ZipCreateDto {
    private String memberId;
    @NotEmpty(message = "소개글은 필수 입력 사항입니다.")
    private String content;
    @NotEmpty(message = "배경색상은 필수 입력 사항입니다.")
    private String colorCode;
    private Type type;

}
