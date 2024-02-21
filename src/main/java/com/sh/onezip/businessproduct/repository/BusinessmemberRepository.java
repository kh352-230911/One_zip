package com.sh.onezip.businessproduct.repository;

import com.sh.onezip.businessproduct.entity.Businessmember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BusinessmemberRepository extends JpaRepository<Businessmember, String> {
    // 사업자 회원가입용
    @Query("from Businessmember b where b.bizMemberId =:bizMemberId")
    Businessmember findByBizMemberId(String bizMemberId);

}
