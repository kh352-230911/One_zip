package com.sh.onezip.authority.service;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    @Autowired
    // HBK start
    AuthorityRepository authorityRepository;
    public Authority createAuthority(Authority authority) {
        System.out.println("authorityRepository:" +  authority);
        return authorityRepository.save(authority);
    }
    // HBK end
}
