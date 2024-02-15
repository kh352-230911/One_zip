package com.sh.onezip.tip.dto;

import com.sh.onezip.member.dto.MemberDetailDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TipCommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private MemberDetailDto member;
}
