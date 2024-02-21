package com.sh.onezip.member.service;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.authority.service.AuthorityService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthorityService authorityService;



    public Member findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }



    public Member createMember(Member member) {
        memberRepository.save(member);
        Authority authority = Authority.builder()
                .memberId(member.getMemberId())
                .userType(RoleAuth.ROLE_USER)
                .build();
        authorityService.createAuthority(authority);
        return member;
    }



    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }
}
