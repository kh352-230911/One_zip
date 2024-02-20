package com.sh.onezip.zip.dto;

import com.sh.onezip.attachment.dto.AttachmentDetailDto;
import com.sh.onezip.member.dto.MemberDetailDto;
import com.sh.onezip.member.entity.Member;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ZipDetailDto {
    private Long id;
    private String content;
    private String colorCode;
    private String dayCount;
    private String totalCount;
    private Member member;
    private LocalDate regDate;
    private List<AttachmentDetailDto> attachments = new ArrayList<>();
}
