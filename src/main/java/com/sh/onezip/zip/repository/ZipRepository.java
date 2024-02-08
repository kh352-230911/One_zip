package com.sh.onezip.zip.repository;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.zip.entity.Zip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZipRepository extends JpaRepository<Zip, Long> {

//    @Query("from Zip z left join fetch z.attachments a where z.id = :id")
    Optional<Zip> findById(Long id);

//    @Query("FROM zip z left JOIN FETCH m.authorities WHERE m.memberId = :memberId")
    @Query("from Zip z left join fetch z.member m where z.member = :member")
    Optional<Zip> findByMember(Member member);
    @Query("from Zip z left join fetch z.member m where z.member = :member")
    List<Zip> findByMembers(Member member);
}
