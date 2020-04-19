package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.testinterface;

import com.dcits.scott.other.dubbo.DubboProxyService;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.support.result.WebApiRspDto;
import com.dcits.scott.util.Request;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dubboProxy")
public class DubboProxyController {

    @Reference
    DubboProxyService dubboProxyService;

    @Reference
    GatewayApiService gatewayApiService;
    private static final Logger logger = LoggerFactory.getLogger(DubboProxyController.class);

    @RequestMapping(value = "/dubbo", method = RequestMethod.GET)
    @ResponseBody
    public WebApiRspDto queryDubbo(@RequestParam(name = "zk") String zk,
                                   @RequestParam(name = "path") String path,
                                   @RequestParam(name = "content") String content,
                                   @RequestParam(name = "dubboIp") String dubboIp){

        Request request = new Request();

        request.setPath(path);

        request.getParams().put("ZK",zk);

        String realIp = "";

        if(dubboIp.contains(":")){

            realIp = dubboIp.split(":")[0];
        }
//
//        if(!realIp.isEmpty() && IpUtil.isIp(realIp)){
//
//            request.getParams().put(Constant.DUBBO_IP,dubboIp);
//        }

        request.setBody(content);

        if(logger.isDebugEnabled()){

        }

       return dubboProxyService.send(request);
    }
}
