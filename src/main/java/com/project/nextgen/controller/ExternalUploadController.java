package com.project.nextgen.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.nextgen.service.ExternalUploadService;

@RestController
@RequestMapping("/external")
public class ExternalUploadController {

    private final ExternalUploadService externalUploadService;

    public ExternalUploadController(ExternalUploadService externalUploadService) {
        this.externalUploadService = externalUploadService;
    }

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam String entityId,
            @RequestParam String tenantId
    ) throws Exception {

        return externalUploadService.callUploadApi(file, entityId, tenantId);
    }
}