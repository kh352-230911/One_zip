package com.sh.onezip.zip.dto;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.member.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZipUpdateDto {
    @NotNull(message = "내용은 필수 입력값입니다.")
    private String content;
    @NotNull(message = "색상은 필수 입력값입니다.")
    private String colorCode;
//    private String type;
    /*private List<AttachmentCreateDto> attachments = new ArrayList<>();

    public void addAttachmentCreateDto(AttachmentCreateDto attachmentCreateDto){
        this.attachments.add(attachmentCreateDto);
    }*/
}
