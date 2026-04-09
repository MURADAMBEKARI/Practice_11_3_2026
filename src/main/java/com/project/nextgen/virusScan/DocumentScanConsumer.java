/**
 * 
 */
package com.project.nextgen.virusScan;

/**
 * @author CMJL-Murad Ambekari
 *
 */

import java.io.InputStream;
import java.util.Optional;

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
    	    System.out.println("inside DocumentScanConsumer Download file from MinIO  "+event);
    	    
    	    System.out.println("inside DocumentScanConsumer download PATH: " + event.getObjectName());

            InputStream inputStream = minioService.getFile(event.getObjectName());
 
            // 2. Scan file
    	    System.out.println("inside DocumentScanConsumer Scan file");

            boolean isClean = virusScanService.scan(inputStream);
 
            // 3. Update status
    	    System.out.println("inside DocumentScanConsumer Update status");
    	    System.out.println("event value" +event.getDocumentId()+" -- "+ event.getVersion());

//            DocumentVersion v = versionRepository
//                    .findByDocumentIdAndVersion(event.getDocumentId(), event.getVersion())
//                    .orElseThrow();
//            
            Optional<DocumentVersion> optional = versionRepository
                    .findByDocumentIdAndVersion(event.getDocumentId(), event.getVersion());

            if (optional.isPresent()) {
                DocumentVersion v = optional.get();
                System.out.println("Document found: " + v);

                if (isClean) {
                    v.setScanStatus("CLEAN");
                    v.setOcrStatus("Clean");
                } else {
                    v.setScanStatus("INFECTED");
                    v.setOcrStatus("Infected");

                }
                versionRepository.save(v);
        	    System.out.println("inside DocumentScanConsumer documentversion " +v);

                // update status here
            } else {
                System.out.println("❌ Document NOT FOUND in DB for ID: "
                        + event.getDocumentId() + " version: " + event.getVersion());
            }
            

 
        
    	    System.out.println("inside DocumentScanConsumer before saving ");

 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
