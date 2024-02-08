package com.sh.onezip.zip.service;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.zip.dto.ZipCreateDto;
import com.sh.onezip.zip.dto.ZipListDto;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.repository.ZipRepository;
import com.sh.onezip.zipattachment.dto.ZipDetailDto;
import com.sh.onezip.zipattachment.service.ZipAttachmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ZipService {
    @Autowired
    private ZipRepository zipRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Zip convertToZip(ZipCreateDto zipCreateDto){
        return modelMapper.map(zipCreateDto, Zip.class);
    }

    private ZipListDto convertToZipListDto(Zip zip) {
        ZipListDto zipListDto = modelMapper.map(zip, ZipListDto.class);
        zipListDto.setMemberId(
                Optional.ofNullable(zip.getMember())
                        .map((member) -> member.getName())
                        .orElse(null)
        );
        return zipListDto;
    }

    public void createZip(ZipCreateDto zipCreateDto){
        Zip zip = zipRepository.save(convertToZip(zipCreateDto));
    }

    public ZipDetailDto findById(Long id){
        return zipRepository.findById(id)
                .map((zip) -> convertToZipDetailDto(zip))
                .orElseThrow();
    }

    private ZipDetailDto convertToZipDetailDto(Zip zip) {
        ZipDetailDto zipDetailDto = modelMapper.map(zip, ZipDetailDto.class);
        return zipDetailDto;
    }
}
