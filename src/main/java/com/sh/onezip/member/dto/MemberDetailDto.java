package com.sh.onezip.member.dto;

import com.sh.onezip.neighbor.entity.Neighbor;
import lombok.Data;

import java.util.List;

@Data
public class MemberDetailDto {

    private String memberId;
    private String name;
    private List<Neighbor> neighbor;
}
