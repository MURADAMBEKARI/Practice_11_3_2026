/**
 * 
 */
package com.project.nextgen.virusScan;

/**
 * @author CMJL-Murad Ambekari
 *
 */

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.project.nextgen.minio.MinioService;
import com.project.nextgen.model.DocumentVersion;
import com.project.nextgen.repository.DocumentVersionRepository;
 
@Service
public class DocumentScanConsumer {
 
    @Autowired
    private MinioService minioService;
 
    @Autowired
    private VirusScanService virusScanService;
 
    @Autowired
    private DocumentVersionRepository versionRepository;
 
    @KafkaListener(topics = "document-events", groupId = "scan-group")
    public void consume(DocumentVersion event) {
 
        try {
            // 1. Download file from MinIO
            InputStream inputStream = minioService.getFile(event.getObjectName());
 
            // 2. Scan file
            boolean isClean = virusScanService.scan(inputStream);
 
            // 3. Update status
            DocumentVersion v = versionRepository
                    .findByDocumentIdAndVersion(event.getDocumentId(), event.getVersion())
                    .orElseThrow();
 
            if (isClean) {
                v.setScanStatus("CLEAN");
            } else {
                v.setScanStatus("INFECTED");
            }
 
            versionRepository.save(v);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
