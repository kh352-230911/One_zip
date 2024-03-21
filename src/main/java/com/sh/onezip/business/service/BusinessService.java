package com.sh.onezip.business.service;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.service.AttachmentService;
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
    private ModelMapper modelMapper;
    // HBK start
    public Page<Business> findAllBizmembers(Pageable pageable) {
        return businessRepository.findAll(pageable);
    }

    public Business findBizmember(Long id) {
        return businessRepository.findBizmember(id);
    }

    public Optional<Business> findById(Long id) {
        return businessRepository.findById(id);
    }

    public Business updateBizAccess(Business newbusiness) {
        return businessRepository.save(newbusiness);
    }

    public Business findByBizName(String bizName) {
        return businessRepository.findByBizName(bizName);
    }

    public void createBusiness(BusinessCreateDto businessCreateDto) {
        Business business = businessRepository.save(convertToBoard(businessCreateDto));
        businessCreateDto.getAttachments().forEach(attachmentCreateDto -> {
            attachmentCreateDto.setRefId(business.getId());
            attachmentService.createAttachment(attachmentCreateDto);
        });
    }

    private Business convertToBoard(BusinessCreateDto businessCreateDto) {
        return modelMapper.map(businessCreateDto, Business.class);
    }

//    public void createBusiness(Business businesscreate, List<AttachmentCreateDto> attachmentCreates) {
//    attachmentCreates.forEach(attachmentCreateDto -> {
//        attachmentCreateDto.setRefId(businesscreate.getId());
//        attachmentService.createAttachment(attachmentCreateDto);
//    });
//    businessRepository.save(businesscreate);
//    }

    // HBK end
}
