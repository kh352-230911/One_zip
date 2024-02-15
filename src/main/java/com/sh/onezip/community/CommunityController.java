package com.sh.onezip.community;

import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.service.ZipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/community")
@Slf4j
public class CommunityController {

    @Autowired
    ZipService zipService;

    @ModelAttribute
    public void addCommonAttributes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String memberId = userDetails.getUsername(); // 현재 로그인한 회원의 아이디 가져오기
            Optional<Zip> zipOptional = zipService.findByMemberId(memberId);
            if (zipOptional.isPresent()) {
                Zip zip = zipOptional.get();
                model.addAttribute("zipContent", zip.getContent()); // Zip 객체의 소개글
                model.addAttribute("zipColorCode", zip.getColorCode()); // Zip 객체의 색상
                model.addAttribute("zipRegDate", zip.getRegDate()); // Zip 생성일
                model.addAttribute("zipDayCount", zip.getDayCount()); // Zip 일일 조회수
                model.addAttribute("zipTotalCount", zip.getTotalCount()); // Zip 누적 조회수
            }
        }
        List<String> images = Arrays.asList("/방.jpg", "/방2.jpg", "/방3.jpg");
        model.addAttribute("images", images);
    }

    @GetMapping("/home.do")
    public void home(){}
    @GetMapping("/diary.do")
    public void diary(){}
    @GetMapping("/photo.do")
    public void photo(){}
    @GetMapping("/visit.do")
    public void visit(){}
}
