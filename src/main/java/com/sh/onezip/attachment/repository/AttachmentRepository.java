package com.sh.onezip.attachment.repository;

import com.sh.onezip.attachment.entity.Attachment;
//import com.sh.onezip.zip.entity.Zip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    @Query("from Attachment z where z.refId = :zipId and z.refType = :refType order by z.regDate desc")
    Stream<Attachment> findTopByOrderByRegDateDesc(@Param("zipId") Long zipId, @Param("refType") String refType);

    @Query("from Attachment z where z.refId = :zipId and z.refType = :refType order by z.regDate desc")
    List<Attachment> findZipAttachmentToList(@Param("zipId") Long zipId, @Param("refType") String refType);

    @Query("from Attachment p where p.refId = :refId and p.refType = :refType order by p.refType")
    List<Attachment> findProductAttachmentToList(@Param("refId") Long refId, @Param("refType") String refType);

    @Query("from Attachment p where p.refId = :refId and p.refType = :refType order by p.refType")
    List<Attachment> findBusinessAttachment(@Param("refId")Long id, @Param("refType")String refType);
}
