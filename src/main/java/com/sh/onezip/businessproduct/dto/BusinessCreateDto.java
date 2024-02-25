package com.sh.onezip.businessproduct.dto;

import com.sh.onezip.businessproduct.entity.BizAccess;
import com.sh.onezip.businessproduct.entity.Businessmember;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusinessCreateDto {
    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    private String bizMemberId;
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String bizPassword;
    @NotBlank(message = "상호명은 필수 입력 항목입니다.")
    private String bizName;
    private String bizPhone;
    private String bizAddr;
    private String bizDetailAddr;
    private String bizLicense;
    private String bizRegNo;
    private BizAccess bizRegStatus;

    public Businessmember toBusiness(){
        return Businessmember.builder()
                .bizMemberId(bizMemberId)
                .bizPassword(bizPassword)
                .bizName(bizName)
                .bizPhone(bizPhone)
                .bizAddr(bizAddr)
                .bizAddr(bizDetailAddr)
//                .bizLicense(bizLicense)
                .bizRegNo(bizRegNo)
                .bizRegStatus(bizRegStatus)
                .build();
    }
}
