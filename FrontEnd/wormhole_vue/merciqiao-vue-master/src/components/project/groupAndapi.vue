<template>
    <div>
      <div>
        <div style="height: 200px;width: 200px;position: absolute;right: 200px;top: 100px" ref="icon">
          <div style="width: 200px;height: 170px;" class="user-avator">
            <el-form ref="icon">
              <img :src="userIcon">
            </el-form>
          </div>

        </div>
        <div class="container form02">
          <el-form :label-position="labelPosition" ref="project" :label-width="labelWidth" :inline="false" :model="project" class="demo-form-inline">
            <el-form-item label="id" prop="id">
              <el-input v-model="project.id"  :disabled="true" style="width: 100px"></el-input>
            </el-form-item>
            <el-form-item label="项目名" prop="name">
              <el-input v-model="project.name" :disabled="true" style="width: 200px"></el-input>
            </el-form-item>
            <el-form-item label="创建人" prop="address">
              <el-input   v-model="project.creater" placeholder="请输入地址" :disabled="true" style="width: 200px"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="cdt">
              <el-date-picker  v-model="project.cdt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
            </el-form-item>
            <el-form-item label="项目描述" prop="password">
              <el-input type="textarea"  :autosize="{ minRows: 4, maxRows: 6}" v-model="project.des" auto-complete="off" :disabled="true" style=" width: 500px"></el-input>
            </el-form-item>


          </el-form>
        </div>



      </div>
        <div class="pagepath">
        </div>
        <!--<el-breadcrumb separator="/" style="padding:10px;border:1px solid #ddd;background:#fff;margin-bottom:1px;">-->
            <!--<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
            <!--<el-breadcrumb-item>-->
                <!--<a href="/">活动管理</a>-->
            <!--</el-breadcrumb-item>-->
            <!--<el-breadcrumb-item>活动列表</el-breadcrumb-item>-->
            <!--<el-breadcrumb-item>活动详情</el-breadcrumb-item>-->
        <!--</el-breadcrumb>-->
        <el-tabs type="border-card">
            <el-tab-pane label="分组">
                分组授权
              <vgroup-detail :message="projectId" ></vgroup-detail>
            </el-tab-pane>
            <el-tab-pane label="接口">接口<br/></el-tab-pane>
            <el-tab-pane label="图表" >
              <div>
                <div style="float: left">
              <group-chart :message="projectId" ></group-chart>
                </div>
                <div>
                  dada
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane label="定时任务补偿">定时任务补偿</el-tab-pane>
        </el-tabs>
    </div>
</template>
<style>
  .user-avator{
    margin-left: 15px;
  }
  .user-avator img{
    display: block;
    width:170px;
    height:170px;
    border-radius: 50%;
  }
</style>

<script>
import vSearchinput from '../searchinput/searchinput.vue';
import vgroupDetail from './GroupDetail.vue'
import groupChart from '../chart/GroupChart.vue'
import interfaceChart from '../chart/InterfaceChart.vue'

export default {
  name: 'groupAndapi',
  components: {
    vSearchinput,
    vgroupDetail,
    groupChart,
    interfaceChart,
  },
  data() {
    return {
      projectId:'',
      labelPosition: "right", //lable对齐方式
      labelWidth: "80px", //lable宽度
      userIcon: null,
      project: {
        id: null,
        name: null,
        des: null,
        creater: null,
        cdt: null,
        avatar: null,
      }
    }
  },
    methods: {
      // reload () {
      //   this.update = false
      //   this.$nextTick(() => (this.update = true))
      // },
      // updateView(update){
      //   this.update=false;
      //   this.$nextTick(() => (this.update = true))
      //   alert(this.update)
      // },
        fun(){
          alert("dasdasd");
        }
    },
    mounted(){
      var project = this.$route.query.project;
      this.project.id=project.id
      this.projectId=project.id
      this.project.name=project.name
      this.project.des = project.des
      this.project.creater = project.authUser.name
      this.project.cdt=project.cdt
      this.userIcon = project.avatar
      console.log(project)
    }
}
</script>
