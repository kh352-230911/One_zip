package com.sh.onezip.zip.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.neighbor.entity.Neighbor;
import com.sh.onezip.neighbor.service.NeighborService;
import com.sh.onezip.zip.dto.ZipCreateDto;
import com.sh.onezip.zip.dto.ZipDetailDto;
import com.sh.onezip.zip.dto.ZipUpdateDto;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zip.service.ZipService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/zip")
@Slf4j
@Validated
public class ZipController {
    @Autowired
    ZipService zipService;
    @Autowired
    NeighborService neighborService;
    @ModelAttribute
    public void addCommonAttributes(Model model, @RequestParam(required = false) Long id, HttpServletRequest req) {
        if(req.getRequestURI().indexOf("/zipCreate.do") > 0 || req.getRequestURI().indexOf("/zipSearch") > 0)
            return;
        ZipDetailDto zipDetailDto = zipService.findById(id);
        if (zipDetailDto != null) {
            model.addAttribute("zip", zipDetailDto);
            model.addAttribute("zipContent", zipDetailDto.getContent()); // Zip 객체의 소개글
            model.addAttribute("zipColorCode", zipDetailDto.getColorCode()); // Zip 객체의 색상
            model.addAttribute("zipRegDate", zipDetailDto.getRegDate()); // Zip 생성일
            model.addAttribute("zipDayCount", zipDetailDto.getDayCount()); // Zip 일일 조회수
            model.addAttribute("zipTotalCount", zipDetailDto.getTotalCount()); // Zip 누적 조회수
            model.addAttribute("zipId", zipDetailDto.getId());

            // Zip 객체와 관련된 회원 정보 가져오기
            Member member = zipDetailDto.getMember();
            if (member != null) {
                String memberName = member.getName(); // 회원의 이름 가져오기
                String memberId = member.getMemberId();

                model.addAttribute("memberName", memberName); // 모델에 회원의 이름 추가
                model.addAttribute("memberId", memberId);

                List<Neighbor> neighbors = member.getNeighbor(); // zip 객체와 관련된 회원의 이웃 정보
                model.addAttribute("neighbors", neighbors);
            }
            log.debug("ZipDetailDto object added to the model: {}", zipDetailDto);
        }
    }

    @GetMapping("/zipDetail.do")
    public void zipDetail(@RequestParam("id") Long id, Model model){
        ZipDetailDto zipDetailDto = zipService.findById(id);
        zipService.updateViewCounts(id);
        model.addAttribute("zip", zipDetailDto);
        log.debug("zip = {}", zipDetailDto);
    }
    @GetMapping("/zipCreate.do")
    public void zipCreate(){}

    @PostMapping("/zipCreate.do")
    public String zipCreate(
            @Valid ZipCreateDto zipCreateDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberDetails memberDetails,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req){
        if(bindingResult.hasErrors()){
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.debug("message = {}", message);
            throw new RuntimeException(message);
        }
        zipCreateDto.setMemberId(memberDetails.getMember().getMemberId());
        Zip zip = zipService.zipCreate(zipCreateDto);
        req.getSession().setAttribute("zip", zip);
        redirectAttributes.addFlashAttribute("msg", "집 생성을 축하합니다.");
        return "redirect:/";
    }

    @RequestMapping(value = "/zipSearch", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map zipSearch(@RequestParam("memberId") String memberId) {
        // memberId로 회원의 집 정보를 검색
        Optional<Zip> zipOptional = zipService.findByMemberId(memberId);
        HashMap map = new HashMap();
        if (zipOptional.isPresent()) {
            // 검색된 집의 ID를 가져와서 상세 정보 페이지로 리다이렉트
            long zipId = zipOptional.get().getId();
            map.put("zipId", zipId);
        } else {
            map.put("zipId", "error");
            // 검색된 회원의 집이 없는 경우 적절한 처리를 할 수 있음
            // 예를 들어 에러 페이지로 이동하거나 메시지를 표시할 수 있음
        }
        return map;
    }


    //    @PostMapping("/zipUpdate.do")
//    public String zipUpdate(@Valid ZipUpdateDto zipUpdateDto,
//                            BindingResult bindingResult,
//                            RedirectAttributes redirectAttributes){
//        log.debug("zipUpdateDto = {}", zipUpdateDto);
//        if(bindingResult.hasErrors()){
//            StringBuilder message = new StringBuilder();
//            bindingResult.getAllErrors().forEach((err) -> {
//                message.append(err.getDefaultMessage() + " ");
//            });
//            throw new RuntimeException(message.toString());
//        }
//
//        redirectAttributes.addFlashAttribute("msg", "집 정보가 수정되었습니다.");
//
//        return "redirect:/zip/zipDetail.do";
//    }
    @GetMapping("/diary.do")
    public void diary(){}
    @GetMapping("/photo.do")
    public void photo(){}
    @GetMapping("/visit.do")
    public void visit(){}
}
