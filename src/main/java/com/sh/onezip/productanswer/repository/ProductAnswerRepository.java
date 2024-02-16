package com.sh.onezip.productanswer.repository;

import com.sh.onezip.productanswer.entity.ProductAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductAnswerRepository extends JpaRepository<ProductAnswer, Long> {

    @Query("from ProductAnswer pa where pa.productQuestion.id = :id")
    ProductAnswer findByProductQuestionId(Long id);
}
