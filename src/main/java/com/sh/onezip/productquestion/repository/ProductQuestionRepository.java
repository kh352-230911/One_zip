package com.sh.onezip.productquestion.repository;

import com.sh.onezip.productquestion.entity.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {
}
