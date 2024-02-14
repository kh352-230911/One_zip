package com.sh.onezip.attachment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentDetailDto {
    private Long id;
    private Long refId;
    private String refType;
    private String originalFilename;
    private String key;
    private String url;
    private LocalDateTime regDate;
}
