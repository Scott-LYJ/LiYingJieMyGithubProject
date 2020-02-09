package com.dcits.scott.userprovider;

import com.dcits.scott.TestInterface;
import org.apache.dubbo.config.annotation.Service;

@Service
public class TestInterfaceImpl implements TestInterface {
    @Override
    public String testInterface(String id) {
        return "success";
    }
}
