package com.dcits.scott.other.dubbo;

import com.dcits.scott.support.result.WebApiRspDto;
import com.dcits.scott.util.Request;

public interface DubboProxyService {
    WebApiRspDto send(Request request);

}
