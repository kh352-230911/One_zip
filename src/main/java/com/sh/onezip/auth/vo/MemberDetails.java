//package com.sh.onezip.auth.vo;
//
//import com.sh.onezip.member.entity.Member;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Map;
//
//@Data
//public class MemberDetails implements UserDetails{
//
//    final Member member;
//
//    private Map<String, Object> attributes;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.member.getAuthorities().stream()
//                .map((authority -> authority.getUserType().name()))
//                .map((roleAuth) -> new SimpleGrantedAuthority(roleAuth))
//                .toList();
//    }
//
//    @Override
//    public String getPassword() {
//        return this.member.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.member.getName();
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
//
//    @Override
//    public String getName() {
//        return this.member.getName();
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return this.attributes;
//    }
//}
