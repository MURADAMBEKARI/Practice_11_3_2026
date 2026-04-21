package com.project.nextgen.model;

public class DocumentEventDto {

    private String documentId;
    private String tenantId;
    private Integer version;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private String bucketName;
    private String objectName;
    private String scanStatus;
    private String correlationId;
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
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
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	@Override
	public String toString() {
		return "DocumentEventDto [documentId=" + documentId + ", tenantId=" + tenantId + ", version=" + version
				+ ", fileName=" + fileName + ", contentType=" + contentType + ", fileSize=" + fileSize + ", bucketName="
				+ bucketName + ", objectName=" + objectName + ", scanStatus=" + scanStatus + ", correlationId="
				+ correlationId + "]";
	}

    // getters and setters
    
    
}
