<template>
  <div>
    <br/>
    <br/>
    <br/>
    <el-form ref="dubboDetailForm" v-show="show1" :model="dubboDetailForm" label-width="80px" id="isEnable">
      <el-form-item label="zk名称">
        <el-select v-model="dubboDetailForm.zk" placeholder="必填，访问的ZK地址" filterable @change="changeZk">
          <el-option v-for="(option,index) in pageTotal.zkList" v-bind:value="option" :key="index" :label="option">
            {{ option }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="服务名称">
        <el-select v-model="dubboDetailForm.serviceName" style="width: 100%" placeholder="必填，访问的服务名称" filterable @change="changeService">
          <el-option v-for="(option,index) in  pageTotal.serviceNames" :key="index" v-bind:value="option" :label="option">
            {{ option }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="接口路径">
        <!--<el-input v-model="dubboDetailForm.interfaceName" style="width: 600px"></el-input>-->
        <el-select v-model="dubboDetailForm.interfaceName" placeholder="必填，请选择接口名称" filterable @change="changeProvider">
          <el-option v-for="(option,index) in pageTotal.providers" :key="index" v-bind:value="option" :label="option">
            {{ option }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="方法名称">
        <!--<el-input v-model="dubboDetailForm.serviceMethod" style="width: 300px"></el-input>-->
        <el-select v-model="dubboDetailForm.serviceMethod" placeholder="必填，请选择方法名称" filterable npm run dev>
          <el-option v-for="(option,index) in pageTotal.methodNames" :key="index" v-bind:value="option" :label="option"@click="get">
            {{ option }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="版本号">
        <el-select v-model="dubboDetailForm.serviceVersion" placeholder="版本号">
          <el-option label="1.0.0.local" value="1.0.0.local"></el-option>
          <el-option label="1.0.0.dev" value="1.0.0.dev"></el-option>
          <el-option label="1.0.0.daily" value="1.0.0.daily"></el-option>
          <el-option label="1.0.0.prehub" value="1.0.0.prehub"></el-option>
          <el-option label="1.0.0.online" value="1.0.0.online"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="超时时间">
        <el-input-number v-model="dubboDetailForm.timeOut" :step="1000"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSave">保存</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>


  <el-form ref="dubboDetailForm" v-show="show2" :model="dubboDetailForm" label-width="80px" id="isNoEnable">
    <el-form-item label="服务名称">
      <el-input v-model="dubboDetailForm.serviceName" style="width: 600px"></el-input>
    </el-form-item>
    <el-form-item label="接口路径">
      <el-input v-model="dubboDetailForm.interfaceName" style="width: 600px"></el-input>
    </el-form-item>
    <el-form-item label="方法名称">
      <el-input v-model="dubboDetailForm.serviceMethod" style="width: 300px"></el-input>
    </el-form-item>
    <el-form-item label="版本号">
      <el-select v-model="dubboDetailForm.serviceVersion" placeholder="版本号">
        <el-option label="1.0.0.local" value="1.0.0.local"></el-option>
        <el-option label="1.0.0.dev" value="1.0.0.dev"></el-option>
        <el-option label="1.0.0.daily" value="1.0.0.daily"></el-option>
        <el-option label="1.0.0.prehub" value="1.0.0.prehub"></el-option>
        <el-option label="1.0.0.online" value="1.0.0.online"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="超时时间">
      <el-input-number v-model="dubboDetailForm.timeOut" :step="1000"></el-input-number>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSave">保存</el-button>
      <el-button @click="onSwitch">切换</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>

<script>
  import {getRegisterService,getAllMethods,getArgs,getAllProviders,getZkList,
    getTemplate,getRemoteHistoryTemplate,getRemoteAssignedTemplate,
    doRequest,saveHisTemplate} from '@/apis/access';
  import{saveCase,getGroupAndCaseName,getAllGroupName,queryCaseDetail} from '@/apis/testCase';
    export default {
        name: "DubboDetail",
        data() {
          return {
            show1:false,
            show2:true,
            dubboDetailForm: {
              zk:'',
              timeOut:'5000',
              serviceName: '',
              interfaceName:'',
              providerName:'',
              serviceMethod: '',
              serviceVersion: '',
            },
            pageTotal:{
              zkList:[],
              serviceNames:[],
              providers:[],
              methodNames:[],
              providerNameMap:[],
            }
          }
        },
        methods: {
          onSave() {
            console.log(this.dubboDetailForm)
            console.log("map:",this.pageTotal.providerNameMap)

            this.$notify({
              title: '提示',
              message:  'dubbo信息保存成功',
              type: 'success'
            });
            console.log('save!');

            this.$emit("dubboDetailSave",this.dubboDetailForm)
            console.log('submit!');
          },
          //
          changeZk:function (value) {
            console.log("current zk:",value);

            this.pageTotal.serviceNames=[];
            let serviceName = window.localStorage.getItem('serviceName');
            if(serviceName){
              //存在local,不变,因为上一个页面传入
            }else{
              //否则清空
              this.dubboDetailForm.serviceName="";
              this.pageTotal.serviceNames=[];
            }
            this.dubboDetailForm.serviceName="";
            this.pageTotal.serviceNames=[];

            this.pageTotal.providers=[];
            this.dubboDetailForm.interfaceName="";


            this.dubboDetailForm.serviceMethod = '';
            this.pageTotal.methodNames = [];

            this.getAllService();
          },
          getAllService(){
            let ezk = encodeURI(this.dubboDetailForm.zk);
            let params = {"zk":ezk};
            getRegisterService(params).then((res) => {
              this.pageTotal.serviceNames = res.data.data;
            });
          },
          //
          changeService:function (value) {
            console.log("current service:",value);

            this.dubboDetailForm.providers=[];
            this.pageTotal.interfaceName="";


            this.dubboDetailForm.serviceMethod = '';
            this.pageTotal.methodNames = [];


            this.getProviders();
          },
          getProviders(){
            let ezk = encodeURI(this.dubboDetailForm.zk);
            let params = {"zk":ezk,"serviceName":this.dubboDetailForm.serviceName};
            return getAllProviders(params).then((res) =>{
              let ms = res.data.data;
               this.pageTotal.providerNameMap = ms;
              this.pageTotal.providers = [];
              for(let item in ms){
                this.pageTotal.providers.push(item);
              }
            });
          },
          //
          changeProvider:function (value) {
            console.log("current provider:",value);
            let map = this.pageTotal.providerNameMap;
            console.log(map)
            this.dubboDetailForm.providerName= map[value];

            this.getMethods();
          },
          getMethods(){
            let ezk = encodeURI(this.dubboDetailForm.zk);
            console.log("providerKey请求:",this.dubboDetailForm.providerName);
            let infKey = encodeURI(this.dubboDetailForm.providerName);
            let params = {
              "zk":ezk,
              "serviceName":this.dubboDetailForm.serviceName,
              "interfaceKey":infKey
            };
            console.log("请求:11111111",params);
            getAllMethods(params).then((res) =>{
              console.log("返回数据methods",res)
              let ms = res.data.data.methodNames;
              this.pageTotal.methodNames = ms;
                this.dubboDetailForm.serviceMethod=ms[0]
            });
          },
          //
          get(){
            console.log("sssssssssss")
          },
          onSwitch(){
              if (this.show1=true){
                this.show1=false;
                this.show2=true;
              }else if (this.show1=false){
                this.show1=true;
                this.show2=false;
                getZkList().then((res)=>{
                  console.log(res)
                  this.pageTotal.zkList = res.data.data;
                })
              }
          }
        },
      mounted(){
          console.log("11111111111111");
          console.log(this.$common.getSessionStorage("isZkEnable"))
          if (this.$common.getSessionStorage("isZkEnable")==0){

            this.show1=true;
            this.show2=false;
            getZkList().then((res)=>{
              console.log(res)
              this.pageTotal.zkList = res.data.data;
            })

          }else {

            this.show1=false;
            this.show2=true;
          }

      }
    }
</script>

<style scoped>

</style>
