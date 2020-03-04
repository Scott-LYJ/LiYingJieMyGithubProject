<template>
  <div>
    <el-steps :active="active" finish-status="success">
      <el-step title="接口信息" description="用来设置名称，描述等信息"></el-step>
      <el-step title="注册中心设置" description="设置zookeeper网址，可测试"></el-step>
      <el-step title="dubbo服务设置" description="dubbo服务的接口和方法"></el-step>
    </el-steps>
    <!--动态组件-->
    <keep-alive>
      <!-- 接口信息编辑页面-->
      <interface-detail ref="interfaceDetail" v-if="active==0" @isSave="is_save" @interfaceDetailSave="interfaceDetailSave"></interface-detail>
      <zookeeper-detail v-if="active==1" @zookeeperDetailSave="zookeeperDetailSave"></zookeeper-detail>
      <dubbo-detail v-if="active==2" @dubboDetailSave="dubboDetailSave"></dubbo-detail>
      <!--<interface-detail v-if="active==1"></interface-detail>-->
      <!--<interface-detail v-if="active==2"></interface-detail>-->
    </keep-alive>
    <div class="x-btn" style="float: right">
      <el-button type="info" v-if="active==1||active==2" @click="prev" >上一步</el-button>
      <el-button type="primary" v-if="active==0||active==1" @click="next" :disabled="!isSave">下一步</el-button>
      <el-button type="danger" v-if="active==2" @click="submit">完成</el-button>
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
    export default {
        name: "CreateInterface",
        components:{
          interfaceDetail,
          zookeeperDetail,
          dubboDetail
        },
        data() {
          return {

            dialogVisible: false,

            isSave:false,
            active: 0,
            isInterfaceDetail:'',
            isZookeeperDetail:'',
            isDubboDetail:'',
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
            this.submitInformation.interfaceName = data.interfaceName;
            this.submitInformation.serviceMethod = data.serviceMethod
            this.submitInformation.serviceVersion = data.serviceVersion
            console.log(this.submitInformation)
          },
          //判断接口信息是否点击保存
          is_save(data){
                this.isSave = data;
                console.log(this.isSave)
          },
          next() {
            if (this.active++ > 2) this.active = 0;
          },
          prev(){
            --this.active;
            if (this.active<0) this.active=0
          },
          submit(){
              if (this.isInterfaceDetail==''||this.isDubboDetail==''||this.isZookeeperDetail==''){
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
              this.$router.push({
                path: '/TestInterface',
                query:{zk: _this.submitInformation.zookeeper[0],
                  serviceName:_this.submitInformation.serviceName,
                  provider:_this.submitInformation.interfaceName,
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
