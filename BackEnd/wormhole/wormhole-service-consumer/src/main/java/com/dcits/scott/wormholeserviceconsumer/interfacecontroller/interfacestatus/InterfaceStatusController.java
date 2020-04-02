package com.dcits.scott.wormholeserviceconsumer.interfacecontroller.interfacestatus;

import com.dcits.scott.project.gatewayapi.GatewayApiService;
import com.dcits.scott.support.entity.DocumentTimeCount;
import com.dcits.scott.support.entity.Echarts;
import com.dcits.scott.support.entity.InterfaceTimeCount;
import com.dcits.scott.support.entity.TestAndDocumentCount;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/interfaceStatus")
public class InterfaceStatusController {
    @Reference
    GatewayApiService gatewayApiService;
    @GetMapping("/showEcharts")
    public Echarts showEcharts() {
        Echarts echarts = new Echarts();
        Integer allInterface = gatewayApiService.selectAllInterface();
        Integer allTest = gatewayApiService.selectAllTest();
        Integer allDocument =gatewayApiService.selectAllDocument();


        Map<String, TestAndDocumentCount> map = new HashMap<>();
        List<InterfaceTimeCount> interfaceTimeCountList = gatewayApiService.showInterfaceTimeCount();

        List<DocumentTimeCount> documentTimeCountList = gatewayApiService.showDocumentTimeCount();
        for (InterfaceTimeCount t : interfaceTimeCountList
                ) {
            TestAndDocumentCount testAndDocumentCount = new TestAndDocumentCount();
            testAndDocumentCount.setTestCount(t.getCount());
            testAndDocumentCount.setDocumentCount(0);
            map.put(t.getInterfaceTime().substring(0,10), testAndDocumentCount);
        }
        for (DocumentTimeCount d : documentTimeCountList) {
            if (map.containsKey(d.getDocumentTime())) {
                TestAndDocumentCount testAndDocumentCount1 = map.get(d.getDocumentTime());
                testAndDocumentCount1.setDocumentCount(d.getCount());
                map.put(d.getDocumentTime(), testAndDocumentCount1);
            } else {
                TestAndDocumentCount testAndDocumentCount = new TestAndDocumentCount();
                testAndDocumentCount.setDocumentCount(d.getCount());
                testAndDocumentCount.setTestCount(0);
                map.put(d.getDocumentTime().substring(0,10), testAndDocumentCount);
            }
        }

        Iterator<Map.Entry<String, TestAndDocumentCount>> entries = map.entrySet().iterator();
        List<String> time = new ArrayList<>();
        List<Integer> interfaceCount = new ArrayList<>();
        List<Integer> documentCount = new ArrayList<>();
        while (entries.hasNext()) {

            Map.Entry<String, TestAndDocumentCount> entry = entries.next();

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            time.add(entry.getKey());
            interfaceCount.add(entry.getValue().getTestCount());
            documentCount.add(entry.getValue().getDocumentCount());

        }
        echarts.setTime(time);
        echarts.setDocumentCount(documentCount);
        echarts.setInterfaceCount(interfaceCount);
        echarts.setAllDocumentCount(allDocument);
        echarts.setAllTestCount(allTest);
        echarts.setAllInterfaceCount(allInterface);
        return echarts;
    }
}
