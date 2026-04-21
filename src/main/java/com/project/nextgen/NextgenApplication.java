package com.project.nextgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class NextgenApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextgenApplication.class, args);
	}

}


//Upload → MinIO ✅
//Save → MongoDB ✅
//Send → Kafka ✅
//Consume → Virus service ✅
//Fetch file → MinIO ✅
//Scan → ClamAV ✅
//Update DB ✅
