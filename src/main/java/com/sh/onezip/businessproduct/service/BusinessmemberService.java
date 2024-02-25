package com.sh.onezip.businessproduct.service;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.businessproduct.repository.BusinessmemberRepository;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.product.dto.BusinessProductCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessmemberService {
    @Autowired
    BusinessmemberRepository businessmemberRepository;

    @Autowired
    ModelMapper modelMapper;


    public Businessmember createBusiness(Businessmember businessmember) {
        businessmemberRepository.save(businessmember);
        return businessmember;

    }


    public Businessmember findBybizMemberId(String bizMemberId) {
        return businessmemberRepository.findByBizMemberId(bizMemberId);
    }

//    public Businessmember findByBizMemberId(String bizMemberId) {
//        return businessmemberRepository.findByBizMemberId(bizMemberId);
//    }
}

