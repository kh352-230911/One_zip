package com.sh.onezip.businessmember.service;

import com.sh.onezip.businessmember.repository.BusinessmemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessmemberService {
    @Autowired
    BusinessmemberRepository businessmemberRepository;
}
