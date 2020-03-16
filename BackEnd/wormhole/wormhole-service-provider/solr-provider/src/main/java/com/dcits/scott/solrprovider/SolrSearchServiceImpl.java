package com.dcits.scott.solrprovider;

import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.other.SolrSearchService;
import com.dcits.scott.util.PageUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@org.apache.dubbo.config.annotation.Service
public class SolrSearchServiceImpl implements SolrSearchService {
    @Autowired
    HttpSolrClient httpSolrClient;

    @Override
    public PageUtils search(String keyword, String isLogin, String isAuth, String status, String serviceVersion, String authVersionStr, String psort, Integer currtPage, Integer pageSize) throws IOException, SolrServerException {
        PageUtils pageUtils = new PageUtils(0, pageSize, currtPage);

        SolrQuery query = new SolrQuery();
        //1.主页关键字判断
        if (StringUtils.isEmpty(keyword)) {
            query.set("q", "*:*");
        } else {
            query.set("q", "api:" + keyword);
        }
        //2.类别筛选
        if (!StringUtils.isEmpty(isLogin)) {
            query.addFilterQuery("api_is_login:" + Integer.parseInt(isLogin));
        }
        //2.类别筛选
        if (!StringUtils.isEmpty(isAuth)) {
            query.addFilterQuery("api_is_auth:" + Integer.parseInt(isAuth));
        }
        //2.类别筛选
        if (!StringUtils.isEmpty(status)) {
            query.addFilterQuery("api_status:" + Integer.parseInt(status));
        }
        //2.类别筛选
        if (!StringUtils.isEmpty(serviceVersion)) {
            query.addFilterQuery("api_serice_version:" +serviceVersion );
        }
        //3.价格过滤
        if (!StringUtils.isEmpty(authVersionStr)) {
            String[] strs = authVersionStr.split("-");
            if (strs.length == 2) {
                if (StringUtils.isEmpty(strs[0])) {
                    query.addFilterQuery("api_auth_version:[0 TO " + strs[1] + "]");
                } else {
                    query.addFilterQuery("api_auth_version:[" + strs[0] + " TO " + strs[1] + "]");
                }
            } else {
                query.addFilterQuery("api_auth_version:[" + strs[0] + " TO *]");
            }
        }

        //4.排序过程 1升序 2降序
        if (!StringUtils.isEmpty(psort)) {
            if ("1".equals(psort)) {
                query.addSort("api_auth_version", SolrQuery.ORDER.asc);
            } else if ("2".equals(psort)) {
                query.addSort("api_auth_version", SolrQuery.ORDER.desc);
            } else if ("3".equals(psort)) {
                query.addSort("cdt", SolrQuery.ORDER.asc);
            } else if ("4".equals(psort)) {
                query.addSort("cdt", SolrQuery.ORDER.desc);
            }
        }

        //5.分页功能
        query.setStart(pageUtils.getOffset());
        query.setRows(pageUtils.getPageSize());

        //设置高亮功能
        query.setHighlight(true);//启动高亮
        query.addHighlightField("group_name");
        query.addHighlightField("api_name");
        query.addHighlightField("api_description");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");


        QueryResponse queryResponse = httpSolrClient.query(query);
        //高亮数据 替换 原文标题的内容
        Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();

        //总记录数
        long count = queryResponse.getResults().getNumFound();


        //得到查询结果
        List<GatewayApiDO> list = queryResponse.getBeans(GatewayApiDO.class);

        for (GatewayApiDO p : list) {

            Map<String, List<String>> map1 = map.get(p.getPid());
            List<String> list1 = map1.get("group_name");
            if (list1 != null) {
                String resultPname = list1.get(0);
                p.setGroupName(resultPname);
            }
            List<String> list2 = map1.get("api_name");
            if (list2 != null) {
                String resultPname = list2.get(0);
                p.setName(resultPname);
            }
            List<String> list3 = map1.get("api_description");
            if (list3 != null) {
                String resultPname = list3.get(0);
                p.setDescription(resultPname);
            }
        }
        pageUtils = new PageUtils((int) count, pageSize, currtPage);
        pageUtils.setList(list);

        return pageUtils;
    }

    @Override
    public UpdateResponse addBean(Object object) {
        UpdateResponse updateResponse;
        try {
            updateResponse = httpSolrClient.addBean(object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UpdateResponse deleteById(List<String> ids) {
        UpdateResponse updateResponse;
        try {
            updateResponse = httpSolrClient.deleteById(ids);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

}
