package com.sh.onezip.zip.service;

import com.sh.onezip.zip.dto.ZipCreateDto;
import com.sh.onezip.zip.dto.ZipDetailDto;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.repository.ZipRepository;
import com.sh.onezip.attachment.repository.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public ZipDetailDto findById(Long id){
        return zipRepository.findById(id)
                .map((zip) -> convertToZipDetailDto(zip))
                .orElseThrow();
    }

    private ZipDetailDto convertToZipDetailDto(Zip zip){
       ZipDetailDto zipDetailDto = modelMapper.map(zip, ZipDetailDto.class);
       return zipDetailDto;
    }


    public Optional<Zip> findByMemberId(String memberId){
        return  zipRepository.findByMemberId(memberId);
    }

    public Zip zipCreate(ZipCreateDto zipCreateDto){
        Zip zip = zipRepository.save(convertToZip(zipCreateDto));
        return zip;
    }

    public Zip convertToZip(ZipCreateDto zipCreateDto){
        // ModelMapper를 사용하여 DTO를 엔티티로 변환
        modelMapper.typeMap(ZipCreateDto.class, Zip.class).addMappings(mapper->{
            mapper.skip(Zip::setId);
        });
        return modelMapper.map(zipCreateDto, Zip.class);
    }

    // 일일 조회수 업데이트 및 조회 시간 갱신
    public void updateViewCounts(Long zipId) {
        Optional<Zip> optionalZip = zipRepository.findById(zipId);
        if (optionalZip.isPresent()) {
            Zip zip = optionalZip.get();
            LocalDateTime now = LocalDateTime.now();
            // 마지막 조회 시간이 없거나 오늘과 다른 날이면 일일 조회수 초기화
            if (zip.getLastViewedAt() == null || !isSameDay(zip.getLastViewedAt(), now)) {
                zip.setDayCount(0);
            }
            // 일일 조회수 업데이트
            zip.setDayCount(zip.getDayCount() + 1);
            // 누적 조회수 업데이트
            zip.setTotalCount(zip.getTotalCount() + 1);
            // 마지막 조회 시간 업데이트
            zip.setLastViewedAt(now);
            // 변경사항을 저장
            zipRepository.save(zip);
        }
    }

    // 두 날짜가 같은 날인지 확인하는 메서드
    private boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        return date1.toLocalDate().isEqual(date2.toLocalDate());
    }

    public Zip updateZip(Zip zip){
        Zip _zip = zipRepository.findById(zip.getId()).orElse(null);
        return zipRepository.save(zip);
    }
}
