package com.moharayed.supplier_document_platform.repository;

import com.moharayed.supplier_document_platform.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}