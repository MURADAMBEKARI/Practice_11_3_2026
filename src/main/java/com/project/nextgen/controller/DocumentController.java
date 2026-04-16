package com.project.nextgen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.nextgen.model.UploadResponse;
import com.project.nextgen.service.DocumentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

	@Autowired
    private DocumentService documentService;

//	@PostMapping("/upload")
//	public ResponseEntity<UploadResponse> upload(
//	        @RequestParam MultipartFile file,
//	        @RequestParam String entityId
//	) throws Exception {
//
//		UploadResponse docId = documentService.upload(file,entityId);
//        System.out.println("docId : "+docId);
//        return ResponseEntity.ok(docId);
//    }
	
	
//	@PostMapping("/upload")
//	public ResponseEntity<UploadResponse> upload(
//	        @RequestParam MultipartFile file,
//	        @RequestParam String entityId,
//	        @RequestParam String tenantId   
//	) throws Exception {
//
//	    UploadResponse docId = documentService.upload(file, entityId, tenantId);
//
//	    System.out.println("inside DocumentController docId : " + docId);
//	    return ResponseEntity.ok(docId);
//	}
//    
//    @GetMapping("/homepage")
//    public ResponseEntity<String> homepage()
//    {
//    	System.err.println("inside DocumentController homepage ");
//    	return ResponseEntity.ok("Hello from Document Service");
//    }
	
	
	@GetMapping("/upload-page")
	public String showUploadPage() {
	    return "upload";
	}

	@PostMapping("/upload")
	public String upload(
	        @RequestParam MultipartFile file,
	        @RequestParam String entityId,
	        @RequestParam String tenantId,
	        Model model
	) throws Exception {

	    UploadResponse response = documentService.upload(file, entityId, tenantId);
        model.addAttribute("message", "File uploaded successfully");
        model.addAttribute("response", response);

        return "upload";
        
	}
	
 
}
