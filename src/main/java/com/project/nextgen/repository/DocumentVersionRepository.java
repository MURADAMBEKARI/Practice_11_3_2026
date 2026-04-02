package com.project.nextgen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.nextgen.model.DocumentVersion;

public interface DocumentVersionRepository extends MongoRepository<DocumentVersion,String>{

    List<DocumentVersion> findByDocumentId(String documentId);
    
    Optional<DocumentVersion> findByDocumentIdAndVersion(String documentId, int version);
}
