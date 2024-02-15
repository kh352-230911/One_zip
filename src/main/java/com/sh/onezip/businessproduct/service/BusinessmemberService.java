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


//    public void businessproductcreate(BusinessCreateDto businessCreateDto) {
//    Businessmember businessmember = businessmemberRepository.save()
//    }
}

//    public Page<BusinessProductListDto> findAll(Pageable pageable) {
//        Page<Product> productPage = productRepository.findAll(pageable);
//        return productPage.map(this::convertToBusinessProductListDto);
//    }
//
//    private BusinessProductListDto convertToBusinessProductListDto(Product product) {
//        BusinessProductListDto businessProductListDto = modelMapper.map(product, BusinessProductListDto.class);
//        businessProductListDto.setBizName(product.getBusinessmember().getBizName());
//        businessProductListDto.setSellPrice((int)(product.getProductPrice() * (1 - ((double)product.getDiscountRate() / 100))));
//        // 필요한 경우에만 이미지 설정
//        // businessProductListDto.setBizImage(product.getBusinessmember().getBizImageUrl());
//        return businessProductListDto;
//    }

//    public List<BusinessProductListDto> findByBizMemberId(String bizMemberId){
//        Businessmember businessmember = businessmemberRepository.findByBizMemberId(bizMemberId);
//        BusinessProductListDto businessProductListDto = new BusinessProductListDto();
//        businessProductListDto.add(convertToBusinessProductListDto(businessmember));
//
//        return businessProductListDtos;
//    }









