package com.dcits.scott.wormholeserviceconsumer;

import com.dcits.scott.gateway.pojo.GatewayMessageDO;
import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.wormholeserviceconsumer.config.websocket.WebSocket;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private WebSocket webSocket;
    @Reference
    private GatewayApiService gatewayApiService;

    @PostMapping("/send")
    public String send(@PathParam("userId") String userId, @RequestBody GatewayMessageDO gatewayMessageDO) throws InterruptedException {
          gatewayApiService.insertMessage(gatewayMessageDO);
//        gatewayApiService.updateMessage(gatewayMessageDO);
//        gatewayApiService.deleteMessage(gatewayMessageDO);
//        List<GatewayMessageDO> gatewayMessageDOList =  gatewayApiService.selectMessage();

           //webSocket.sendAllObjectMessage(gatewayMessageDO);
       // webSocket.sendAllMessage("dasdasd");
        webSocket.sendAllObjectMessage(gatewayMessageDO);

        return "success";
    }
    @PostMapping("/sure")
    public void updateMessage(@RequestBody GatewayMessageDO gatewayMessageDO){
        gatewayApiService.updateMessage(gatewayMessageDO);
    }
    @PostMapping("/del")
    public void deleteMessage(@RequestBody GatewayMessageDO gatewayMessageDO){
        gatewayApiService.deleteMessage(gatewayMessageDO);
    }
    @GetMapping("/select")
    public List<GatewayMessageDO> selectMessage(){
        List<GatewayMessageDO> gatewayMessageDOS = gatewayApiService.selectMessage();
        return gatewayMessageDOS;
    }
}
