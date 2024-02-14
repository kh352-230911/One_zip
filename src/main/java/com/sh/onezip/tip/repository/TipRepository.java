package com.sh.onezip.tip.repository;

import com.sh.onezip.tip.entity.Tip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipRepository extends JpaRepository<Tip, Long> {

    @Query("from Tip t left join fetch t.member m left join fetch t.attachments order by t.id desc")
    Page<Tip> findAll(Pageable pageable);
}
