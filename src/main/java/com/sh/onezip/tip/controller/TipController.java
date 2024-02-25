package com.sh.onezip.tip.controller;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.dto.AttachmentDetailDto;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.attachment.service.S3FileService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.diary.dto.DiaryCreateDto;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.tip.dto.TipCreateDto;
import com.sh.onezip.tip.dto.TipDetailDto;
import com.sh.onezip.tip.dto.TipListDto;
import com.sh.onezip.tip.entity.Tip;
import com.sh.onezip.tip.entity.TipComment;
import com.sh.onezip.tip.service.TipCommentService;
import com.sh.onezip.tip.service.TipService;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.repository.ZipRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/community")
public class TipController {
    @Autowired
    private TipService tipService;
    @Autowired
    private S3FileService s3FileService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    private TipCommentService tipCommentService;
    @Autowired
    private ZipRepository zipRepository;


    @GetMapping("/tipList.do")
    public String tipList(@PageableDefault(size = 5, page = 0) Pageable pageable,
                          @AuthenticationPrincipal MemberDetails memberDetails,
                          Model model) {
        Zip zip= zipRepository.findByUsername(memberDetails.getUsername());
        model.addAttribute("zip",zip);
        model.addAttribute("pfAttachments", attachmentService.findByIdWithType(zip.getId(), "PF"));
        System.out.println("flag1");
        model.addAttribute("stAttachments", attachmentService.findByIdWithType(zip.getId(), "ST"));
        System.out.println("flag2");
        model.addAttribute("roAttachments", attachmentService.findZipAttachmentToList(zip.getId(), "RO"));
        System.out.println("flag3");

        Page<TipListDto> tipPage = tipService.findAllByZipId(zip.getId(), pageable);
        Page<TipListDto> latestTips = tipService.findAllByZipId(zip.getId(), PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "regDate")));
        model.addAttribute("latestTips", latestTips.getContent());
        log.debug("tips = {}", tipPage.getContent());
        model.addAttribute("tips", tipPage.getContent());
        model.addAttribute("totalCount", tipPage.getTotalElements()); // 전체 게시물수
        model.addAttribute("tipPage", tipPage); // tipPage 객체를 모델에 추가
        return "community/tipList";
    }

    @GetMapping("/createTip.do")
    public String createTipForm( @AuthenticationPrincipal MemberDetails memberDetails,
                                 @PageableDefault(size = 5, page = 0) Pageable pageable,
                                 Model model) {
        Zip zip= zipRepository.findByUsername(memberDetails.getUsername());
        model.addAttribute("zip",zip);
        model.addAttribute("pfAttachments", attachmentService.findByIdWithType(zip.getId(), "PF"));
        System.out.println("flag1");
        model.addAttribute("stAttachments", attachmentService.findByIdWithType(zip.getId(), "ST"));
        System.out.println("flag2");
        model.addAttribute("roAttachments", attachmentService.findZipAttachmentToList(zip.getId(), "RO"));
        model.addAttribute("diaryCreateDto", new DiaryCreateDto());
        model.addAttribute("tipCreateDto", new TipCreateDto());
        Page<TipListDto> tipPage = tipService.findAllByZipId(zip.getId(), pageable);
        Page<TipListDto> latestTips = tipService.findAllByZipId(zip.getId(), PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "regDate")));
        model.addAttribute("latestTips", latestTips.getContent());
        log.debug("tips = {}", tipPage.getContent());
        model.addAttribute("tips", tipPage.getContent());
        return "community/createTip";
    }
    @PostMapping("/createTip.do")
    public String createTip(
            @Valid TipCreateDto tipCreateDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberDetails memberDetails,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes)
            throws IOException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        tipService.createTip(tipCreateDto,memberDetails.getUsername());
        // 리다이렉트후에 사용자피드백
        redirectAttributes.addFlashAttribute("msg", "🎈🎈🎈 게시글을 성공적으로 등록했습니다. 🎈🎈🎈");
        return "redirect:/community/tipList.do";
    }


    @GetMapping("/tipDetail.do")
    public void tipDetail( @AuthenticationPrincipal MemberDetails memberDetails,
                           @RequestParam("id") Long id, Model model) {
        Zip zip= zipRepository.findByUsername(memberDetails.getUsername());
        model.addAttribute("zip",zip);
        model.addAttribute("pfAttachments", attachmentService.findByIdWithType(zip.getId(), "PF"));
        System.out.println("flag1");
        model.addAttribute("stAttachments", attachmentService.findByIdWithType(zip.getId(), "ST"));
        System.out.println("flag2");
        model.addAttribute("roAttachments", attachmentService.findZipAttachmentToList(zip.getId(), "RO"));
        model.addAttribute("diaryCreateDto", new DiaryCreateDto());
        TipDetailDto tipDetailDto = tipService.findById(id);
        model.addAttribute("tip", tipDetailDto);
        log.debug("tip = {}", tipDetailDto);

    }

    @GetMapping("/fileDownload.do")
    public ResponseEntity<?> fileDownload(@RequestParam("id") Long id, @RequestParam("refType") String refType) throws UnsupportedEncodingException {
        AttachmentDetailDto attachmentDetailDto = attachmentService.findByIdWithType(id, refType);
        return s3FileService.download(attachmentDetailDto);
    }

    @PostMapping("/createComment.do")
    public String createComment(
            @Valid TipComment tipComment,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("tipId") Long tipId, // tipId를 요청 파라미터로 추가
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Tip tip = tipService.findTipEntityById(tipId); // Tip 엔티티 조회
        tipComment.setTip(tip);
        tipComment.setMember(memberDetails.getMember());
        tipCommentService.createComment(tipComment);
        redirectAttributes.addFlashAttribute("msg", "댓글을 성공적으로 등록했습니다.");
        return "redirect:/community/tipDetail.do?id=" + tipComment.getTip().getId(); // 댓글을 단 팁 상세보기 페이지로 리다이렉트
    }

    @PostMapping("/deleteComment.do")
    public String deleteComment(@RequestParam("commentId") Long commentId,
                                @RequestParam("tipId") Long tipId,
                                RedirectAttributes redirectAttributes) {
        tipCommentService.deleteComment(commentId);
        redirectAttributes.addFlashAttribute("msg", "댓글을 성공적으로 삭제했습니다.");
        return "redirect:/tipDetail.do?id=" + tipId; // 댓글이 삭제된 후의 팁 상세보기 페이지로 리다이렉트
    }

}
