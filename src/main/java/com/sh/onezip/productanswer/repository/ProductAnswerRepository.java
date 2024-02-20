package com.sh.onezip.productanswer.repository;

import com.sh.onezip.productanswer.entity.ProductAnswer;

import com.sh.onezip.productquestion.entity.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductAnswerRepository extends JpaRepository<ProductAnswer, Long> {

//    @Query("From ProductAnswer p where p.id =:pquestionNo")
//    List<ProductAnswer> findByQquestionNo(ProductAnswer pquestionNo);
}
