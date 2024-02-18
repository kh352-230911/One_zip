package com.sh.onezip.member.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class MemberDetailDto {

    private String memberId;
    private String name;
    private String memberAddr;
    private String memberDetailAddr;
    private String hobby;
    private String mbti;


}
