<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
            <i class="el-icon-menu"></i>
        </div>
        <div class="logo">{{$t("home.title")}}</div>
        <div class="header-right">
            <div class="header-user-con">

              <!-- 消息中心 -->
              <div class="btn-bell">
                  <change-theme class="theme-container" ></change-theme>
              </div>

                <!-- <marquee onMouseOver="this.start()" style="font-size:18px;padding-bottom:4px;width:100px;" scrollamount="1">{{getlev}}</marquee> -->
                <div style="font-size:18px;padding-bottom:4px;width:100px;">{{getlev}}</div>
                <!-- 全屏显示 -->
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>
                <!-- 消息中心 -->
                <div class="btn-bell">
                    <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom">
                            <i class="el-icon-bell" @click="draw()"></i>
                    </el-tooltip>
                    <span class="btn-bell-badge" v-if="message"></span>
                </div>
                <!-- 用户头像 -->
                <div class="user-avator"><img :src="geticon"></div>
                <!-- 用户名下拉菜单 -->
                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}} <i class="el-icon-caret-bottom"></i>
                    </span>

                    <el-dropdown-menu slot="dropdown">
                        <a  target="_blank">
                          <el-dropdown-item style="color:orange;" @click="toUserEdit"><router-link to="/routedev">个人管理</router-link></el-dropdown-item>
                        </a>
                        <a href="https://github.com/merciqiao/merciqiao-vue" target="_blank">
                            <el-dropdown-item>项目仓库</el-dropdown-item>
                        </a>
                        <a  href="https://github.com/merciqiao" target="_blank">
                          <el-dropdown-item>关于作者</el-dropdown-item>
                        </a>
                        <el-dropdown-item divided  command="changeZh">切换中文</el-dropdown-item>
                        <el-dropdown-item command="changeEn">切换英文</el-dropdown-item>
                        <el-dropdown-item divided  command="loginout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>

            </div>
        </div>
      <el-drawer
        title="信息"
        :visible.sync="drawer"
        direction="rtl">
        :before-close="handleClose"
        <div class="block">
          <el-timeline >
            <el-timeline-item v-for="(item,index) in messageList":icon="item.icon" :color="item.color"  :key="index" :timestamp="item.sendDate" placement="top">
              <el-card>
                <h4>{{item.userName}}&nbsp;&nbsp;&nbsp;&nbsp;
                  <el-tag v-if="item.roleName=='开发者'||item.roleName=='超级管理员'" type=""  size="mini" effect="dark">{{item.roleName}}</el-tag>
                  <el-tag v-else-if="item.roleName=='测试者'" type="success" effect="dark" size="mini">{{item.roleName}}</el-tag>
                  <el-tag v-else-if="item.roleName=='接入者'" type="info" effect="dark" size="mini">{{item.roleName}}</el-tag>
                  <el-tag v-else type="warning" effect="dark" size="mini">{{item.roleName}}</el-tag>
                </h4>
                <p>{{item.message}}</p>
                <div style="float: right">
                <el-button v-if="item.isRead==0" type="success" size="mini" @click="sure(item)">确认收到</el-button>
                <el-button type="danger" size="mini" @click="del(item)">删除</el-button>
                </div>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>


      </el-drawer>




    </div>
</template>
<script>
    import bus from './bus';
    import '../../assets/css/science-blue.css' // the theme changed version element-ui css
    import ChangeTheme from '../../components/theme'

    export default {
      components: {
        ChangeTheme
      },

        data() {
            return {
              messageList:[{
                id:1,
                userName:'dasdas',
                roleName:"dasd",
                message:'这是信息',
                sendDate:'2018/4/12',
                isRead:0
              },
                {
                  id:1,
                  userName:'dasdas',
                  roleName:"dasd",
                  message:'这是信息',
                  sendDate:'2018/4/3',
                  icon:"el-icon-check",
                  color:'#0bbd87',
                  isRead:0
                },
                {
                  id:1,
                  userName:'dasdas',
                  roleName:"dasd",
                  message:'这是信息',
                  icon:"el-icon-warning-outline",
                  color:'#ff250f',
                  sendDate:'2018/4/3',
                  isRead:0
                },
                {
                  id:1,
                  userName:'dasdas',
                  roleName:"dasd",
                  message:'这是信息',
                  sendDate:'2018/4/3',
                  isRead:0
                },
                {
                  id:1,
                  userName:'dasdas',
                  roleName:"dasd",
                  message:'这是信息',
                  sendDate:'2018/4/3',
                  isRead:0
                },

              ],
              drawer: false,
              direction: 'rtl',
                collapse: false,
                fullscreen: false,
                name: 'merciqiao',
                lev:'青铜级',
                message: 0
            }
        },
        computed:{
            username(){
                let username = this.$common.getSessionStorage('username');
                return username ? username : this.name;
            },
            getlev(){

                let levList = this.$common.getSessionStorage('lev',true);
                let lev='';
                if(levList){
                    for(var i=0;i<levList.length;i++){
                        lev+=levList[i].name;
                    }
                }

                return levList ? levList[0].name : this.lev;
            },
          geticon(){
              //let path='E://ScottProject//LiYingJieMyGithubProject//image'
           // require('C://tmp//'+item.url+'.png')
            let userIcon = this.$common.getSessionStorage('icon');
            return userIcon ? userIcon:'static/img/user/hongbao.png'
          }
        },
        methods:{

          sure(item){
            let param={
              id:item.id,
            }
            this.$ajax({
              method: "post",
              url: "/message/sure",
              data: param
            }).then(function(resultData) {
             item.color='#0bbd87'
             item.icon='el-icon-check'
            });
          },
          del(item){
            let param={
                id:item.id,
            }
            this.$ajax({
              method: "post",
              url: "/message/del",
              data: param
            }).then(function(resultData) {

            });
          },
          draw(){
            this.$ajax({
              method: "get",
              url: "/message/select",
            }).then((resultData)=>{
              console.log(resultData)
              let list = resultData.data;
              for (var i=0;i<list.length;i++){
                if(list[i].isRead==0){
                    list[i].color='#ff250f'
                    list[i].icon='el-icon-warning-outline'
                }else{
                  list[i].color='#0bbd87'
                  list[i].icon='el-icon-check'
                }

              };
              this.messageList = list
              this.drawer = true
            });
          },
          handleClose(done) {
            this.$confirm('确认关闭？')
              .then(_ => {
                done();
              })
              .catch(_ => {});
          },
          toUserEdit(){

          },
            // 用户名下拉菜单选择事件
            handleCommand(command) {
                if(command == 'loginout') {
                  this.$ajax({
                    method: "get",
                    url: "/test/logout",
                  }).then((resultData) => {

                    console.log(resultData)
                    this.$common.removeSessionStorage('token');
                    this.$router.push('/login');
                  });
                }
                else if(command == 'changeZh'){
                    this.$i18n.locale = 'zh_CN';
                }
                else if(command == 'changeEn'){
                    this.$i18n.locale = 'en_US';
                }
            },
            // 侧边栏折叠
            collapseChage(){
                this.collapse = !this.collapse;
                bus.$emit('collapse', this.collapse);
            },
            // 全屏事件
            handleFullScreen(){
                let element = document.documentElement;
                if (this.fullscreen) {
                    if (document.exitFullscreen) {
                        document.exitFullscreen();
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen();
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen();
                    } else if (document.msExitFullscreen) {
                        document.msExitFullscreen();
                    }
                } else {
                    if (element.requestFullscreen) {
                        element.requestFullscreen();
                    } else if (element.webkitRequestFullScreen) {
                        element.webkitRequestFullScreen();
                    } else if (element.mozRequestFullScreen) {
                        element.mozRequestFullScreen();
                    } else if (element.msRequestFullscreen) {
                        // IE11
                        element.msRequestFullscreen();
                    }
                }
                this.fullscreen = !this.fullscreen;
            },
          websocketonmessage: function (e) {
            if (e!=null){
              this.message=this.message+1;

              console.log(this.message)
              this.$notify({
                title: '消息',
                message: '你有'+this.message+'条未读消息:('+'编号为'+JSON.parse(e.data).userId+'的用户'+JSON.parse(e.data).message
              });

              // console.log(JSON.parse(e.data).userId);
            }
                     // console.log(e);
          },
          websocketonopen: function () {
            console.log("WebSocket连接成功");
          },
          websocketonerror: function (e) {
            console.log("WebSocket连接发生错误");
          },
          websocketclose: function (e) {
            console.log("connection closed (" + e.code + ")");
          },
        },
        created(){
          console.log("header初始化")

          // if (this.$common.getSessionStorage("message")!=null){
          //   console.log("2222")
          //   if(document.body.clientWidth < 1366){
          //     this.collapseChage();
          //   }
          // }else {
            console.log("3333")
          this.websock = new WebSocket("ws://localhost:8085/websocket/"+this.$common.getSessionStorage("id"));
          this.websock.onopen = this.websocketonopen;
          this.websock.onerror = this.websocketonerror;
          this.websock.onmessage = this.websocketonmessage;
          this.websock.onclose = this.websocketclose;
          console.log("websocket")
          this.$common.setSessionStorage("message",this.websock)
          if(document.body.clientWidth < 1366){
            this.collapseChage();
          }
        }
        // }
    }
</script>
<style scoped>
  .txt {

    line-height:15px;
    overflow: hidden;
    -webkit-line-clamp: 2;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
  }
    .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        color: #fff;
    }
    .collapse-btn{
        float: left;
        padding: 0 21px;
        cursor: pointer;
        line-height: 70px;
    }
    .header .logo{
        float: left;
        width:250px;
        line-height: 70px;
    }
    .header-right{
        float: right;
        padding-right: 50px;
    }
    .header-user-con{
        display: flex;
        height: 70px;
        align-items: center;
    }
    .btn-fullscreen{
        transform: rotate(45deg);
        margin-right: 5px;
        font-size: 24px;
    }
    .btn-bell, .btn-fullscreen{
        position: relative;
        width: 30px;
        height: 30px;
        text-align: center;
        border-radius: 15px;
        cursor: pointer;
    }
    .btn-bell-badge{
        position: absolute;
        right: 0;
        top: -2px;
        width: 8px;
        height: 8px;
        border-radius: 4px;
        background: #f56c6c;
        color: #fff;
    }
    .btn-bell .el-icon-bell{
        color: #fff;
    }
    .user-name{
        margin-left: 10px;
    }
    .user-avator{
        margin-left: 20px;
    }
    .user-avator img{
        display: block;
        width:40px;
        height:40px;
        border-radius: 50%;
    }
    .el-dropdown-link{
        color: #fff;
        cursor: pointer;
    }
    .el-dropdown-menu__item{
        text-align: center;
    }
</style>

<style rel="stylesheet/scss" lang="scss">
  .el-drawer.rtl {

    overflow: scroll
  }
  .theme-container {
    color: #FFFFff;
    font-size: 50px;
    cursor: pointer;
   // background-image: linear-gradient(red, blue)
  }

</style>

