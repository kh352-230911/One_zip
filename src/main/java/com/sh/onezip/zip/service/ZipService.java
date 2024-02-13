package com.sh.onezip.zip.service;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.repository.ZipRepository;
import com.sh.onezip.attachment.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZipService {
    @Autowired
    private ZipRepository zipRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;

    public Optional<Zip> findById(Long id){
        return zipRepository.findById(id);
    }

    public List<Zip> findByMemberId(Member member){
        return zipRepository.findByMemberId(member);
    }

    public Zip createZip(Zip zip){
        zipRepository.save(zip);
        return zip;
    }

    public Zip updateZip(Zip zip){
        Zip _zip = zipRepository.findById(zip.getId()).orElse(null);
        return zipRepository.save(zip);
    }
}
