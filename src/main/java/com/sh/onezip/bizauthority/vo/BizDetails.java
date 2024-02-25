//package com.sh.onezip.bizauthority.vo;
//
//import com.sh.onezip.businessproduct.entity.BizAccess;
//import com.sh.onezip.businessproduct.entity.Businessmember;
//import com.sh.onezip.member.entity.Member;
//import lombok.Data;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Data
//public class BizDetails implements UserDetails{
//
//    // 현재 사용자의 인증된 정보를 가져옴
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//    final Businessmember businessmember;
////    private Map<String, Object> attributes;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<BizAccess> bizAccesses = new ArrayList<>();
//        bizAccesses.add(this.businessmember.getBizLicense());
//        return bizAccesses.stream()
//                .map((bizAuthoritiy -> bizAuthoritiy.get().name()))
//                .map(bizAuthority -> new SimpleGrantedAuthority(bizAuthority.getBizUserType().name()))
//                .collect(Collectors.toList());
//        return new ArrayList<>();
//    }
//
//    @Override
//    public String getPassword() {
//        return this.businessmember.getBizPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.businessmember.getBizMemberId();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    }
//
//
