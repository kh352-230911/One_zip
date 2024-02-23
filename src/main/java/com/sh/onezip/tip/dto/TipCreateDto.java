package com.sh.onezip.tip.dto;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.zip.entity.Zip;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TipCreateDto {
    @NotEmpty(message = "제목은 필수 입력 사항입니다.")
    private String tipTitle;
    @NotEmpty(message = "제목은 필수 입력 사항입니다.")
    private String tipContent;
    private Long tipCount = 0L;
    private List<AttachmentCreateDto> attachments = new ArrayList<>();
    public void addAttachmentCreateDto(AttachmentCreateDto attachmentCreateDto) {
        this.attachments.add(attachmentCreateDto);
    }
}
