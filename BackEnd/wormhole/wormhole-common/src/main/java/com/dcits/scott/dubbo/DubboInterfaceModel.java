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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * interface接口model类
 *
 */
public class DubboInterfaceModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * serviceKey
     */
    String key;
    
    String interfaceName;

    Set<String> serverIps = new HashSet<>();

    Class<?> interfaceClass;
    
    Set<String> methodNames = new HashSet<>();

    List<DubboMethodModel> methods = new ArrayList<>();
    
    String group;
    
    String version;
    
    long timeout;
    
    String registryBeanName;
    
    boolean check;
    
    int retries;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Set<String> getServerIps() {
        return serverIps;
    }

    public void setServerIps(Set<String> serverIps) {
        this.serverIps = serverIps;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public Set<String> getMethodNames() {
        return methodNames;
    }

    public void setMethodNames(Set<String> methodNames) {
        this.methodNames = methodNames;
    }

    public List<DubboMethodModel> getMethods() {
        return methods;
    }

    public void setMethods(List<DubboMethodModel> methods) {
        this.methods = methods;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getRegistryBeanName() {
        return registryBeanName;
    }

    public void setRegistryBeanName(String registryBeanName) {
        this.registryBeanName = registryBeanName;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }
}
