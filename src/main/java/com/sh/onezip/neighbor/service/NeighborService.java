package com.sh.onezip.neighbor.service;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.neighbor.entity.Neighbor;
import com.sh.onezip.neighbor.repository.NeighborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NeighborService {
    @Autowired
    private NeighborRepository neighborRepository;

    public List<Neighbor> findNeighborsByMember(Member member) {
        return neighborRepository.findNeighborsByMember(member);
    }

//    public List<Neighbor> findNeighborsByMember(String memberId) {
//        // memberId에 해당하는 Member 객체를 찾아서 그 회원의 이웃 목록 조회
//        Member member = new Member();
//        member.setMemberId(memberId);
//        return neighborRepository.findNeighborsByMember(member);
//    }

    // 이미 이웃인지 확인하는 메서드
    public boolean areNeighbors(Member member1, Member member2) {
        return neighborRepository.existsByMember1AndMember2(member1, member2)
                || neighborRepository.existsByMember1AndMember2(member2, member1);
    }

    // 이웃을 추가하는 메서드
    public void addNeighbor(String memberId1, String memberId2) {
        // memberId1과 memberId2에 해당하는 Member 객체를 찾아서 Neighbor 객체로 저장
        Member member1 = new Member();
        member1.setMemberId(memberId1);

        Member member2 = new Member();
        member2.setMemberId(memberId2);

        if (memberId1.equals(memberId2)) {
            throw new IllegalArgumentException("자기 자신을 이웃으로 추가할 수 없습니다.");
        }

        if (neighborRepository.existsByMember1AndMember2(member1, member2)) {
            throw new IllegalArgumentException("이미 이웃 관계입니다.");
        }

        // 이웃 관계를 저장
        Neighbor neighbor = new Neighbor();
        neighbor.setMember1(member1);
        neighbor.setMember2(member2);
        neighborRepository.save(neighbor);
    }
}
