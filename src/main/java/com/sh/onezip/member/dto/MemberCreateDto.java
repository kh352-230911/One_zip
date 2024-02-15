package com.sh.onezip.member.dto;

import com.sh.onezip.member.entity.Gender;
import com.sh.onezip.member.entity.Member;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberCreateDto {
    private String memberId;
    private String password;
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
