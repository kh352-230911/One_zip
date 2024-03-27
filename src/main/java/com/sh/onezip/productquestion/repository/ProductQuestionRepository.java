package com.sh.onezip.productquestion.repository;

import com.sh.onezip.productquestion.entity.ProductQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {
    @Query("select q from Product p left join fetch ProductQuestion q on p.id = q.product.id where p.id = :id")
    List<ProductQuestion> pquestionFindByProductid(Long id);

    @Query("from ProductQuestion pq where pq.product.id = :productId order by pq.id desc")
    Page<ProductQuestion> productQuestionFindAllByProductId(Pageable pageable, Long productId);

    @Query("from ProductQuestion pq where pq.product.id = :productId")
    List<ProductQuestion> productQuestionFindAllByProductId(@Param("productId") Long productId);

}
