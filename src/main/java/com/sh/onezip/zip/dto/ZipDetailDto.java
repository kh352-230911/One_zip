package com.sh.onezip.zip.dto;

import com.sh.onezip.zipattachment.dto.ZipAttachmentDetailDto;
import lombok.Data;

import java.util.List;

@Data
public class ZipDetailDto {
    private String content;
    private String colorCode;
    private List<ZipAttachmentDetailDto> zipAttachmentDetailDtoList;
}
