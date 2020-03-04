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
 * @author everythingbest
 *
 */
public class DubboModel implements Serializable {
    private static final long serialVersionUID = 1L;

    String zkAddress;

    String serviceName;

    boolean isZk = false;

    String groupId;
    
    String artifactId;
    
    String version;

    long generateTime;

    /**
     * 标识是否加载到classLoader
     * 这个值不能持久化
     */
//    Boolean loadedToClassLoader = false;

    /**
     * 一个dubbo应用包含多个接口定义
     */
    List<DubboInterfaceModel> serviceModelList = new ArrayList<>();

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isZk() {
        return isZk;
    }

    public void setZk(boolean zk) {
        isZk = zk;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(long generateTime) {
        this.generateTime = generateTime;
    }

//    public Boolean getLoadedToClassLoader() {
//        return loadedToClassLoader;
//    }
//
//    public void setLoadedToClassLoader(Boolean loadedToClassLoader) {
//        this.loadedToClassLoader = loadedToClassLoader;
//    }

    public List<DubboInterfaceModel> getServiceModelList() {
        return serviceModelList;
    }

    public void setServiceModelList(List<DubboInterfaceModel> serviceModelList) {
        this.serviceModelList = serviceModelList;
    }
}
