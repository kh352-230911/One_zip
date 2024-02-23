package com.sh.onezip.tip.service;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.attachment.repository.AttachmentRepository;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.member.dto.MemberDetailDto;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.tip.dto.TipCommentDto;
import com.sh.onezip.tip.dto.TipCreateDto;
import com.sh.onezip.tip.dto.TipDetailDto;
import com.sh.onezip.tip.dto.TipListDto;
import com.sh.onezip.tip.entity.Tip;
import com.sh.onezip.tip.entity.TipComment;
import com.sh.onezip.tip.repository.TipCommentRepository;
import com.sh.onezip.tip.repository.TipRepository;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.repository.ZipRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class TipService {

    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private TipCommentRepository tipCommentRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ZipRepository zipRepository;

    public Page<TipListDto> findAllByZipId(Long zipId, Pageable pageable) {
        Page<Tip> tipPage = tipRepository.findByZipId(zipId, pageable);
        return tipPage.map((tip) -> convertToTipListDto(tip)); // Page<BoardListDto>
    }

    public void createTip(TipCreateDto tipCreateDto,String username) {
        Tip tip=tipRepository.save(convertToTip(tipCreateDto,username));


        tipCreateDto.getAttachments().forEach((attachmentCreateDto -> {
            attachmentCreateDto.setRefId(tip.getId());
            attachmentService.createAttachment(attachmentCreateDto);
        }));

    }

    private Tip convertToTip(TipCreateDto tipCreateDto,String username) {
        Member member = memberRepository.findByMemberId(username);
        Zip zip = zipRepository.findByUsername(username);
        Tip tip = modelMapper.map(tipCreateDto, Tip.class);
        tip.setMember(member);
        tip.setZip(zip);

        return tip;
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


    public Tip findTipEntityById(Long id) {
        return tipRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tip not found"));
    }



    public TipDetailDto findById(Long id) {
        // 팁 상세 정보를 조회합니다.
        Tip tip = tipRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tip not found"));

        // 팁 상세 정보를 TipDetailDto 객체로 변환합니다.
        TipDetailDto tipDetailDto = convertToTipDetailDto(tip);

        // 해당 팁에 연결된 댓글들을 조회합니다.
        List<TipComment> comments = tipCommentRepository.findByTipId(tip.getId());

        // 조회된 댓글 정보를 TipCommentDto 리스트로 변환합니다.
        List<TipCommentDto> commentDtos = comments.stream().map(this::convertToTipCommentDto).collect(Collectors.toList());

        // 변환된 댓글 리스트를 TipDetailDto 객체에 설정합니다.
        tipDetailDto.setComments(commentDtos);

        return tipDetailDto;
    }

    private TipDetailDto convertToTipDetailDto(Tip tip) {
        // Tip 엔티티를 TipDetailDto로 변환하는 로직을 구현합니다.
        // 예를 들어 ModelMapper 라이브러리를 사용하거나, 직접 필드를 매핑할 수 있습니다.
        TipDetailDto dto = new TipDetailDto();
        dto.setId(tip.getId());
        dto.setTitle(tip.getTipTitle());
        dto.setContent(tip.getTipContent());
        dto.setCreatedAt(tip.getRegDate());
        // dto의 나머지 필드를 설정합니다.
        return dto;
    }

    private TipCommentDto convertToTipCommentDto(TipComment comment) {
        // TipComment 엔티티를 TipCommentDto로 변환하는 로직을 구현합니다.
        TipCommentDto dto = new TipCommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getCommentContent());
        dto.setCreatedAt(comment.getRegDate());
        // dto의 나머지 필드를 설정합니다.
        MemberDetailDto memberDto = new MemberDetailDto();
        memberDto.setMemberId(comment.getMember().getMemberId());
        memberDto.setName(comment.getMember().getName());
        dto.setMember(memberDto);
        return dto;
    }
}
