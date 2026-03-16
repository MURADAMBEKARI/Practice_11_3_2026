package com.project.nextgen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.nextgen.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

	@Autowired
    private DocumentService documentService;

	@PostMapping("/upload")
	public ResponseEntity<String> upload(
	        @RequestParam MultipartFile file,
	        @RequestParam String entityId
	) throws Exception {

        String docId = documentService.upload(file,entityId);

        return ResponseEntity.ok(docId);
    }
    
    @GetMapping("/homepage")
    public ResponseEntity<String> homepage()
    {
    	System.err.println("Hello 1");
    	return ResponseEntity.ok("Hello from Document Service");
    }
 
}
