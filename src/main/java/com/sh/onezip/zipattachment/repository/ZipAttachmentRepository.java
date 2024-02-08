package com.sh.onezip.zipattachment.repository;

import com.sh.onezip.zip.entity.Zip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipAttachmentRepository extends JpaRepository<Zip, Long> {
}
