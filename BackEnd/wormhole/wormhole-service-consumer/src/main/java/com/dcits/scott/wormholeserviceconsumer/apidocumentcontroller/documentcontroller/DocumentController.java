package com.dcits.scott.wormholeserviceconsumer.apidocumentcontroller.documentcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dcits.scott.apidocument.ApiDocument;
import com.dcits.scott.apidocument.Comment;
import com.dcits.scott.apidocument.SubDocument.ApiDetails;
import com.dcits.scott.apidocument.SubDocument.RequestParam;
import com.dcits.scott.apidocument.SubDocument.ResponseCode;
import com.dcits.scott.apidocument.SubDocument.ResponseParam;
import com.dcits.scott.other.ApiDocumentService;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.support.result.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiDocument")
public class DocumentController {
    @Reference
    ApiDocumentService apiDocumentService;
    @Reference
    GatewayApiService gatewayApiService;

    //查询所有
    @HystrixCommand(fallbackMethod = "findAllError")
    @GetMapping("/findAll")
    public Result<List<ApiDocument>> findAll(){
        return new Result("200","查询成功",apiDocumentService.findAll());
    }
    Result<List<ApiDocument>> findAllError(){
        return new Result("500","查询失败",null);
    }
    //查看文档
    @HystrixCommand(fallbackMethod = "findError")
    @GetMapping (value = "/find/{sid}")
    public List<Map<String, Object>> findById(@PathVariable String sid) {
        System.out.println("查询评论");
        List<Map<String,Object>> list = apiDocumentService.findBySid(sid) ;
        return list;
    }
    List<Map<String, Object>> findError(String sid){
        List<Map<String,Object>> maps = new ArrayList<>();
        return maps;
    }
    //保存接口文档
    @PostMapping("/save/{sid}")
    public Result save1(@RequestBody ApiDocument apiDocument, @PathVariable String sid){
        System.out.println(apiDocument);
        List<ApiDocument> all = apiDocumentService.findAll();
        apiDocumentService.save(apiDocument,sid);
        //修改编写文档的时间
        Long apiId = Long.parseLong(sid);
        gatewayApiService.updateDocumentTime(apiId);
        System.out.println(apiDocument);
        return  new Result("","保存成功");
    }
    //提交评论
    @RequestMapping(value = "/{commentId}",method = RequestMethod.POST)
    public Result update(@PathVariable String commentId,@RequestBody ApiDocument apiDocument){
        apiDocument.set_id(commentId);
        apiDocumentService.update(apiDocument);
        return  new Result("","修改成功");
    }
    //修改
    @RequestMapping(value = "/",method = RequestMethod.PUT)
    public Result update1(@RequestBody String apiDocument){
        return  new Result("","修改成功");
    }
    //更新评论
    @RequestMapping(value = "/comment/{sid}",method = RequestMethod.PUT)
    public Result updateComments(@PathVariable String sid,@RequestBody List<Comment> comments){
        apiDocumentService.updateComment(comments,sid);
        return  new Result("","修改成功");
    }
//    @RequestMapping(value = "/{commentId}",method = RequestMethod.DELETE)
//    public Result delete(@PathVariable String commentId){
//        apiDocumentService.deleteById(commentId);
//        return  new Result("","删除成功");
//    }

}
