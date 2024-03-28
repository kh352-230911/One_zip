package com.sh.onezip.business.service;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.repository.AttachmentRepository;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.business.dto.BusinessAllDto;
import com.sh.onezip.business.dto.BusinessCreateDto;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.business.repository.BusinessRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class BusinessService {
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    // HBK start
    public Page<Business> findAllBizmembers(Pageable pageable) {
        return businessRepository.findAllBizmember(pageable);
    }

    // 사업자만 있는 페이지
    public Page<BusinessAllDto> findAllBizmember(Pageable pageable) {
        // 데이터베이스에서 사업자 회원 목록을 페이지로 가져옴
        Page<Business> businessPage = businessRepository.findAllBizmember(pageable);
        // Business 엔티티를 BusinessAllDto로 변환하여 페이지로 반환
        return businessPage.map(business -> convertToBusinessListDto(business));
    }

    private BusinessAllDto convertToBusinessListDto(Business business) {
        // Business 엔티티를 BusinessAllDto로 매핑
        BusinessAllDto businessAllDto = modelMapper.map(business, BusinessAllDto.class);
        // Business의 id를 사용하여 해당 사업자의 첨부 파일 목록을 가져옴
        businessAllDto.setAttachmentList(attachmentRepository.findBusinessAttachment(businessAllDto.getBusiness().getId(), "BIZ"));
        // 변환된 BusinessAllDto 객체를 반환 (BusinessAllDto를 클래스로 변환)
        return businessAllDto;
    }


    public Business findByBizName(String bizName) {
        return businessRepository.findByBizName(bizName);
    }

    public void createBusiness(BusinessCreateDto businessCreateDto) {
        Business business = businessRepository.save(convertToBoard(businessCreateDto));
        businessCreateDto.getAttachments().forEach(attachmentCreateDto -> {
            attachmentCreateDto.setRefId(business.getId());
            attachmentCreateDto.setRefType("SP");
            attachmentService.createAttachment(attachmentCreateDto);
        });

    }

    private Business convertToBoard(BusinessCreateDto businessCreateDto) {
        return modelMapper.map(businessCreateDto, Business.class);
    }


    public Business updateBizAccess(Business newbusiness) {
        return businessRepository.save(newbusiness);
    }

    public Optional<Business> findById(Long id) {
        return businessRepository.findById(id);
    }


    public BusinessAllDto findBizAmember(Long id) {
        Business adminbusiness = businessRepository.findBizmember(id);
        return convertToBusinessAllDto(adminbusiness);
    }

    private BusinessAllDto convertToBusinessAllDto(Business adminbusiness) {
        BusinessAllDto businessAllDto = modelMapper.map(adminbusiness, BusinessAllDto.class);
        businessAllDto.setAttachmentList(attachmentRepository.findBusinessAttachment(businessAllDto.getBusiness().getId(), "SP"));
        return businessAllDto;
    }

    public BusinessAllDto findByBId(Long id) {
        Business business = businessRepository.findBizmemberId(id);
        return convertToBusinessIdDto(business);
    }

    private BusinessAllDto convertToBusinessIdDto(Business business) {
        BusinessAllDto businessAllDto = modelMapper.map(business, BusinessAllDto.class);
        businessAllDto.setAttachmentList(attachmentRepository.findBusinessAttachment(businessAllDto.getBusiness().getId(), "SP"));
        return businessAllDto;
    }


    public void updateBusiness(BusinessAllDto businessAllDto) {
        Business business = businessRepository.save(convertToBusinessUpdateAllDto(businessAllDto));
        businessAllDto.getAttachments().forEach(attachmentCreateDto -> {
            attachmentCreateDto.setRefId(business.getId());
            attachmentCreateDto.setRefType("SP");
            attachmentService.createAttachment(attachmentCreateDto);
        });
    }

    private Business convertToBusinessUpdateAllDto(BusinessAllDto businessAllDto) {
        return modelMapper.map(businessAllDto, Business.class);
    }

    public void deleteById(Long id) {
        businessRepository.deleteById(id);
    }

}

// HBK end