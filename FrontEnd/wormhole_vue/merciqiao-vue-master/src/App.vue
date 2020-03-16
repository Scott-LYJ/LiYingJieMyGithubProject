<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>
<style>
    @import "../static/css/main.css";
    @import "../static/css/theme-green/color-green.css";
    /* @import '../static/css/theme-green/index.css'; */
</style>
<script>
  export default {
    name:'app',
    created () {
      setTimeout(() => {
        window.L2Dwidget.init({
          pluginRootPath: 'static/live2dw/',
          pluginJsPath: 'lib/',
          pluginModelPath: 'live2d-widget-model-koharu/assets/',
          tagMode: false,
          debug: false,
          model: { jsonPath: '/static/live2dw/live2d-widget-model-      koharu/assets/koharu.model.json' },
          display: { position: 'left', width: 150, height: 300 },
          mobile: { show: true },
          log: false
        })
      }, 15000)
    },
  }
</script>
