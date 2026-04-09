package com.project.nextgen.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection = "document_versions_107")
public class DocumentVersion {

    @Id
    private String id;

    // Logical document reference
    private String documentId;
    
    // 🔥 NEW: tenantId (multi-tenant support)
    private String tenantId;
    
    private String scanStatus; // PENDING, CLEAN, INFECTED

    // Version number
    private int version;

    // File metadata
    private String fileName;
    private String originalFileName;

    private String contentType;
    private long fileSize;

    // MinIO storage info
    private String bucketName;
    private String objectName;

    // Security / integrity
    private String checksum;
    private String checksumAlgorithm;

    // Processing status
    private String ocrStatus;

    // Correlation id for tracing
    private String correlationId;

    // Latest version flag
    private boolean isLatest;

    // Audit fields
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getChecksumAlgorithm() {
		return checksumAlgorithm;
	}

	public void setChecksumAlgorithm(String checksumAlgorithm) {
		this.checksumAlgorithm = checksumAlgorithm;
	}

	public String getOcrStatus() {
		return ocrStatus;
	}

	public void setOcrStatus(String ocrStatus) {
		this.ocrStatus = ocrStatus;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public boolean isLatest() {
		return isLatest;
	}

	public void setLatest(boolean isLatest) {
		this.isLatest = isLatest;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getScanStatus() {
		return scanStatus;
	}

	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}

	@Override
	public String toString() {
		return "DocumentVersion [id=" + id + ", documentId=" + documentId + ", tenantId=" + tenantId + ", scanStatus="
				+ scanStatus + ", version=" + version + ", fileName=" + fileName + ", originalFileName="
				+ originalFileName + ", contentType=" + contentType + ", fileSize=" + fileSize + ", bucketName="
				+ bucketName + ", objectName=" + objectName + ", checksum=" + checksum + ", checksumAlgorithm="
				+ checksumAlgorithm + ", ocrStatus=" + ocrStatus + ", correlationId=" + correlationId + ", isLatest="
				+ isLatest + ", createdAt=" + createdAt + "]";
	}

	
    
}
