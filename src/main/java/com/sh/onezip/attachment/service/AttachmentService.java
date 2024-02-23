package com.sh.onezip.attachment.service;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.dto.AttachmentDetailDto;
import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.attachment.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public AttachmentDetailDto findById(Long id) {
        return attachmentRepository.findById(id)
                .map(this::convertToAttachmentDetailDto)
                .orElseThrow();
    }

    private AttachmentDetailDto convertToAttachmentDetailDto(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentDetailDto.class);
    }
}
