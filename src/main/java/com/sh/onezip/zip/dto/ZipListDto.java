package com.sh.onezip.zip.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ZipListDto {
    private Long id;
    private String memberId;
    private String content;
    private int dayCount;
    private int totalCount;
    private LocalDateTime regDate;
    private String colorCode;
}
