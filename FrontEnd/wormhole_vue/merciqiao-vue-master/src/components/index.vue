<template>
  <div>
    <div>
  <index-header  @toFatherData="sendSonData"></index-header>
    </div>
    <br/>
  <el-row>
    <!--就改这里一行-->
    <el-col :span="4"  v-for="(project,index) in this.list" :key="index" :offset="1" style="margin-bottom:30px;" >
      <el-card :body-style="{ padding: '0px', height:'300px'}" shadow="hover" style="width: 230px;height: 300px;">
          <div slot="header" class="clearfix">
            <span v-html="project.name"></span>
           <div style="float: right;" >
             <div style="margin-top: -1px" v-html="project.authVersion">

             </div>
           </div>
          </div>
        <div>
          <div style="padding: 6px;height: 310px;margin: auto">
            <div>
              <el-form ref="api" :model="project" label-width="80px"size="mini" label-position="left">
                <el-form-item label="创建人:" >
                  <el-tag
                    type=""
                    effect="dark"
                    v-html="project.createBy"
                  >

                  </el-tag>
                </el-form-item >
                <el-form-item label="分组:" style="margin-top:-10px">
                  <el-tag
                    type="info"
                    effect="dark"
                    v-html="project.groupName"
                  >
                  </el-tag>
                </el-form-item>

              </el-form>
              <el-form ref="api" :model="project" label-width="80px"size="mini" label-position="top">
                <el-form-item label="描述:" style="margin-top:-10px">
                  <div style="margin-top:-10px;height:30px" class="txt">
                    <el-tooltip class="item" effect="dark" :content="project.description" placement="top-start">
                      <font size="1px" v-html="project.description">
                      </font>
                    </el-tooltip>

                  </div>
                </el-form-item>
                <el-form-item  style="margin-top:-10px">
                  <el-tag v-if="project.status==1">启用</el-tag>
                  <el-tag type="danger" v-if="project.status==0">禁用</el-tag>
                  &nbsp;
                  <i class="el-icon-view"></i><span>22222</span>
                  &nbsp;
                  <i class="el-icon-time"></i><span >{{project.cdt}}</span>
                </el-form-item>
                <el-form-item  style="margin-top:-10px">
                  <el-button type="info" round style="float: left;width: 100px" @click="toDetail(project.pid)">详情</el-button>
                  <el-button type="success" round style="float: right;width: 100px" @click="toTest(project)">测试</el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
    <live2d />

    <!-- 详情界面-->
    <el-dialog title="接口详情" :visible.sync="detailFormVisible" :close-on-click-modal="false">
      <el-tabs v-model="activeName" @tab-click="handleClick($event,apiDetail.id)">
        <el-tab-pane label="接口基本信息" name="first">
          <el-form ref="apiDetail" :model="apiDetail" label-width="80px" >
            <el-form-item label="API名称">
              <el-input v-model="apiDetail.name" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="API编号">
              <el-input v-model="apiDetail.id" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="版本">
              <el-input-number v-model="apiDetail.version" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
            </el-form-item>
            <el-form-item label="分组名称">
              <el-input v-model="apiDetail.groupName" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="接口描述">
              <el-input type="textarea" v-model="apiDetail.description" :disabled="true"></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="dubbo相关信息" name="second">
          <el-form ref="apiDetail" :model="apiDetail" label-width="80px">
            <el-form-item label="ZK地址">
              <el-input v-model="apiDetail.zookeeper" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="服务名称">
              <el-input v-model="apiDetail.serviceName" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="接口名称">
              <el-input v-model="apiDetail.interfaceName":disabled="true" ></el-input>
            </el-form-item>
            <el-form-item label="方法名称">
              <el-input v-model="apiDetail.serviceMethod" :disabled="true"></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="请求参数信息" name="third">
          <el-table :data="requestParamsForm" size="mini"  highlight-current-row border  max-height="400"   class="el-tb-edit mgt20" ref="multipleTable" tooltip-effect="dark" >
            <el-table-column type="index" :index="indexMethod" >
            </el-table-column>

            <el-table-column  label="参数名称" width="200">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" auto-complete="off" :disabled="true"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="参数类型" width="200">
              <template slot-scope="scope">
                <el-select v-model="scope.row.typeName" placeholder="请选择":disabled="true">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>

              </template>
            </el-table-column>
            <el-table-column label="请求示例" width="320">
              <template slot-scope="scope">
                <el-input type="textarea" v-model="scope.row.example":disabled="true"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="参数描述" width="320">
              <template slot-scope="scope">
                <el-input type="textarea" v-model="scope.row.description":disabled="true"></el-input>
              </template>
            </el-table-column>

          </el-table>

        </el-tab-pane>
        <el-tab-pane label="接口文档" name="fourth">

        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button>{{$t('SysUser.cancle')}}</el-button>
      </div>
    </el-dialog>
    <!--详情界面结束-->
    </div>
</template>

<style>
  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 200px;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .txt {

    line-height:15px;
    overflow: hidden;
    -webkit-line-clamp: 2;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
</style>


<script>
import apis from "../apis/apis";
import apiCard from "./card/apiCard.vue";
import indexHeader from "./indexHeader.vue";

import live2d from 'vue-live2d'
import 'vue-live2d/dist/vue-live2d.css'

export default {


  components:{
    apiCard,
    indexHeader,
    live2d,
  },
  data() {
    return {
      options:[],
      activeName: 'first',
      detailFormVisible:false,
      apiDetail:{},
      list:[],
      bool:true,
      api:{
        authVersion:'',
        cdt:'',
        createBy:'',
        description:'',
        groupName:'',
        name:'',
        pid:'',
        serviceVersion:'',
        status:'',
      },
      requestParamsForm: {
        tableData:[],
        size:'',
      },
      toTestForm:{
        zookeeper:'',
        id:'',
        groupId:'',
        serviceVersion:'',
        serviceName:'',
        interfaceName:'',
        serviceMethod:'',
      },
      zanzhu:[],
      currentDate: new Date()
    }
  },

  methods: {

    //table序号
    indexMethod(index) {
      return index + 1;
    },

    handleChange(value) {
      console.log(value);
    },
    toDetail(data){
      let param = {
        id:data
      }
      console.log(data)
      this.$ajax({
        method: "post",
        url: "/interface/interfaceList/querySysInterfaceList",
        data: param
      }).then((resultData) =>{
         console.log(resultData)
        // for (var i=0;i<resultData.data.length;i++){
        //   this.apiDetail = resultData.data.data.get(0);
        // }
        this.apiDetail = resultData.data.data[0];
         console.log(this.apiDetail.gatewayServiceRequestDOS)
        this.requestParamsForm =  resultData.data.data[0].gatewayServiceRequestDOS;
         this.detailFormVisible=true
        console.log(this.requestParamsForm)

      });
    },
    toShowApiDocument(sid){
      console.log(sid)
      this.$router.push({
        path: '/apiDocumentDetail2',
        query:{
          sid:sid,
        }

      });
    },
    handleClick(tab, event) {
      if(tab.index==3){
        this.toShowApiDocument(event)
      }
    },
    toTest(data){
      console.log(data)
      //
      this.toTestForm.zookeeper=data.zookeeper
      this.toTestForm.id=data.pid
      // this.toTestForm.groupId=row.groupId
      this.toTestForm.serviceVersion=data.serviceVersion
      this.toTestForm.serviceName=data.serviceName
      this.toTestForm.interfaceName=data.interfaceName
      this.toTestForm.serviceMethod=data.serviceMethod
      //
      var _this = this
      let param={
        zookeeper:data.zookeeper,
        // groupId:data.groupId,
        createBy:data.createBy,
        serviceVersion:data.serviceVersion,
      }
      console.log(param)
      this.$ajax({
        method: "post",
        url: "/interface/zkConfig/createDubboModel",
        data: param
      }).then((resultData) =>{
        let param={
          zk:data.zookeeper,
          // groupId:row.groupId,
          createBy:data.createBy,
          serviceVersion:data.serviceVersion,
          serviceName:data.serviceName,
          interfaceName:data.interfaceName,
          serviceMethod:data.serviceMethod,
          id:data.pid,
        }
        this.$ajax({
          method: "post",
          url: "/interface/beforeTestInterface/createDubboModel",
          data: param
        }).then((resultData) =>{

          var _this = this
          console.log("大萨达撒多",_this.toTestForm)
          let index = this.toTestForm.interfaceName.lastIndexOf("/");
          let provider = this.toTestForm.interfaceName.substr(index+1)
          console.log("provider",provider)
          this.$router.push({
            path: '/TestInterface',
            query:{zk: this.toTestForm.zookeeper,
              serviceName:this.toTestForm.serviceName,
              provider:provider,
              methodName:this.toTestForm.serviceMethod,
              id:this.toTestForm.id
            }

          });
        });
      });
      // _this.$router.push({path: 'api', query: {group: row}})
    },
    sendSonData(data){
      if (data!=null){
        this.list=[];
      }
      // this.list = data
      for (var i=0;i<data.length;i++) {
        this.api=data[i];
        this.list.push(this.api)
      }


    },

    rollBackTables() {
      var text = "数据还原";
      apis.shiroApi
        .rollBackTables()
        .then(data => {
          var alertText = "";
          if (data.data.status == "SUCCESS") {
            text += "成功";
            alertText = text + ",请重新登陆";
          } else {
            text += "失败";
            alertText = text + ",请重试";
          }
          this.$alert(alertText, "提示", {
            confirmButtonText: "确定"
          });
          log(text);
        })
        .catch(e => {
          this.$alert("数据还原异常,请重试", "提示", {
            confirmButtonText: "确定"
          });
          text += "失败";
          log(text);
        });
      console.log(text);

      function log(text) {
        var loginLog = {
          ip: returnCitySN["cip"],
          city: returnCitySN["cname"] + "-" + text
        };

        apis.shiroApi.loginLog(loginLog);
      }
    },
    openZanZhu(){
      this.$common.OpenNewPage(this,'zanzhu');
    },
    getZanZhu(){
      apis.mayiApi.getZanZhu().then(res=>{
        if(res.status=="200"){
          if(res.data.dataList){
            this.zanzhu=res.data.dataList;
          }
        }
      }).catch(

      );
    },
    getDate(param){
      return this.$common.toDate(param);
    }
  },
  mounted() {
    this.getZanZhu();
  },

  computed:{

  }
};
</script>
