package com.dcits.scott.documentprovider;

import com.dcits.scott.apidocument.ApiDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ApiDocumentRepository extends MongoRepository<ApiDocument,String> {
    List<ApiDocument> findBySid(String sid);



}
