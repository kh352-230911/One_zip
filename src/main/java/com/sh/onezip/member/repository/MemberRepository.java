package com.sh.onezip.member.repository;

import com.sh.onezip.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("from Member m join fetch m.authorities where m.name = :name")
    Member findByName(String name);

    Member findByMemberId(String memberId);
}
