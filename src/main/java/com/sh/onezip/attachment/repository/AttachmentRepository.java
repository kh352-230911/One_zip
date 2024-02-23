package com.sh.onezip.attachment.repository;

import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.zip.entity.Zip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query("from Attachment z where z.refId = :zipId")
    Optional<Attachment> findById(@Param("zipId") Long zipId);
}
