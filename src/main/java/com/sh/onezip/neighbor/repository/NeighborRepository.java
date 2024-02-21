package com.sh.onezip.neighbor.repository;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.neighbor.entity.Neighbor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.persistence.Tuple;

import java.util.List;


@Repository
public interface NeighborRepository extends JpaRepository<Neighbor, Long> {

    @Query("SELECT COUNT(n) > 0 FROM Neighbor n WHERE (n.member1 = :member1 AND n.member2 = :member2) OR (n.member2 = :member1 AND n.member1 = :member2)")
    boolean existsByMember1AndMember2(Member member1, Member member2);
    @Query("SELECT n.id AS id, " +
            "DECODE(n.member1,:member,n.member2.memberId,n.member1.memberId) AS memberId, " +
            "DECODE(n.member1,:member,n.member2.name,n.member1.name) AS memberName, " +
            "DECODE(n.member1,:member,n.member2.zip.id,n.member1.zip.id) AS member_zip_id," +
            "n.requestedAt as requestedAt FROM Neighbor  n WHERE n.member1 = :member OR n.member2 = :member")
    List<Tuple> findNeighborsByMember(Member member);

}