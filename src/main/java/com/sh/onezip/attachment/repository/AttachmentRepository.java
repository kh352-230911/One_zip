package com.sh.onezip.attachment.repository;

import com.sh.onezip.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
