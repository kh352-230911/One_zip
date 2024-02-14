package com.sh.onezip.tip.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TipListDto {
    private Long id;
    private String tipTitle;
    private String memberId; // Member#username
    private String tipContent;
    private LocalDateTime createdAt;
    private int attachCount;
}
