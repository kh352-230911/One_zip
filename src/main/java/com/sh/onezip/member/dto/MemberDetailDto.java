package com.sh.onezip.member.dto;

import lombok.Data;

@Data
public class MemberDetailDto {
    private String memberId;
    private String name;
    private String nickname;
//    private String memberAddr;
//    private String memberDetailAddr;
    private String hobby;
    private String mbti;
}
