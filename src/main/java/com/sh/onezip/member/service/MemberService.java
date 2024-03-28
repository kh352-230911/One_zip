package com.sh.onezip.member.service;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.authority.service.AuthorityService;
import com.sh.onezip.member.entity.Address;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.AddressRepository;
import com.sh.onezip.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AddressRepository addressRepository;

    public Member findByName(String username) {
        return memberRepository.findByName(username);
    }
    public Member findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }



    public Member createMember(Member member, Address address) {
        Member savedMember = memberRepository.save(member);  // 먼저 tb_member 테이블에 저장
        Authority authority = Authority.builder()
                .member(savedMember)  // 저장된 멤버의 ID 사용
                .userType(RoleAuth.ROLE_USER)
                .build();
        authorityService.createAuthority(authority);  // tb_authority 테이블에 저장


        address.setMember(savedMember);

        addressRepository.save(address);

        return savedMember;
    }

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    // 여기까지가 HSH코드

    // HBK start
    public Page<Member> findAllMembers(Pageable pageable) {
        return memberRepository.findAllMembers(pageable);
    }


    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }


    public Member findById(Long id) {
        return memberRepository.findByMId(id);
    }



    // HBK end
}

