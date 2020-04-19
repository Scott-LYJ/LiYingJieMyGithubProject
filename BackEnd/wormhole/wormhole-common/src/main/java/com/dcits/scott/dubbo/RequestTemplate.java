/*
 * MIT License
 *
 * Copyright (c) 2019 everythingbest
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dcits.scott.dubbo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求模板一个template代表一个唯一的方法访问路径
 */
public class RequestTemplate implements Cloneable,Serializable{
    
    private static final long serialVersionUID = 1L;

    private boolean useDubbo = false;

    private String path;
    
    private String registry;
    
    private String dubboUrl;

    private String serviceName;

    private String interfaceName;
    
    private String version;
    
    private String methodName;
    
    private String group;

    private String methodExample;

    private int retries = 0;

    public String getMethodExample() {
        return methodExample;
    }

    public void setMethodExample(String methodExample) {
        this.methodExample = methodExample;
    }

    private List<RequestParam> matcherParams = new ArrayList<>();

    private boolean hasRunTimeInfo = false;

    /**
     * 通过MatcherParam生成
     */
    private List<String> paramTypes = new ArrayList<>();

    private List<Object> paramValues = new ArrayList<>();


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isUseDubbo() {
        return useDubbo;
    }

    public void setUseDubbo(boolean useDubbo) {
        this.useDubbo = useDubbo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public String getDubboUrl() {
        return dubboUrl;
    }

    public void setDubboUrl(String dubboUrl) {
        this.dubboUrl = dubboUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public List<RequestParam> getMatcherParams() {
        return matcherParams;
    }

    public void setMatcherParams(List<RequestParam> matcherParams) {
        this.matcherParams = matcherParams;
    }

    public boolean isHasRunTimeInfo() {
        return hasRunTimeInfo;
    }

    public void setHasRunTimeInfo(boolean hasRunTimeInfo) {
        this.hasRunTimeInfo = hasRunTimeInfo;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<String> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public List<Object> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<Object> paramValues) {
        this.paramValues = paramValues;
    }

    @Override
    public Object clone(){

        RequestTemplate template = null;

        try {

            template = (RequestTemplate) super.clone();

            //matcherParams 不需要修改
            template.setUseDubbo(false);

            //这里要重置,每次请求设置新的值
            template.setParamTypes(new ArrayList<>());

            template.setParamValues(new ArrayList<>());

        }catch (CloneNotSupportedException e){
        }

        return template;
    }

    private void setParamTypes(ArrayList<Object> objects) {
    }


}
