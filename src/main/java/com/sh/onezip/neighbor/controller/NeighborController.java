package com.sh.onezip.neighbor.controller;

import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import com.sh.onezip.neighbor.entity.Neighbor;
import com.sh.onezip.neighbor.service.NeighborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        Member member = memberDetails.getMember();
        List<Neighbor> neighbors = neighborService.findNeighborsByMember(member);
        model.addAttribute("neighbors", neighbors);
        return "neighbor/list";
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
}
