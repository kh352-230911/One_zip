package com.sh.onezip.bizauthority.vo;

import com.sh.onezip.businessproduct.entity.Businessmember;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class BizDetails implements UserDetails{

    // 현재 사용자의 인증된 정보를 가져옴
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    final Businessmember businessmember;
//    private Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       /* List<BizAccess> bizAccesses = new ArrayList<>();
        bizAccesses.add(this.businessmember.getBizLicense());
        return bizAccesses.stream()
                .map((bizAuthoritiy -> bizAuthoritiy.get().name()))
                .map(bizAuthority -> new SimpleGrantedAuthority(bizAuthority.getBizUserType().name()))
                .collect(Collectors.toList());*/
        Collection<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE";
            }
        });
        return list;
    }

    @Override
    public String getPassword() {
        return this.businessmember.getBizPassword();
    }

    @Override
    public String getUsername() {
        return this.businessmember.getBizMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    }

