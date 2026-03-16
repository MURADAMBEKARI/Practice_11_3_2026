package com.project.nextgen.service;

import java.time.LocalDateTime;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.nextgen.kafka.KafkaProducerService;
import com.project.nextgen.minio.MinioService;
import com.project.nextgen.model.DocumentData;
import com.project.nextgen.model.DocumentVersion;
import com.project.nextgen.repository.DocumentRepository;
import com.project.nextgen.repository.DocumentVersionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

	@Autowired
    private  DocumentRepository documentRepository;
	
	@Autowired
    private  DocumentVersionRepository versionRepository;
	
	@Autowired
    private  MinioService minioService;
	
	@Autowired
    private  KafkaProducerService kafkaProducerService;

    public String upload(MultipartFile file,String entityId) throws Exception {

        String documentId = UUID.randomUUID().toString();

        int version = 1;

        String objectName = "documents/"+documentId+"/v1";

        minioService.uploadFile(
                objectName,
                file.getInputStream(),
                file.getSize(),
                file.getContentType()
        );

        DocumentData document = new DocumentData();

        document.setDocumentId(documentId);
        document.setBusinessEntityId(entityId);
        document.setLatestVersion(version);
        document.setStatus("ACTIVE");
        document.setCreatedAt(LocalDateTime.now());

        documentRepository.save(document);

        DocumentVersion v = new DocumentVersion();

        v.setDocumentId(documentId);
        v.setVersion(version);
        v.setFileName(file.getOriginalFilename());
        v.setContentType(file.getContentType());
        v.setFileSize(file.getSize());
        v.setBucketName("documents");
        v.setObjectName(objectName);
        v.setOcrStatus("PENDING");
        v.setCreatedAt(LocalDateTime.now());

        versionRepository.save(v);

        kafkaProducerService.publishDocumentEvent(v);

        return documentId;
    }
}