package com.project.nextgen.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.project.nextgen.model.DocumentEventDto;

@Component
public class KafkaConsumerService {

    @KafkaListener(
            topics = "document-events",
            groupId = "nextgen-group"
    )
    public void consume(DocumentEventDto event) {

        System.out.println("===== EVENT RECEIVED =====");
        System.out.println("Document ID: " + event.getDocumentId());
        System.out.println("Tenant ID: " + event.getTenantId());
        System.out.println("File Name: " + event.getFileName());
        System.out.println("Version: " + event.getVersion());
     //   System.out.println("Created At: " + event.getCreatedAt());
    }
}
