<template xmlns:overflow="http://www.w3.org/1999/xhtml">
<div>
  <div id="pdfDom" >
  <el-container>
    <el-header>
      <div style="margin-top: 20px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item><i class="el-icon-tickets"></i>接口信息</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">基本信息</el-aside>
      <el-container>
        <el-main>
          <div>
            <el-form label-position="left"  class="demo-table-expand" label-width="100px" >
              <el-form-item label="API编号:">
                <span>{{ apiDetails.id}}</span>
              </el-form-item>
              <el-form-item label="文档名称:" >
                <span>{{ apiDetails.name}}</span>
              </el-form-item>
              <el-form-item label="文档创建人:" >
                <span>{{ apiDetails.createBy}}</span>
              </el-form-item>
              <el-form-item label="ZK地址:" >
                <span>{{ apiDetails.zookeeper}}</span>
              </el-form-item>
            </el-form>
            <el-form label-position="left"  class="demo-table-expand"  label-width="100px">
                <el-form-item label="服务名称:" >
                  <span>{{ apiDetails.serviceName}}</span>
                </el-form-item>
                <el-form-item label="URL:" >
                  <span>{{ apiDetails.url}}</span>
                </el-form-item>
                <el-form-item label="数据格式:">
                  <span>{{ apiDetails.dataStyle}}</span>
                </el-form-item>
                <el-form-item label="文档描述:" >
                  <span>{{ apiDetails.description}}</span>
                </el-form-item>
            </el-form>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
  <el-container>
    <el-header>
      <div style="margin-top: 20px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item><i class="el-icon-tickets"></i>请求参数</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">参数信息</el-aside>
      <el-container>
        <el-main>
          <el-table
          :data="requestParamsForm.tableData"
          border
          style="width: 100%"
          max-height="250">
          <el-table-column
            prop="param"
            label="参数"
            width="180">
          </el-table-column>
          <el-table-column
            prop="name"
            label="参数名称"
            width="180">
          </el-table-column>
          <el-table-column
            prop="typeName"
            label="参数类型">
          </el-table-column>
            <el-table-column
              prop="required"
              label="是否必填">
            </el-table-column>
          <el-table-column
            prop="maxLength"
            label="最大长度">
          </el-table-column>
          <el-table-column
            prop="example"
            label="示例">
          </el-table-column>
          <el-table-column
            prop="description"
            label="参数描述">
          </el-table-column>
        </el-table>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
  <el-container>
    <el-header>
      <div style="margin-top: 20px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item><i class="el-icon-tickets"></i>响应参数</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">参数信息</el-aside>
      <el-container>
        <el-main>
          <el-table
            :data="responseParamsForm.tableData"
            border
            style="width: 100%"
            max-height="250">
            <el-table-column
              prop="param"
              label="参数"
              width="180">
            </el-table-column>
            <el-table-column
              prop="name"
              label="参数名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="typeName"
              label="参数类型">
            </el-table-column>
            <el-table-column
              prop="required"
              label="是否必填">
            </el-table-column>
            <el-table-column
              prop="maxLength"
              label="最大长度">
            </el-table-column>
            <el-table-column
              prop="example"
              label="示例">
            </el-table-column>
            <el-table-column
              prop="description"
              label="参数描述">
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
  <el-container>
    <el-header>
      <div style="margin-top: 20px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item><i class="el-icon-tickets"></i>响应码信息</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">

        响应码

      </el-aside>
      <el-container>
        <el-main>
          <el-table
            :data="responseCodesForm.tableData"
            border
            style="width: 100%"
            max-height="250">
            <el-table-column
              prop="code"
              label="响应码"
              width="180">
            </el-table-column>
            <el-table-column
              prop="description"
              label="响应描述"
              width="180">
            </el-table-column>
            <el-table-column
              prop="reason"
              label="原因">
            </el-table-column>
            <el-table-column
              prop="solution"
              label="解决方案">
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
  <el-container>
    <el-header>
      <div style="margin-top: 20px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item><i class="el-icon-tickets"></i>示例</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">上送/下送</el-aside>
      <el-container>
        <el-main style="height: 350px;">
          <div style="position: absolute;height: 350px">
          <div style="width: 350px;height:300px;position: absolute;left: 5px;">

            <div class="blog-content"  v-html="upContent" style="overflow:scroll;;background-color: #fbfdff;width: 320px;height:300px;">

            </div>

          </div>
          <div style=" position:absolute;width: 350px;height:300px;left:500px;">
            <div class="blog-content" overflow:auto v-html="downContent" style="overflow:scroll;;background-color: #fbfdff;width: 320px;height:300px;">
            </div>

          </div>

            <div style=" position:absolute;width: 20px;height:20px;left:360px;bottom: 70px">
              <el-button v-on:click="getPdf()">导出PDF</el-button>

            </div>

          </div>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
  </div>
<!--
-->
  <div>
    <div v-clickoutside="hideReplyBtn" @click="inputFocus" class="my-reply">
      <!--<el-avatar class="header-img" :size="40" :src="myHeader"></el-avatar>-->
      <div class="header-img"><img :src="myHeader"></div>

      <div class="reply-info" >
        <div
          tabindex="0"
          contenteditable="true"
          id="replyInput"
          spellcheck="false"
          placeholder="输入评论..."
          class="reply-input"
          @focus="showReplyBtn"
          @input="onDivInput($event)"
        >
        </div>
      </div>
      <div class="reply-btn-box" v-show="btnShow">
        <el-button class="reply-btn" size="medium" @click="sendComment" type="primary">发表评论</el-button>
      </div>
    </div>
    <div v-for="(item,i) in comments" :key="i" class="author-title reply-father">
      <!--<el-avatar class="header-img" :size="40" :src="item.headImg"></el-avatar>-->
      <div class="header-img"><img :src="item.headImg"></div>

      <div class="author-info">
        <span class="author-name">{{item.name}}</span>
        <!--//{{item.time}}-->
        <span class="author-time" v-text="dateStr(item.time)"></span>
      </div>
      <div class="icon-btn">
        <span @click="showReplyInput(i,item.name,item.id,item.commentNum)" ><i  class="iconfont el-icon-s-comment"></i>{{item.commentNum}}</span>
        <i class="iconfont el-icon-caret-top" @click="likeUp(item,item.like)"></i>{{item.like}}
        <i class="iconfont el-icon-caret-bottom" @click="likeDown(item,item.unLike)" ></i>{{item.unLike}}
      </div>
      <div class="talk-box">
        <p>
          <span class="reply">{{item.comment}}</span>
        </p>
      </div>
      <div class="reply-box">
        <div v-for="(reply,index) in item.reply" :key="index" class="author-title">
          <!--<el-avatar class="header-img" :size="40" :src=""></el-avatar>-->
          <div class="header-img"><img :src="reply.fromHeadImg"></div>
          <div class="author-info">
            <span class="author-name">{{reply.from}}</span>
            <span class="author-time"  v-text="dateStr(reply.time)"></span>
          </div>
          <div class="icon-btn">
            <span @click="showReplyInput(i,reply.from,reply.fromId,item.commentNum)"><i class="iconfont el-icon-s-comment"></i>{{reply.commentNum}}</span>
            <i class="iconfont el-icon-caret-top"  @click="likeUp(reply,reply.like)"></i>{{reply.like}}
            <i class="iconfont el-icon-caret-bottom" @click="likeDown(reply,reply.unLike)"></i>{{reply.unLike}}
          </div>
          <div class="talk-box">
            <p>
              <span>回复 {{reply.to}}:</span>
              <span class="reply">{{reply.comment}}</span>
            </p>
          </div>
          <div class="reply-box">

          </div>
        </div>
      </div>
      <div  v-show="_inputShow(i)" class="my-reply my-comment-reply">
        <!--<el-avatar class="header-img" :size="40" :src="myHeader"></el-avatar>-->
        <!-- 用户头像 -->
        <div class="header-img"><img :src="myHeader"></div>
        <div class="reply-info" >
          <div tabindex="0" contenteditable="true" spellcheck="false" placeholder="输入评论..."   @input="onDivInput($event)"  class="reply-input reply-comment-input"></div>
        </div>



        <div class=" reply-btn-box">
          <el-button class="reply-btn" size="medium" @click="cancleCommentReply(i,index)" type="primary">取消</el-button>
          <el-button class="reply-btn"  size="medium" @click="sendCommentReply(i,index)" type="primary">发表评论</el-button>

        </div>
      </div>
    </div>
  </div>


  <!--->
</div>
</template>

<script>
  import html2canvas from 'html2canvas';
  import JsPDF from 'jspdf'
  const clickoutside = {
    // 初始化指令
    bind(el, binding, vnode) {
      function documentHandler(e) {
        // 这里判断点击的元素是否是本身，是本身，则返回
        if (el.contains(e.target)) {
          return false;
        }
        // 判断指令中是否绑定了函数
        if (binding.expression) {
          // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
          binding.value(e);
        }
      }
      // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
      el.vueClickOutside = documentHandler;
      document.addEventListener('click', documentHandler);
    },
    update() {},
    unbind(el, binding) {
      // 解除事件监听
      document.removeEventListener('click', el.vueClickOutside);
      delete el.vueClickOutside;
    },
  };
    export default {
        name: "apiDocumentDetail",
        data(){
          return{
            htmlTitle: '页面导出PDF文件名',
            //
            btnShow: false,
            index:'0',
            replyComment:'',
            myName:this.$common.getSessionStorage("username"),
            myHeader:this.$common.getSessionStorage("avatar"),
            myId:this.$common.setSessionStorage("id"),
            to:'',
            toId:-1,


            //
            _id : "",
            sid:"",
            //
            otherFunction:{
              discuss:null,
              thumbsUp:null,
              thumbsDown:null,
            },
            //
            apiDetails:{
              id:'111',
              name:'1111',
              createBy:'1111',
              zookeeper:'111',
              cdt:'111',
              serviceName:'111',
              url:'111',
              dataStyle:'111',
              description:'111',
            },
            //
            requestParamsForm:{
              tableData:[],
            },
            //
            responseParamsForm:{
              tableData:[],
            },
            responseCodesForm:{
              tableData:[],
            },
            //
            upContent: null,
            upEditorOption: {},
            //
            downContent: null,
            downEditorOption: {},

            comments:[
              {
                name:'Lana Del Rey',
                id:19870621,
                headImg:'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.indexpg',
                comment:'我发布一张新专辑Norman Fucking Rockwell,大家快来听啊',
                time:'2019年9月16日 18:43',
                commentNum:2,
                like:15,
                inputShow:false,
                reply:[
                  {
                    from:'Taylor Swift',
                    fromId:19891221,
                    fromHeadImg:'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.indexpg',
                    to:'Lana Del Rey',
                    toId:19870621,
                    comment:'我很喜欢你的新专辑！！',
                    time:'2019年9月16日 18:43',
                    commentNum:1,
                    like:15,
                    inputShow:false
                  },
                ]
              },
              {
                name:'Taylor Swift',
                id:19891221,
                headImg:'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.indexpg',
                comment:'我发行了我的新专辑Lover',
                time:'2019年9月16日 18:43',
                commentNum:1,
                like:5,
                inputShow:false,
                reply:[
                  {
                    from:'Lana Del Rey',
                    fromId:19870621,
                    fromHeadImg:'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.indexpg',
                    to:'Taylor Swift',
                    toId:19891221,
                    comment:'新专辑和speak now 一样棒！',
                    time:'2019年9月16日 18:43',
                    commentNum:25,
                    like:5,
                    inputShow:false

                  }
                ]
              },
              {
                name:'Norman Fucking Rockwell',
                id:20190830,
                headImg:'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.indexpg',
                comment:'Plz buy Norman Fucking Rockwell on everywhere',
                time:'2019年9月16日 18:43',
                commentNum:0,
                like:5,
                inputShow:false,
                reply:[]
              },
            ]

          }
        },
      directives: {clickoutside},
      mounted(){
          let sid=this.$route.query.sid;
          console.log(sid)
        this.$ajax({
          method: "get",
          url: "/test/apiDocument/find/"+sid,
        }).then((resultData)=>{
          console.log(resultData)
          let data =  resultData.data[0];
          if (resultData.data.length==0){
            this.$confirm('此接口未编写接口文档', '提示', {
              confirmButtonText: '返回',
              type: 'warning'
            }).then(() => {
              this.$router.back(-1)
            }).catch(() => {
              this.$router.back(-1)
            });
            return
          }else{
          this.sid=data.sid;
          this._id=data._id;
          this.otherFunction=data.otherFunction
          console.log(this.otherFunction)
          this.apiDetails=data.apiDetails;
          this.upContent=data.upContent;
          this.downContent=data.downContent;
          for (var i=0;i<data.requestParams.length;i++){
            data.requestParams[i].required = data.requestParams[0].required==true?'true':'false';
          }
          for (var i=0;i<data.responseParams.length;i++){
            data.responseParams[i].required = data.responseParams[0].required==true?'true':'false';
          }
          this.requestParamsForm.tableData=data.requestParams
          this.responseParamsForm.tableData=data.responseParams;
          this.responseCodesForm.tableData=data.responseCodes;
          console.log(resultData.data)
          this.comments = data.comments
          console.log(this.comments)
          }
        });
      },
      methods:{
        likeUp(item,like) {
          if (item.liked === false) {
            item.like = like - 1;
            item.liked = true
            return;
          } else {
            console.log(item)
            console.log(item + "  " + like)
            item.like = like + 1;
            let param = {
              sid: this.sid,
              upContent: this.upContent,
              downContent: this.downContent,
              apiDetails: this.apiDetails,
              otherFunction: this.otherFunction,
              requestParamList: this.requestParamsForm.tableData,
              responseParamList: this.responseParamsForm.tableData,
              responseCodeList: this.responseCodesForm.tableData,
              comments: this.comments
            }
            console.log(param)
            //
            this.$ajax({
              method: "post",
              url: "/test/apiDocument/" + this._id,
              data: param
            }).then(res => {

              this.$message({
                message: "成功",
                type: "success"
              });
              item.liked=false
              // this.$emit('update', this.update)
            });

          }
        },
        likeDown(item,unlike) {
          if (item.unLiked === false) {
            item.unLike = unlike - 1;
            item.unLiked = true
            return;
          } else {
            item.unLike = unlike + 1;
            let param = {
              sid: this.sid,
              upContent: this.upContent,
              downContent: this.downContent,
              apiDetails: this.apiDetails,
              otherFunction: this.otherFunction,
              requestParamList: this.requestParamsForm.tableData,
              responseParamList: this.responseParamsForm.tableData,
              responseCodeList: this.responseCodesForm.tableData,
              comments: this.comments
            }
            console.log(param)
            //
            this.$ajax({
              method: "post",
              url: "/test/apiDocument/" + this._id,
              data: param
            }).then(res => {

              this.$message({
                message: "成功",
                type: "success"
              });
              // this.$emit('update', this.update)
              item.unLiked=false
            });

          }
        },
        cancleCommentReply(i,index){
          this.comments[i].inputShow = false
        },
        inputFocus(){
          var replyInput = document.getElementById('replyInput');
          replyInput.style.padding= "8px 8px"
          replyInput.style.border ="2px solid blue"
          replyInput.focus()
        },
        showReplyBtn(){
          this.btnShow = true
        },
        hideReplyBtn(){
          this.btnShow = false
          replyInput.style.padding= "10px"
          replyInput.style.border ="none"
        },
        showReplyInput(i,name,id,num){
          this.comments[this.index].inputShow = false
          this.index =i
          this.comments[i].inputShow = true
          this.to = name
          this.toId = id
          console.log(name,id)

        },
        _inputShow(i){
          return this.comments[i].inputShow
        },
        sendComment(){
          var _this=this
          if(!this.replyComment){
            this.$message({
              showClose: true,
              type:'warning',
              message:'评论不能为空'
            })
          }else{
            console.log(this.commentNum=this.commentNum+1)
            let a ={}
            let input =  document.getElementById('replyInput')
            let timeNow = new Date().getTime();
            let time= this.dateStr(timeNow);
            a.name= this.myName
            a.comment =this.replyComment
            a.headImg = this.myHeader
            a.time = timeNow
            a.id=timeNow
            a.commentNum = 0
            a.like = 0
            a.unLike = 0
            a.reply=[]
            this.comments.push(a)
            this.replyComment = ''
            input.innerHTML = '';
            //
            let param = {
              sid:this.sid,
              upContent : this.upContent,
              downContent:this.downContent,
              apiDetails:this.apiDetails,
              otherFunction:this.otherFunction,
              requestParamList:this.requestParamsForm.tableData,
              responseParamList : this.responseParamsForm.tableData,
              responseCodeList:this.responseCodesForm.tableData,
              comments:this.comments
            }
            console.log(param)
              //
            this.$ajax({
              method: "post",
              url: "/test/apiDocument/"+this._id,
              data: param
            }).then(res => {

              this.$message({
                message: "成功",
                type: "success"
              });
              // this.$emit('update', this.update)
            });
            //提交后台
            // this.$ajax({
            //   method:"put",
            //   url:"/test/apiDocument/"+this._id,
            //   data:param
            // }).then(result=>{
            //   console.log("成功")
            // }).error(error=>{
            //   console.log("失败")
            // });
            //

          }
        },
        sendCommentReply(i,index){
          console.log("index",index)
          console.log("i",i)
          if(!this.replyComment){
            this.$message({
              showClose: true,
              type:'warning',
              message:'评论不能为空'
            })
          }else{

            let a ={}
            let timeNow = new Date().getTime();
            let time= this.dateStr(timeNow);
            a.from= this.myName
            a.fromId=timeNow
            a.to = this.to
            console.log("a.to",a.to)
            a.toId=this.toId
            console.log("a.toId",a.toId)
            a.fromHeadImg = this.myHeader
            a.comment =this.replyComment
            a.time = timeNow
            a.commentNum = 0
            a.like = 0
            a.unLike=0
            this.comments[i].reply.push(a)
            console.log(a)
            console.log("index",this.index+"  "+i)
            console.log(this.comments[this.index].reply)
            for (var i=0;i<this.comments[this.index].reply.length;i++){
              if(this.comments[this.index].reply[i].fromId==a.toId){
                this.comments[this.index].reply[i].commentNum=this.comments[this.index].reply[i].commentNum+1
              }
            }

            this.replyComment = ''
            document.getElementsByClassName("reply-comment-input")[i].innerHTML = ""
            //
            let param = {
              sid:this.sid,
              upContent : this.upContent,
              downContent:this.downContent,
              apiDetails:this.apiDetails,
              otherFunction:this.otherFunction,
              requestParamList:this.requestParamsForm.tableData,
              responseParamList : this.responseParamsForm.tableData,
              responseCodeList:this.responseCodesForm.tableData,
              comments:this.comments
            }
            console.log(param)
            //
            this.$ajax({
              method: "post",
              url: "/test/apiDocument/"+this._id,
              data: param
            }).then(res => {

              this.$message({
                message: "成功",
                type: "success"
              });
              // this.$emit('update', this.update)
            });

            //
          }
        },
        onDivInput: function(e) {
          this.replyComment = e.target.innerHTML;
        },
        dateStr(date){
          //获取indexs 时间戳
          var time=new Date().getTime();
          //去掉 indexs 时间戳后三位，与php 时间戳保持一致
          time=parseInt((time-date)/1000);
          //存储转换值
          var s;
          if(time<60*10){//十分钟内
            return '刚刚';
          }else if((time<60*60)&&(time>=60*10)){
            //超过十分钟少于1小时
            s = Math.floor(time/60);
            return  s+"分钟前";
          }else if((time<60*60*24)&&(time>=60*60)){
            //超过1小时少于24小时
            s = Math.floor(time/60/60);
            return  s+"小时前";
          }else if((time<60*60*24*30)&&(time>=60*60*24)){
            //超过1天少于30天内
            s = Math.floor(time/60/60/24);
            return s+"天前";
          }else{
            //超过30天ddd
            var date= new Date(parseInt(date));
            return date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
          }
        },
        //
      },

    }

</script>
<style lang="stylus" scoped>
  .header-img{
    margin-left: 20px;
  }
  .header-img img{
    display: block;
    width:40px;
    height:40px;
    border-radius: 50%;
  }
  .my-reply
    padding 10px
    background-color #fafbfc
    .header-img
      display inline-block
      vertical-align top
    .reply-info
      display inline-block
      margin-left 5px
      width 90%
      @media screen and (max-width:1200px) {
        width 80%
      }
      .reply-input
        min-height 20px
        line-height 22px
        padding 10px 10px
        color #ccc
        background-color #fff
        border-radius 5px
        &:empty:before
          content attr(placeholder)
        &:focus:before
          content none
        &:focus
          padding 8px 8px
          border 2px solid blue
          box-shadow none
          outline none
    .reply-btn-box
      height 25px
      margin 10px 0
      .reply-btn
        position relative
        float right
        margin-right 15px
  .my-comment-reply
    margin-left 50px
    .reply-input
      width flex
  .author-title:not(:last-child)
    border-bottom: 1px solid rgba(178,186,194,.3)
  .author-title
    padding 10px
    .header-img
      display inline-block
      vertical-align top
    .author-info
      display inline-block
      margin-left 5px
      width 60%
      height 40px
      line-height 20px
      >span
        display block
        cursor pointer
        overflow hidden
        white-space nowrap
        text-overflow ellipsis
      .author-name
        color #000
        font-size 18px
        font-weight bold
      .author-time
        font-size 14px
    .icon-btn
      width 30%
      padding 0 !important
      float right
      @media screen and (max-width : 1200px){
        width 20%
        padding 7px
      }
      >span
        cursor pointer
      .iconfont
        margin 0 5px
    .talk-box
      margin 0 50px
      >p
        margin 0
      .reply
        font-size 16px
        color #000
    .reply-box
      margin 10px 0 0 50px
      background-color #efefef
</style>


<style>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #e9eef3;
    color: #333;
    /* text-align: center; */
    line-height: 20px !important;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>
