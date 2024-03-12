package com.sh.onezip.member.service;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.authority.service.AuthorityService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthorityService authorityService;

    public Member findByName(String username) {
        return memberRepository.findByName(username);
    }
    public Member findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }

//    public Member createMember(Member member) {
//        memberRepository.save(member);
//        Authority authority = Authority.builder()
//                .memberId(member.getId())
//                .userType(RoleAuth.ROLE_USER)
//                .build();
//        authorityService.createAuthority(authority);
//        return member;
//    }


    @Transactional
    public Member createMember(Member member) {
        System.out.println("flag");
        Member savedMember = memberRepository.save(member);  // 먼저 tb_member 테이블에 저장
        System.out.println(savedMember + "flag1");
        Authority authority = Authority.builder()
                .member(savedMember)  // 저장된 멤버의 ID 사용
                .userType(RoleAuth.ROLE_USER)
                .build();
        authorityService.createAuthority(authority);  // tb_authority 테이블에 저장
        return savedMember;
    }

    // 여기까지가 HSH코드
}

