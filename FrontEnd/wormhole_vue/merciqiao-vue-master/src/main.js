// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
// import Vue from 'vue'
// import ElementUI from 'element-ui';
// import axios from 'axios'


import App from './App'
import router from './router'

import 'babel-polyfill'
import util from './common/util'
import Vuex from 'vuex'
import store from './store'
// import CKEditor from '@ckeditor/ckeditor5-vue';
window.moment=require('moment')
import live2d4vue from 'live2d4vue'

import './utils/directive.js'//路径改为自己的，这里不加from ,不use

import 'element-ui/lib/theme-chalk/index.css';
// 全局图标
import './icons'
// import '../static/css/theme-green/index.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import Icon from 'vue-svg-icon/Icon.vue';
// Vue.component('icon', Icon);

Vue.use(ElementUI)
Vue.component('font-awesome-icon', FontAwesomeIcon);
//引入多语言支持
import i18n from './common/i18n'

import VueClipboard from 'vue-clipboard2'
Vue.use(VueClipboard)


Vue.prototype.$ajax=axios
Vue.config.productionTip = false

// Vue.use(ElementUI);
Vue.use(live2d4vue)
Vue.use(util);
Vue.use(Vuex);
// Vue.use(CKEditor);

import permission from './directive'
process.env.MOCK && require('@/apimock')
import htmlToPdf from '@//utils/htmlToPdf'
Vue.use(htmlToPdf)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,//挂载i18n
  router,
  store,
  components: { App },
  template: '<App/>'
})

