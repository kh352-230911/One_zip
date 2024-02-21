package com.sh.onezip.neighbor.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.entity.Member;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import com.sh.onezip.neighbor.entity.Neighbor;
import com.sh.onezip.neighbor.service.NeighborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/neighbors")
@Slf4j
@Validated
public class NeighborController {
    @Autowired
    private NeighborService neighborService;

    @GetMapping("/list")
    public String listNeighbor(
            Model model,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        List<Tuple> neighbors = neighborService.findNeighborsByMember(memberDetails.getMember().getMemberId());
        model.addAttribute("neighbors", neighbors); // 모델에 neighbors 추가
        return "/list"; // 이웃 목록을 보여줄 뷰로 이동
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNeighbor(
            @RequestParam("memberId1") String memberId1,
            @RequestParam("memberId2") String memberId2) {
        try {
            neighborService.addNeighbor(memberId1, memberId2);
            return ResponseEntity.ok().body("이웃 관계가 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 이웃 삭제
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteNeighbor(
            @PathVariable("id") Long id) {
        try {
            neighborService.deleteNeighbor(id);
            return ResponseEntity.ok().body("이웃 관계가 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
