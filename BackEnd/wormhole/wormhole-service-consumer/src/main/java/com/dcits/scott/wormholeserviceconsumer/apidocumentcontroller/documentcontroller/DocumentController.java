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
import com.dcits.scott.support.result.Result;
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


    @GetMapping("/findAll")
    public Result<List<ApiDocument>> findAll(){
        return new Result("","查询成功",apiDocumentService.findAll());
    }

    @GetMapping (value = "/find/{sid}")
    public List<Map<String, Object>> findById(@PathVariable String sid) {
        System.out.println("查询评论");
        return apiDocumentService.findBySid(sid) ;
    }


//    @PostMapping("/save/{sid}")
//    public Result save(@RequestBody Map<String,Object> map, @PathVariable String sid){
//
//        ApiDocument apiDocument = new ApiDocument();
//        String ssid= String.valueOf(map.get("sid"));
//        String downContent= String.valueOf(map.get("downContent"));
//        String upContent= String.valueOf(map.get("upContent"));
//        String sapiDetails=String.valueOf(map.get("apiDetails")) ;
//        String requestParamList=String.valueOf(map.get("requestParamList")) ;
//        String responseParamList=String.valueOf(map.get("responseParamList")) ;
//        String responseCodeList=String.valueOf(map.get("responseCodeList")) ;
//        ApiDetails apiDetails = JSON.parseObject(sapiDetails, ApiDetails.class);
//        List<RequestParam> requestParams = JSONObject.parseObject(requestParamList, new TypeReference<ArrayList<RequestParam>>(){});
//        List<ResponseParam> responseParams = JSONObject.parseObject(responseParamList, new TypeReference<ArrayList<ResponseParam>>(){});
//        List<ResponseCode> responseCodes = JSONObject.parseObject(responseCodeList,  new TypeReference<ArrayList<ResponseCode>>(){});
//        apiDocument.setSid(ssid);
//        apiDocument.setApiDetails(apiDetails);
//        apiDocument.setUpContent(upContent);
//        apiDocument.setDownContent(downContent);
//        apiDocument.setRequestParamList(requestParams);
//        apiDocument.setResponseParamList(responseParams);
//        apiDocument.setResponseCodeList(responseCodes);
//        System.out.println(apiDocument);
//        apiDocumentService.save(apiDocument,sid);
//        System.out.println(apiDocument);
//        return  new Result("","保存成功");
//    }
    @PostMapping("/save/{sid}")
    public Result save1(@RequestBody ApiDocument apiDocument, @PathVariable String sid){
        System.out.println(apiDocument);
        apiDocumentService.save(apiDocument,sid);
        System.out.println(apiDocument);
        return  new Result("","保存成功");
    }    @PostMapping("/save2/{sid}")

    @RequestMapping(value = "/{commentId}",method = RequestMethod.POST)
    public Result update(@PathVariable String commentId,@RequestBody ApiDocument apiDocument){
        apiDocument.set_id(commentId);
        apiDocumentService.update(apiDocument);
        return  new Result("","修改成功");
    }
    //
    @RequestMapping(value = "/",method = RequestMethod.PUT)
    public Result update1(@RequestBody String apiDocument){
//        apiDocument.set_id(commentId);
//        apiDocumentService.update(apiDocument);
        return  new Result("","修改成功");
    }
    //

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
