package com.sh.onezip.neighbor.repository;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.neighbor.entity.Neighbor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NeighborRepository extends JpaRepository<Neighbor, Long> {

    @Query("SELECT COUNT(n) > 0 FROM Neighbor n WHERE (n.member1 = :member1 AND n.member2 = :member2) OR (n.member2 = :member1 AND n.member1 = :member2)")
    boolean existsByMember1AndMember2(Member member1, Member member2);
    @Query("FROM Neighbor  n WHERE n.member1 = :member OR n.member2 = :member")
    List<Neighbor> findNeighborsByMember(Member member);

}