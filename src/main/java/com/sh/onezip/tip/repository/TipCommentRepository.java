package com.sh.onezip.tip.repository;

import com.sh.onezip.tip.entity.TipComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipCommentRepository extends JpaRepository<TipComment, Long> {
    List<TipComment> findByTipId(Long tipId);
}
