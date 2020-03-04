package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.interfacelist;

import com.dcits.scott.gateway.pojo.GatewayApiDO;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.support.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interfaceList")
public class InterfaceListController {
    @Reference
    GatewayApiService gatewayApiService;

    @PostMapping("/querySysInterfaceList")
    public Result<List<GatewayApiDO>> querySysInterfaceList(@RequestBody Map<String,Object> map){
        Integer count = gatewayApiService.selectSearchCount(map);
        List<GatewayApiDO> gatewayApiDOS = gatewayApiService.selectSearchPage(map);
        return new Result<>("200","查询成功","",gatewayApiDOS,count);
    }


}
