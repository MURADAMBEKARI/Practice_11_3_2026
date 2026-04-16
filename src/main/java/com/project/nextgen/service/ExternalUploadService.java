package com.project.nextgen.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalUploadService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8085")
            .build();

    public String callUploadApi(MultipartFile file, String entityId, String tenantId) {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        body.add("entityId", entityId);
        body.add("tenantId", tenantId);

        return webClient.post()
                .uri("/documents/upload")  // ✅ FIXED
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}