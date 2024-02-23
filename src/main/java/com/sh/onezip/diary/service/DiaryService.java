package com.sh.onezip.diary.service;

import com.sh.onezip.diary.dto.DiaryCreateDto;
import com.sh.onezip.diary.dto.DiaryListDto;
import com.sh.onezip.diary.entity.Diary;
import com.sh.onezip.diary.repository.DiaryRepository;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.repository.ZipRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class DiaryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private ZipRepository zipRepository;

    public Page<DiaryListDto> findAll(Pageable pageable) {
        Page<Diary> diaryPage = diaryRepository.findAll(pageable);
        return diaryPage.map((diary) -> convertToDiaryListDto(diary)); // Page<BoardListDto>
    }
    public Page<DiaryListDto> findAllByZipId(Long zipId, Pageable pageable) {
        Page<Diary> diaryPage = diaryRepository.findByZipId(zipId, pageable);
        return diaryPage.map(this::convertToDiaryListDto);
    }
    private DiaryListDto convertToDiaryListDto(Diary diary) {
        DiaryListDto diaryListDto = modelMapper.map(diary, DiaryListDto.class);
        diaryListDto.setZipId(
                Optional.ofNullable(diary.getZip())
                        .map((zip) -> zip.getId())
                        .orElse(null)
        );
        return diaryListDto;
    }
    public void createDiary(DiaryCreateDto diaryCreateDto, Member  member) {
        Zip zip = zipRepository.findByMemberId(member.getMemberId())
                .orElseThrow(() -> new RuntimeException("Zip not found for the member"));

        // 찾은 Zip의 ID를 DiaryCreateDto에 설정합니다.
        diaryCreateDto.setZipId(zip.getId());

        // 나머지 다이어리 생성 로직...
        diaryRepository.save(convertToDiary(diaryCreateDto));
    }

    private Diary convertToDiary(DiaryCreateDto diaryCreateDto) {
        return modelMapper.map(diaryCreateDto,Diary.class);
    }
}
