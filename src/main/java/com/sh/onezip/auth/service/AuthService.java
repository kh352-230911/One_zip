package com.sh.onezip.auth.service;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements UserDetailsService{
    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberService.findByMemberId(memberId);
        if(member == null){
            throw new UsernameNotFoundException(memberId);
        }
        return new MemberDetails(member);
    }
    public void updateAuthentication(String memberId) {
        Member newMember = memberService.findByMemberId(memberId);
        MemberDetails newMemberDetails = new MemberDetails(newMember);
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                newMemberDetails,
                newMemberDetails.getPassword(),
                newMemberDetails.getAuthorities()
        );
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(newAuthentication);
    }
}