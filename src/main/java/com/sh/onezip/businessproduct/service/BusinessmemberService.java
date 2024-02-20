package com.sh.onezip.businessproduct.service;

import com.sh.onezip.businessproduct.repository.BusinessmemberRepository;
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


}






