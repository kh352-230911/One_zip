package com.sh.onezip.zip.dto;

import lombok.Data;

import java.util.List;

@Data
public class ZipDetailDto {
    private String content;
    private String colorCode;
    private List<ZipDetailDto> ZupDetailDto;
}
