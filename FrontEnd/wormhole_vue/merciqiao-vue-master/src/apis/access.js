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

import request from '@/utils/request'
import Axios from "axios";

export function getRegisterService(params) {

  return Axios.get('/interface/createrInterface/result/serviceNames',{ params});

}

export function getAllMethods(params) {
  return Axios.get('/interface/createrInterface/result/interface',{ params});
}
export function getZkList() {
  return Axios.get('/interface/createrInterface/result/all-zk');
}
export function getArgs(params) {
  return Axios.get('/dubbo-postman/args',{params});
}

export function getAllProviders(params) {
  return Axios.get('/interface/createrInterface/result/interfaceNames',{params});
}

export function getTemplate(params) {
  return Axios.get('/interface/createrInterface/result/methodParams',{params});
}


export function getRemoteHistoryTemplate(params) {
  return Axios.get('/dubbo-postman/result/template/names',{params});

}

export function getRemoteAssignedTemplate(params) {
    return request({
        url:'/dubbo-postman/result/template/get',
        method:'get',
        params
    })
}

export function doRequest(params) {
  return Axios.get('/interface/dubboProxy/dubbo',{params});

}

export function saveHisTemplate(params) {
    return request({
        url:'/dubbo-postman/result/template/save',
        method:'get',
        params
    })
}
