package com.sh.onezip.productquestion.repository;

import com.sh.onezip.productquestion.entity.ProductQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {

    //    @Query("From ProductQuestion p where p.id =:id")
//    List<ProductQuestion> findByProductQuestionAnswerId(Long id);
//
//    @Query("SELECT p FROM ProductAnswer p LEFT JOIN FETCH p.productQuestion WHERE p.productQuestion.id = :id")
//    Page<ProductQuestion> findOneQuestion(Pageable pageable, Long id);
//}
    @Query("SELECT p FROM ProductQuestion p WHERE p.id = :id")
    ProductQuestion findByProductQuestionAnswerId(Long id);

    @Query("SELECT p FROM ProductQuestion p LEFT JOIN FETCH p.productAnswer WHERE p.productAnswer.id = :id")
    Page<ProductQuestion> findOneQuestion(Pageable pageable, Long id);
}