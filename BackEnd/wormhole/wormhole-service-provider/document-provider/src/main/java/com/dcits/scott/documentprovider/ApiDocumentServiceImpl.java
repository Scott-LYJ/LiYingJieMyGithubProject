package com.dcits.scott.documentprovider;

import com.dcits.scott.apidocument.ApiDocument;
import com.dcits.scott.apidocument.Comment;
import com.dcits.scott.documentprovider.config.IdWorker;
import com.dcits.scott.other.ApiDocumentService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ApiDocumentServiceImpl implements ApiDocumentService {
    @Autowired
    ApiDocumentRepository apiDocumentRepository;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ApiDocument> findAll() {

        return apiDocumentRepository.findAll();

    }

    @Override
    public List<Map<String, Object>> findBySid(String sid) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<ApiDocument> apiDocumentList = apiDocumentRepository.findBySid(sid);
        apiDocumentList.forEach(apiDocument -> {
            Map<String, Object> map = new HashMap<>();
            map.put("_id",apiDocument.get_id());
            map.put("sid",apiDocument.getSid());
            map.put("apiDetails",apiDocument.getApiDetails());
            map.put("otherFunction",apiDocument.getOtherFunction());
            map.put("requestParams",apiDocument.getRequestParamList());
            map.put("responseParams",apiDocument.getResponseParamList());
            map.put("responseCodes",apiDocument.getResponseCodeList());
            map.put("comments",apiDocument.getComments());
            map.put("upContent",apiDocument.getUpContent());
            map.put("downContent",apiDocument.getDownContent());
            result.add(map);
        });
        return result;

    }

    @Override
    public void save(ApiDocument apiDocument, String sid) {
        apiDocument.set_id(idWorker.nextId() + "");
        System.out.println(sid);
        apiDocument.setSid(sid);
        apiDocumentRepository.save(apiDocument);

    }

    @Override
    public void update(ApiDocument apiDocument) {
        apiDocumentRepository.save(apiDocument);
    }

    @Override
    public void updateComment(List<Comment> comments,String sid) {
            Query query = new Query(Criteria.where("_id").is("1240844956868939776")) ;
        ApiDocument one = mongoTemplate.findOne(query, ApiDocument.class);
        Update update = new Update().set("comments",comments);
        List<ApiDocument> all = mongoTemplate.findAll(ApiDocument.class);
        mongoTemplate.updateFirst(query,update,Comment.class);
    }

    @Override
    public void deleteById(ApiDocument apiDocument) {
        apiDocumentRepository.delete(apiDocument);
    }
}
