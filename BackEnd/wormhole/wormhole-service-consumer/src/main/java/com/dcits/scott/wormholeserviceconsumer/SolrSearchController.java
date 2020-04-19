package com.dcits.scott.wormholeserviceconsumer;

import com.dcits.scott.other.SolrSearchService;
import com.dcits.scott.util.PageUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("solr")
public class SolrSearchController {

    @Reference
    SolrSearchService solrSearchService;
    @RequestMapping("search")
    public PageUtils search(@RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "isLogin",required = false)String isLogin,
                               @RequestParam(value = "isAuth",required = false)String isAuth,
                               @RequestParam(value = "status",required = false)String status,
                               @RequestParam(value = "serviceVersion",required = false)String serviceVersion,
                               @RequestParam(value = "authVersionStr",required = false)String authVersionStr,
                               @RequestParam(value = "psort",required = false)String psort,
                               @RequestParam(value = "currPage",defaultValue = "1",required = false)Integer currtPage){
        PageUtils pageUtils =null;
        try {
             pageUtils= solrSearchService.search(keyword, isLogin, isAuth,status, serviceVersion,authVersionStr, psort, currtPage, 8);
            System.out.println("controllor ------------>"+pageUtils);

        } catch (Exception e) {
            System.out.println(e.getMessage()+"\t ---------------->solr 服务器无法访问");
            e.printStackTrace();
        }

        return pageUtils;
    }
}
