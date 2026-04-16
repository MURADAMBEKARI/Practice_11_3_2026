package com.project.nextgen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;

import com.project.nextgen.model.DocumentVersion;

@Service
public class DocumentMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private String getCollectionName(String tenantId) {
        return "document_versions_" + tenantId;
    }

    public Optional<DocumentVersion> findByTenantIdAndDocumentIdAndVersion(
            String tenantId, String documentId, int version) {

        String collectionName = getCollectionName(tenantId);

        Query query = new Query();
        query.addCriteria(
            Criteria.where("documentId").is(documentId)
                    .and("version").is(version)
        );

        DocumentVersion result =
                mongoTemplate.findOne(query, DocumentVersion.class, collectionName);

        return Optional.ofNullable(result);
    }
}