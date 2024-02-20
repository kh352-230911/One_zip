package com.sh.onezip.member.dto;

import com.sh.onezip.member.entity.Gender;
import com.sh.onezip.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberCreateDto {
    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    private String memberId;
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;
    private String nickname;
    private LocalDate birthday;
    private Gender gender;
    private String phone;
    private String hobby;
    private String mbti;
    private String memberAddr;
    private String memberDetailAddr;

    public Member toMember() {

        String fullAddress = memberAddr + "#" + memberDetailAddr;
        return Member.builder()
                .memberId(memberId)
                .password(password)
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .birthday(birthday)
                .phone(phone)
                .hobby(hobby)
                .mbti(mbti)
                .memberAddr(fullAddress)
                .build();
    }
}
