package com.project.nextgen.model;

import lombok.Data;

@Data
public class UploadResponse {
 
    private String documentId;
    
    private String downloadUrl;
    
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
    
    
    
}
