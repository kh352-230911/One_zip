package com.sh.onezip.zip.service;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.zip.dto.ZipCreateDto;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.repository.ZipRepository;
import com.sh.onezip.attachment.repository.AttachmentRepository;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
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
    public ModelMapper modelMapper;
    @Autowired
    private AttachmentRepository attachmentRepository;

    public Optional<Zip> findById(Long id){
        return zipRepository.findById(id);
    }

    public Optional<Zip> findByMemberId(String memberId){
        return  zipRepository.findByMemberId(memberId);
    }

    public void zipCreate(ZipCreateDto zipCreateDto){
        Zip zip = zipRepository.save(convertToZip(zipCreateDto));
    }

    public Zip convertToZip(ZipCreateDto zipCreateDto){
        // ModelMapper에 커스텀 컨버터 추가
        modelMapper.addConverter(new Converter<String, Long>() {
            public Long convert(org.modelmapper.spi.MappingContext<String, Long> context) {
                String source = context.getSource();
                try {
                    // 입력 문자열을 Long으로 변환하여 반환
                    return Long.parseLong(source);
                } catch (NumberFormatException e) {
                    // 숫자로만 구성되지 않은 문자열을 처리하는 로직 추가
                    // 여기에서는 null을 반환하도록 설정했습니다. 필요에 따라 다른 처리를 수행할 수 있습니다.
                    return null;
                }
            }
        });

        // ModelMapper를 사용하여 DTO를 엔티티로 변환
        return modelMapper.map(zipCreateDto, Zip.class);
    }

    public Zip updateZip(Zip zip){
        Zip _zip = zipRepository.findById(zip.getId()).orElse(null);
        return zipRepository.save(zip);
    }
}
