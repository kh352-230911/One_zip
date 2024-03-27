package com.sh.onezip.business.dto;


import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.dto.AttachmentDetailDto;
import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.business.entity.BizAccess;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.member.entity.Member;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusinessAllDto {
    private Member member;
    private Business business;
    List<Attachment> attachmentList = new ArrayList<>();

    // 사업자 수정에 필요함
    private Long id;
    private String bizName;
    private String bizRegNo;
    private BizAccess bizRegStatus;
    private List<AttachmentCreateDto> attachments = new ArrayList<>();
    public void addAttachmentCreateDto(AttachmentCreateDto attachmentCreateDto) {
        this.attachments.add(attachmentCreateDto);
    }


}
