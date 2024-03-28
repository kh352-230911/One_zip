package com.sh.onezip.member.dto;


import com.sh.onezip.member.entity.Address;
import com.sh.onezip.member.entity.AddressType; // AddressType 엔터티 또는 Enum을 사용한다고 가정

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
    private String email;
    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;
    private LocalDate birthday;
    private Gender gender;
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phone;
    private String hobby;
    private String mbti;
    @NotBlank(message = "기본 주소는 필수 입력 항목입니다.")
    private String baseAddress;
    private String detailAddress;
    private String recipientPhone; // 받는 사람 전화번호 추가
    private AddressType addressType = AddressType.D; // 주소 유형 기본값 설정

    public Member toMember() {
        return Member.builder()
                .memberId(memberId)
                .password(password)
                .name(name)
                .email(email)
                .nickname(nickname)
                .birthday(birthday)
                .gender(gender)
                .phone(phone)
                .hobby(hobby)
                .mbti(mbti)
                .build();
    }

    public Address toAddress(Member member) {
        Address address = new Address();
        address.setMember(member); // Member 참조 설정
        address.setBaseAddress(baseAddress);
        address.setDetailAddress(detailAddress);
        address.setRecipientName(name); // 받는 사람 이름은 회원 이름으로 설정
        address.setRecipientPhone(recipientPhone); // 받는 사람 전화번호 설정
        address.setAddressType(addressType); // 주소 유형 설정
        return address;
    }

}
