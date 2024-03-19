package com.sh.onezip.customeranswercenter.repository;

import com.sh.onezip.customeranswercenter.entity.AnswerCenter;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Table(name="tb_aone")
@Repository
public interface AnswerCenterRepository extends JpaRepository<AnswerCenter, Long> {

    // HBK start
//    @Query("SELECT ac FROM AnswerCenter ac WHERE ac.questionCenter.id = :qid")
//    AnswerCenter findByQId(Long qid);
//    @Query("SELECT ac FROM AnswerCenter ac WHERE ac.id = :aid")
//    AnswerCenter findByAId(@Param("id") Long aid);
    // HBK end
}
