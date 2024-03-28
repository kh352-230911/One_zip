package com.sh.onezip.attachment.service;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.dto.AttachmentDetailDto;
import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.attachment.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public void createAttachment(AttachmentCreateDto attachmentCreateDto){
        Attachment attachment = convertToAttachment(attachmentCreateDto);
        attachment.setId(null);
        attachmentRepository.save(attachment);
    }

    public Attachment convertToAttachment(AttachmentCreateDto attachmentCreateDto){
        return modelMapper.map(attachmentCreateDto, Attachment.class);
    }

    public AttachmentDetailDto findByIdWithType(Long id, String refType) {
        return attachmentRepository.findTopByOrderByRegDateDesc(id, refType).findFirst()
                .map(this::convertToAttachmentDetailDto)
                .orElse(null);
    }

    private AttachmentDetailDto convertToAttachmentDetailDto(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentDetailDto.class);
    }

    public List<AttachmentDetailDto> findZipAttachmentToList(Long id, String refType) {
        List<Attachment> attachList = attachmentRepository.findZipAttachmentToList(id, refType);
        if(attachList.isEmpty()) {return new ArrayList<>();}
        return attachList.stream().map(this::convertToAttachmentDetailDto).collect(Collectors.toList());
    }

    public List<Attachment> findProductAttachmentToList(Long id) {
        List<Attachment> attachmentList = attachmentRepository.findProductAttachmentToList(id, "SP");
        if(attachmentList.isEmpty()) {return new ArrayList<>();}
        return attachmentList;
    }

    // HBK start

    public void deleteByphotoId(Long id) {
        attachmentRepository.deleteById(id);
    }

}
