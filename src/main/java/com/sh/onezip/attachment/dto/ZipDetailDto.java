package com.sh.onezip.attachment.dto;

//import com.sh.onezip.member.dto.MemberDetailDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ZipDetailDto {
    private Long id;
    private String content;
    private String colorCode;
//    private MemberDetailDto memberId;
    private LocalDate regDate;
    private List<AttachmentDetailDto> attachments = new ArrayList<>();
}
