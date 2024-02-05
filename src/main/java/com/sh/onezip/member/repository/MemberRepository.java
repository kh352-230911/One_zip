package com.sh.onezip.member.repository;

import com.sh.onezip.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

    Member findByName(String name);

    Member findByMemberId(String memberId);
}
