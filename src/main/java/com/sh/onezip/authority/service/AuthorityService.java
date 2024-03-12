package com.sh.onezip.authority.service;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;
    public Authority createAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }
}
