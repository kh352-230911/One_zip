package com.sh.onezip.businessproduct.repository;

import com.sh.onezip.businessproduct.entity.Businessmember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BusinessmemberRepository extends JpaRepository<Businessmember, String> {
    Businessmember findByBizMemberId(String bizMemberId);

}
