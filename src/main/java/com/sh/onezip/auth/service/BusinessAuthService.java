package com.sh.onezip.auth.service;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.bizauthority.entity.BizAuthority;
import com.sh.onezip.bizauthority.repository.BizAuthorityRepository;
import com.sh.onezip.bizauthority.vo.BizDetails;
import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.businessproduct.service.BusinessmemberService;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BusinessAuthService implements UserDetailsService{
    @Autowired
    private BusinessmemberService businessmemberService;

    @Override
    public UserDetails loadUserByUsername(String bizMemberId) throws UsernameNotFoundException {
        log.debug("Attempting to load user by username: {}", bizMemberId);
        Businessmember businessmember = businessmemberService.findBybizMemberId(bizMemberId);
        if (businessmember == null) {
            log.error("User not found with username: {}", bizMemberId);
            throw new UsernameNotFoundException(bizMemberId);
        }
        return new BizDetails(businessmember);
    }

   /* public void updateAuthentication(String bizMemberId) {
        Businessmember newBusinessmember = businessmemberService.findBybizMemberId(bizMemberId);
        BizDetails newBizDetails = new BizDetails(newBusinessmember);
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                newBizDetails,
                newBizDetails.getPassword(),
                newBizDetails.getAuthorities()
        );
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(newAuthentication);
    }*/
}