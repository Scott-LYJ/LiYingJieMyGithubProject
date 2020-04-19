<template>
  <div>
    <el-steps :active="active" finish-status="success">
      <el-step title="接口信息" description="用来设置名称，描述等信息"></el-step>
      <el-step title="注册中心设置" description="设置zookeeper网址，可测试"></el-step>
      <el-step title="dubbo服务设置" description="dubbo服务的接口和方法"></el-step>
      <el-step title="请求参数设置" description="用于构建请求模板"></el-step>
    </el-steps>
    <!--动态组件-->
    <keep-alive>
      <!-- 接口信息编辑页面-->
      <interface-detail ref="interfaceDetail" v-if="active==0" @isSave="is_save" @interfaceDetailSave="interfaceDetailSave"></interface-detail>
      <zookeeper-detail v-if="active==1" @zookeeperDetailSave="zookeeperDetailSave"></zookeeper-detail>
      <dubbo-detail v-if="(active==2||isZkEnable)&&active!=3" @dubboDetailSave="dubboDetailSave"></dubbo-detail>
      <request-params v-if="active==3" @requestParamsSave="requestParamsSave"></request-params>
      <!--<interface-detail v-if="active==1"></interface-detail>-->
      <!--<interface-detail v-if="active==2"></interface-detail>-->
    </keep-alive>
    <div class="x-btn" style="float: right">
      <el-button type="info" v-if="active==1||active==2||active==3" @click="prev" >上一步</el-button>
      <el-button type="primary" v-if="active==0||active==1||active==2" @click="next" :disabled="!isSave">下一步</el-button>
      <el-button type="danger" v-if="active==3" @click="submit">完成</el-button>
    </div>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>是否前往测试界面进行接口测试</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="toTest">确 定</el-button>
  </span>
    </el-dialog>
  </div>





</template>

<script>
  import interfaceDetail from './SubVue/InterfaceDetail.vue'
  import zookeeperDetail from './SubVue/ZookeeperDetail.vue'
  import dubboDetail from './SubVue/DubboDetail.vue'
  import requestParams from './SubVue/RequestParams.vue'
    export default {
        name: "CreateInterface",
        components:{
          interfaceDetail,
          zookeeperDetail,
          dubboDetail,
          requestParams
        },
        data() {
          return {
            isZkEnable:false,
            dialogVisible: false,

            isSave:false,
            active: 0,
            isInterfaceDetail:'',
            isZookeeperDetail:'',
            isDubboDetail:'',
            isRequestParams:'',
            submitInformation:{
              id:'',
              name: '',
              groupId: '',
              description: '',
              isAuth: '',
              status: null,
              isLogin: '',
              authVersion:'',
              pass: '',
              zookeeper: '',
              timeOut:'',
              serviceName: '',
              interfaceName:'',
              serviceMethod: '',
              serviceVersion: '',
              requestParams:{
                     tableData:[],
                      size:'',
              }

            }
          };
        },

        methods: {
          handleClose(done) {
            this.$confirm('确认关闭？')
              .then(_ => {
                done();
              })
              .catch(_ => {});
          },
          //保存接口信息
          interfaceDetailSave(data){
            this.isInterfaceDetail = data;
            this.submitInformation.name = data.name
            this.submitInformation.groupId = data.groupId;
            this.submitInformation.authVersion = data.authVersion
            this.submitInformation.description = data.description
            this.submitInformation.isLogin = data.isLogin
            this.submitInformation.isAuth = data.isAuth
            this.submitInformation.status = data.status
            console.log(this.submitInformation)
          },
          //保存zk信息
          zookeeperDetailSave(data){
            this.isZookeeperDetail = data;
            this.submitInformation.pass = data.pass
            this.submitInformation.zookeeper = data.zookeeper
            console.log(this.submitInformation)
          },
          //保存dubbo信息
          dubboDetailSave(data){
            this.isDubboDetail = data
            this.submitInformation.timeOut = data.timeOut
            this.submitInformation.serviceName = data.serviceName
            this.submitInformation.interfaceName = data.providerName;
            this.submitInformation.serviceMethod = data.serviceMethod
            this.submitInformation.serviceVersion = data.serviceVersion
            console.log(this.submitInformation);
          },
          //保存请求参数模板信息
          requestParamsSave(data){
              this.isRequestParams = data
              this.submitInformation.requestParams.size=data.size
              this.submitInformation.tableData = data.tableData

          },
          //判断接口信息是否点击保存
          is_save(data){
                this.isSave = data;
                console.log(this.isSave)
          },
          next() {
            if ( this.$common.getSessionStorage("isZkEnable")==0){
              this.isZkEnable=true;
            }
            if (this.active++ > 3) this.active = 0;
          },
          prev(){
            --this.active;
            if (this.active<0) this.active=0
          },
          submit(){
              if (this.isInterfaceDetail==''||this.isDubboDetail==''||this.isZookeeperDetail==''||this.isRequestParams==''){
                this.$message.error('您有一步没有保存，请保存再提交');
              }else{
                console.log(this.submitInformation)
               //创建接口
                this.$ajax({
                  method: "post",
                  url: "/interface/createrInterface/insertInterface",
                  data:this.submitInformation
                }).then((res) =>{
                  console.log(res)
                  this.submitInformation.id = res.data.data.id;
                  this.dialogVisible=true;
                  //
                  let levList = this.$common.getSessionStorage('lev',true);
                  let roleId = levList[0].id;
                  let param = {
                    userId : this.$common.getSessionStorage("id"),
                    roleId:roleId,
                    message:'创建了一个接口('+this.submitInformation.name+')',
                    isRead:0
                  }
                  this.$ajax({
                    method: "post",
                    url: "/message/send?userId="+this.$common.getSessionStorage("id"),
                    data: param
                  }).then(function(resultData) {

                  });
                  //
                });



                // this.$router.push({
                //   path: '/TestInterface', query:{zk: this.submitInformation.zookeeper,serviceName:"user_provider"}
                //
                // });
              }
          },
          toTest(){

            this.$ajax({
              method: "post",
              url: "/interface/beforeTestInterface/createDubboModel",
              data:this.submitInformation
            }).then((res) =>{
              console.log(res)



              var _this = this
              console.log(_this.submitInformation)
              let index = _this.submitInformation.interfaceName.lastIndexOf("/");
              let provider = _this.submitInformation.interfaceName.substr(index+1)
              console.log("provider",provider)
              this.$router.push({
                path: '/TestInterface',
                query:{zk: _this.submitInformation.zookeeper,
                  serviceName:_this.submitInformation.serviceName,
                  provider:provider,
                  methodName:_this.submitInformation.serviceMethod,
                  id:_this.submitInformation.id
                }

              });
            });





          }
          //


        }
    }
</script>

<style scoped>

</style>
