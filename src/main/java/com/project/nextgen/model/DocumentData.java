package com.project.nextgen.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "documents")
public class DocumentData {

    @Id
    private String id;

    // Unique business document id
    private String documentId;

    // Multi-tenant support
    private String tenantId;

    // Document classification
    private String documentType;
    
    private String category;

    // Business mapping
    private String businessEntityType;
    
    private String businessEntityId;

    // Document status
    private String status;

    // Latest version number
    private int latestVersion;

    // Audit fields
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBusinessEntityType() {
		return businessEntityType;
	}
	public void setBusinessEntityType(String businessEntityType) {
		this.businessEntityType = businessEntityType;
	}
	public String getBusinessEntityId() {
		return businessEntityId;
	}
	public void setBusinessEntityId(String businessEntityId) {
		this.businessEntityId = businessEntityId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(int latestVersion) {
		this.latestVersion = latestVersion;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Override
	public String toString() {
		return "DocumentData [id=" + id + ", documentId=" + documentId + ", tenantId=" + tenantId + ", documentType="
				+ documentType + ", category=" + category + ", businessEntityType=" + businessEntityType
				+ ", businessEntityId=" + businessEntityId + ", status=" + status + ", latestVersion=" + latestVersion
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    
    

}