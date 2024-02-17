package com.sh.onezip.productquestion.repository;

import com.sh.onezip.productquestion.entity.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {

    @Query("From ProductQuestion p where p.id =:id2")
    ProductQuestion findByProductQuestionAnswerId(Long id2);
}

