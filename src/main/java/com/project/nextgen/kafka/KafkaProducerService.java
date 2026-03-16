package com.project.nextgen.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

	@Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public void publishDocumentEvent(Object event){

        kafkaTemplate.send("document-events", event);
    }
}
