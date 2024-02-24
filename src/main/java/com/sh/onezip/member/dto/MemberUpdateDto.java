package com.sh.onezip.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberUpdateDto {
    @NotNull(message = "id는 필수 입력값입니다.")
    private String memberId;
    @NotNull
    private String name;
    private String nickname;
    private String memberAddr;
    private String memberDetailAddr;
    private String hobby;
    private String mbti;

}
