package com.sh.onezip.neighbor.dto;

import com.sh.onezip.member.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class NeighborDetailDto {
    private Long id;
    private Member member1;
    private Member member2;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime acceptedAt;
}
