package com.moharayed.supplier_document_platform.service;

import com.moharayed.supplier_document_platform.model.Document;
import com.moharayed.supplier_document_platform.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class DocumentService {

    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public List<Document> getAllDocuments() {
        return repository.findAll();
    }

    public Document saveDocument(Document document) {
        document.setStatus("PENDING");
        document.setUploadDate(LocalDateTime.now());
        return repository.save(document);
    }

    public Document getDocumentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public Document updateDocument(Long id, Document updateDocument) {
        Document document = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setFileName(updateDocument.getFileName());
        document.setStatus(updateDocument.getStatus());
        document.setSupplierName(updateDocument.getSupplierName());

        return repository.save(document);
    }

    public void deleteDocumentById(Long id) {
        Document document = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        repository.delete(document);
    }
}