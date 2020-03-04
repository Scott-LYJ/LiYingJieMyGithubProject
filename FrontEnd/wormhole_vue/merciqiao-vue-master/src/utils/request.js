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

import axios from 'axios'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 1200*10*1000, // 请求超时时间10min
  headers:{"ajax-type":true}
});

// response 拦截器
service.interceptors.response.use(
  response => {
      console.log(response.headers);
      let session_timeout = response.headers["ajax-header"];
      if (session_timeout) {
          MessageBox.confirm(
              '你已被登出，可以取消继续留在该页面，或者重新登录',
              '确定登出',
              {
                  confirmButtonText: '重新登录',
                  cancelButtonText: '取消',
                  type: 'warning'
              }
          ).then(() => {
              window.location = "/logout";
          }).catch(() => {
              console.log("catch,session过期留在当前页面")
          })
      }else{
          return response
      }
  },
  error => {
      console.log("错误类型:",typeof error);
      console.log('服务错误:' + error);// for debug

      Message({
          message: error.toString(),
          type: 'error',
          duration: 5 * 1000
      });
      return Promise.reject(error)
  }
)

export default service
