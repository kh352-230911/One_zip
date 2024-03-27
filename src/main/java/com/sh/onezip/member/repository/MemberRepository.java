package com.sh.onezip.member.repository;

import com.sh.onezip.member.entity.Member;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Table(name = "tb_member")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> { // id 타입이 Long인 것은 유지

    Member findByName(String name);

    // memberId에 해당하는 메서드를 String 타입으로 변경
    @Query("FROM Member m JOIN FETCH m.authorities WHERE m.memberId = :memberId")
    Member findByMemberId(String memberId);

    @Query("FROM Member m JOIN FETCH m.authorities WHERE m.memberId = :memberId")
    Optional<Member> findByMemberIdOptional(String memberId);

    // HBK start
    // 사업자 등록 페이지
    @Query("from Member where id = :id")
    Member findByMId(Long id);

    @Query("from Member order by regDate asc")
    Page<Member> findAllMembers(Pageable pageable);
}

