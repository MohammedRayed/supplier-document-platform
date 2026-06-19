package com.moharayed.supplier_document_platform.controller;

import com.moharayed.supplier_document_platform.model.Document;
import com.moharayed.supplier_document_platform.service.DocumentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Document> getDocuments() {
        return service.getAllDocuments();
    }

    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        return service.saveDocument(document);
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable Long id) {
        return service.getDocumentById(id);
    }

    @PutMapping("/{id}")
    public Document updateDocument(
            @PathVariable Long id,
            @RequestBody Document document
    ) {
        return service.updateDocument(id, document);
    }

    @DeleteMapping("/{id}")
    public void deleteDocumentById(@PathVariable Long id) {
        service.deleteDocumentById(id);
    }
}