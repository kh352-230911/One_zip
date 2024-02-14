package com.sh.onezip.tip.dto;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TipCreateDto {
    @NotEmpty(message = "제목은 필수 입력 사항입니다.")
    private String tipTitle;
    private String  memberId;
    @NotEmpty(message = "제목은 필수 입력 사항입니다.")
    private String tipContent;
    private List<AttachmentCreateDto> attachments = new ArrayList<>();
    public void addAttachmentCreateDto(AttachmentCreateDto attachmentCreateDto) {
        this.attachments.add(attachmentCreateDto);
    }
}
