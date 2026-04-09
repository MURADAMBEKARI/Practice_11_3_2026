package com.project.nextgen.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.nextgen.kafka.KafkaProducerService;
import com.project.nextgen.minio.MinioService;
import com.project.nextgen.model.DocumentData;
import com.project.nextgen.model.DocumentVersion;
import com.project.nextgen.model.UploadResponse;

@Service
public class DocumentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MinioService minioService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // ✅ Create collections for new tenant
    public void createCollectionForTenant(String tenantId) {

        String docCollection = "document_" + tenantId;
        String versionCollection = "document_versions_" + tenantId;

        if (!mongoTemplate.collectionExists(docCollection)) {
            mongoTemplate.createCollection(docCollection);
        }

        if (!mongoTemplate.collectionExists(versionCollection)) {
            mongoTemplate.createCollection(versionCollection);
        }
    }

    // ✅ Upload API
    public UploadResponse upload(MultipartFile file, String entityId, String tenantId) throws Exception {

        // 🔥 Step 1: Ensure collections exist
        createCollectionForTenant(tenantId);

        // 🔥 Step 2: Prepare identifiers
        String documentId = UUID.randomUUID().toString();
        int version = 1;

//        String objectName = tenantId + "/" + documentId + "/v" + version;
//
//        // 🔥 Step 3: Upload to MinIO (bucket per customer)
//        minioService.uploadFile(
//                tenantId,
//                objectName,
//                file.getInputStream(),
//                file.getSize(),
//                file.getContentType()
//        );
        

        // ✅ Get original file name
        String fileName = file.getOriginalFilename();

        // (Optional but recommended) clean filename
        fileName = fileName.replaceAll("\\s+", "_");

        // ✅ FULL object path (IMPORTANT FIX)
        String objectName = tenantId + "/" + documentId + "/v" + version + "/" + fileName;

        // Step 3: Upload to MinIO
        minioService.uploadFile(
                tenantId,                 
                objectName,              
                file.getInputStream(),
                file.getSize(),
                file.getContentType()
        );

        // ✅ Send correct path in event
        System.out.println("inside DocumentService UPLOAD PATH: " + objectName);

        String downloadUrl = minioService.getDownloadUrl(tenantId, objectName);

	    System.out.println("inside DocumentService  Download file MINIO  "+downloadUrl);

        
        // 🔥 Step 4: Dynamic collection names
        String docCollection = "document_" + tenantId;
        String versionCollection = "document_versions_" + tenantId;

        // 🔥 Step 5: Save Document (Mongo)
        DocumentData document = new DocumentData();
        document.setDocumentId(documentId);
        document.setBusinessEntityId(entityId);
        document.setLatestVersion(version);
        document.setStatus("ACTIVE");
        document.setCreatedAt(LocalDateTime.now());

        mongoTemplate.save(document, docCollection);

        // 🔥 Step 6: Save Version (Mongo)
        DocumentVersion v = new DocumentVersion();
        v.setTenantId(tenantId);
        v.setDocumentId(documentId);
        v.setVersion(version);
        v.setFileName(file.getOriginalFilename());
        v.setContentType(file.getContentType());
        v.setFileSize(file.getSize());
        v.setBucketName(tenantId); // bucket = tenantId
        v.setObjectName(objectName);
        v.setOcrStatus("PENDING");
        v.setCreatedAt(LocalDateTime.now());

        mongoTemplate.save(v, versionCollection);

        v.setScanStatus("PENDING");
        
        // 🔥 Step 7: Send Kafka Event
        kafkaProducerService.publishDocumentEvent(v);
	    System.out.println("inside DocumentService  After step 7 ");

        // 🔥 Step 8: Response
        UploadResponse response = new UploadResponse();
        response.setDocumentId(documentId);
        response.setDownloadUrl(downloadUrl);
        
	    System.out.println("inside DocumentService  After step 8 ");


        return response;
    }
}