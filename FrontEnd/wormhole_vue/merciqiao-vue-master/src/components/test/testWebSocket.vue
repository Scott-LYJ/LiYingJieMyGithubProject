<template>
  <div>
    <h1>测试webSocket</h1>
    <button @click="getWebsocket">点击请求后台数据</button>
    <el-drawer
      title="我是标题"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose">
      <span>我来啦!</span>
    </el-drawer>
  </div>


</template>
<!-- 引入样式 --><link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">

<!-- 引入组件库 --><script src="https://unpkg.com/element-ui/lib/index.js"></script>

<script>
  export default {
    name:"testWebSocket",
    data() {
      return {
        drawer: false,
        direction: 'rtl',
      };
    },
    created() { // 页面创建生命周期函数
     // this.initWebSocket()
    },
    destroyed: function () { // 离开页面生命周期函数
      this.websocketclose();
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      initWebSocket: function () {
        // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
        // this.websock = new WebSocket("ws://localhost:8085/websocket/"+this.getSessionStorage("id"));
        // this.websock.onopen = this.websocketonopen;
        // this.websock.onerror = this.websocketonerror;
        // this.websock =  this.$common.getSessionStorage("message");
        // this.websock.onmessage = this.websocketonmessage;
        // this.websock.onclose = this.websocketclose;

       // this.websocketonmessage()
      },
      websocketonopen: function () {
        console.log("WebSocket连接成功");
      },
      websocketonerror: function (e) {
        console.log("WebSocket连接发生错误");
      },
      websocketonmessage: function (e) {
        console.log(e.data);                // console.log(e);
      },
      websocketclose: function (e) {
        console.log("connection closed (" + e.code + ")");
      },
      getWebsocket:function(){
        // let url = "/message/test?shipId=DPS007"
        // // 这里只是一个基于axios的ajax请求，你可以换成你的请求格式
        // this.$ajax.get(url).then(res=>{
        //   console.log(res)
        // })
        let levList = this.$common.getSessionStorage('lev',true);
        let roleId = levList[0].id;
        let param = {
          userId : this.$common.getSessionStorage("id"),
          roleId:roleId,
          message:'创建了一个接口',
          isRead:0
        }
        this.$ajax({
          method: "post",
          url: "/message/send?userId="+this.$common.getSessionStorage("id"),
          data: param
        }).then(function(resultData) {

        });



        //
      }
    }
  }
</script>
<style  scoped>
</style>
