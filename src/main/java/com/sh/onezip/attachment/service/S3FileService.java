package com.sh.onezip.attachment.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.dto.AttachmentDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class S3FileService {

    @Autowired
    private AmazonS3Client amazonS3Client;
    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public AttachmentCreateDto upload(MultipartFile upFile) throws IOException {
        String key = UUID.randomUUID().toString();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(upFile.getContentType());
        objectMetadata.setContentLength(upFile.getSize());

        // 업로드
        amazonS3Client.putObject(bucket, key, upFile.getInputStream(), objectMetadata);
        // url조회
        String url = amazonS3Client.getUrl(bucket, key).toString();
        return new AttachmentCreateDto(null, null, upFile.getOriginalFilename(), key, url);
    }

    public ResponseEntity<?> download(AttachmentDetailDto attachmentDetailDto) throws UnsupportedEncodingException {
        Resource resource = resourceLoader.getResource(attachmentDetailDto.getUrl());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(attachmentDetailDto.getOriginalFilename(), "UTF-8"))
                .body(resource);
    }
}