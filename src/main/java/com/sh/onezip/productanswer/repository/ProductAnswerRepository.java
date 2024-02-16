package com.sh.onezip.productanswer.repository;

import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductAnswerRepository extends JpaRepository<ProductAnswer, Long> {
    @Query("From ProductQuestion p where p.id =:id2")
    ProductQuestion findByProductQuestionId(Long id2);
}
//@Query("FROM Product p WHERE p.businessmember.bizMemberId = :bizMemberId")