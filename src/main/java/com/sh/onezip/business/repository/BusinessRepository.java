package com.sh.onezip.business.repository;

import com.sh.onezip.business.entity.Business;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Table(name="tb_business")
@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    // HBK start
    @Query("SELECT b FROM Business b WHERE b.id =:id")
    Business findBizmember(Long id);

    @Query("SELECT b FROM Business b WHERE b.bizName =:bizName")
    Business findByBizName(String bizName);
    // HBK end
}