package com.sh.onezip.diary.repository;

import com.sh.onezip.diary.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT d from Diary d left join fetch d.zip")
    Page<Diary> findAll(Pageable pageable);
    @Query("SELECT d FROM Diary d WHERE d.zip.id = :zipId")
    Page<Diary> findByZipId(Long zipId, Pageable pageable);
}
