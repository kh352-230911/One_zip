package com.sh.onezip.tip.dto;

import com.sh.onezip.attachment.dto.AttachmentDetailDto;
import com.sh.onezip.member.dto.MemberDetailDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TipDetailDto {
    private Long id;
    private String title;
    private MemberDetailDto member;
    private String content;
    private LocalDateTime createdAt;
    private List<AttachmentDetailDto> attachments = new ArrayList<>();
    private List<TipCommentDto> comments = new ArrayList<>();
}
