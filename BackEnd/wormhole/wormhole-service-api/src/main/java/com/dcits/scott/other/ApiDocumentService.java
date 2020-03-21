package com.dcits.scott.other;

import com.dcits.scott.apidocument.ApiDocument;
import com.dcits.scott.apidocument.Comment;

import java.util.List;
import java.util.Map;

public interface ApiDocumentService {
    public List<ApiDocument> findAll() ;

    public List<Map<String, Object>> findBySid(String sid);

    public void save(ApiDocument apiDocument,String sid);

    public void update(ApiDocument apiDocument);

    public void updateComment(List<Comment> comments,String sid);

    public void deleteById(ApiDocument apiDocument);
}
