package com.sh.onezip.bizauthority.repository;

import com.sh.onezip.bizauthority.entity.BizAuthority;
import com.sh.onezip.businessproduct.entity.Businessmember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BizAuthorityRepository extends JpaRepository<BizAuthority, Long> {

}

