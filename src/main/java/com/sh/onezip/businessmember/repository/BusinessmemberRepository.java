package com.sh.onezip.businessmember.repository;

import com.sh.onezip.businessmember.entity.Businessmember;
import com.sh.onezip.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BusinessmemberRepository extends JpaRepository<Businessmember, String> {
    Businessmember findByBizMemberId(String bizMemberId);

}
