package com.sh.onezip.stomp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private Type type;
    private String message;
    private String from;
    private String to;
    private Long createdAt;
}
