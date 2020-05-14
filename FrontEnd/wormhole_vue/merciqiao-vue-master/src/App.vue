<template>
    <div id="app"  @click="clicked">
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
    data (){
      return {
        lTime: new Date().getTime(), // 最后一次点击的时间
        cTime: new Date().getTime(), //当前时间
        // tOut: 60 * 10 * 1000,   //超时时间10min
        tOut: 60 * 10 * 1000,
        t1: ''
      }
    },
    mounted () {
      this.t1 = setInterval(this.tTime, 1000);
    },
    methods:{
      clicked () {
        this.lTime = new Date().getTime()  //当界面被点击更新点击时间
      },
      tTime() {
        this.cTime = new Date().getTime();
        if (this.cTime -this.lTime > this.tOut) {
          //未登录状态
          if(this.$common.getSessionStorage('token') == null){

            this.lTime = new Date().getTime();
          }else{
            this.FedLogOut();
            this.$alert('登录超时，请重新登录', '提示', {
              confirmButtonText: '确定'
            });
            console.log(this.lTime)
          }
        }
      },
      FedLogOut(){
        this.$ajax({
          method: "get",
          url: "/test/logout",
        }).then((resultData) => {
          this.$common.removeSessionStorage('token');
          this.$router.push('/login');
        });
      }
      // // 退出登录点击事件
      // ...mapActions([
      //   'FedLogOut',
      // ]),

    },
  }
</script>
