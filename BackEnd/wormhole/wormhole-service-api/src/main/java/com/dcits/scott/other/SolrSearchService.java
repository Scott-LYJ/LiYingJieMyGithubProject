package com.dcits.scott.other;

import com.dcits.scott.util.PageUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;
import java.util.List;

public interface SolrSearchService {

    public PageUtils search(String keyword, String isLogin,String isAuth,String status,String serviceVersion, String priceStr, String psort,
                            Integer currtPage, Integer pageSize) throws IOException, SolrServerException;

    public UpdateResponse addBean(Object object);

    public UpdateResponse deleteById(List<String> ids);
}

