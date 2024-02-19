package com.sh.onezip.tip.service;

import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.tip.dto.TipCreateDto;
import com.sh.onezip.tip.dto.TipDetailDto;
import com.sh.onezip.tip.dto.TipListDto;
import com.sh.onezip.tip.entity.Tip;
import com.sh.onezip.tip.repository.TipRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TipService {

    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AttachmentService attachmentService;
    public Page<TipListDto> findAll(Pageable pageable) {
        Page<Tip> tipPage = tipRepository.findAll(pageable);
        return tipPage.map((tip) -> convertToTipListDto(tip)); // Page<BoardListDto>
    }

    private TipListDto convertToTipListDto(Tip tip) {
        TipListDto tipListDto = modelMapper.map(tip, TipListDto.class);
        tipListDto.setMemberId(
                Optional.ofNullable(tip.getMember())
                        .map((member) -> member.getMemberId())
                        .orElse(null)
        );
        tipListDto.setAttachCount(tip.getAttachments().size());
        return tipListDto;
    }

    public void createTip(TipCreateDto tipCreateDto) {
        Tip tip=tipRepository.save(convertToTip(tipCreateDto));
        tipCreateDto.getAttachments().forEach((attachmentCreateDto -> {
            attachmentCreateDto.setRefId(tip.getId());
            attachmentService.createAttachment(attachmentCreateDto);
        }));
    }

    private Tip convertToTip(TipCreateDto tipCreateDto) {
        return modelMapper.map(tipCreateDto, Tip.class);
    }

    public TipDetailDto findById(Long id) {
        return tipRepository.findById(id)
                .map((tip)->convertToTipDetailDto(tip))
                .orElseThrow();
    }

    private TipDetailDto convertToTipDetailDto(Tip tip) {
        TipDetailDto boardDetailDto=modelMapper.map(tip,TipDetailDto.class);
        return boardDetailDto;
    }
}
