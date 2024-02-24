package com.sh.onezip.member.dto;

import com.sh.onezip.neighbor.entity.Neighbor;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
public class MemberDetailDto {
    private String memberId;
    private String name;
    private String nickname;
    private String memberAddr;
    private String memberDetailAddr;
    private String hobby;
    private String mbti;


}
