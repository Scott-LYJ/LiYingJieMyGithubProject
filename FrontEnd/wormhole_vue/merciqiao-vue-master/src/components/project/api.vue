<template>
    <div>
      <div>
        <div style="height: 200px;width: 200px;position: absolute;right: 200px;top: 100px" ref="icon">
          <div style="width: 200px;height: 170px;" class="user-avator">
            <el-form ref="icon">
            </el-form>
          </div>

        </div>
        <div class="container form02">
          <el-form :label-position="labelPosition" ref="group" :label-width="labelWidth" :inline="false" :model="group" class="demo-form-inline">
            <el-form-item label="id" prop="id">
              <el-input v-model="group.id"  :disabled="true" ></el-input>
            </el-form-item>
            <el-form-item label="分组名" prop="name">
              <el-input v-model="group.name" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="创建人" prop="">
              <el-input   v-model="group.creater" placeholder="请输入地址" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="创建时间" prop="cdt">
              <el-date-picker  v-model="group.cdt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
            </el-form-item>
            <el-form-item label="分组描述" prop="">
              <el-input type="textarea"  :autosize="{ minRows: 4, maxRows: 6}" v-model="group.des" auto-complete="off" :disabled="true" style=" width: 500px"></el-input>
            </el-form-item>


          </el-form>
        </div>



      </div>
        <div class="pagepath">

        </div>
        <el-tabs type="border-card">
            <el-tab-pane label="接口">
              <group-api :message="groupId"></group-api>
            </el-tab-pane>
            <el-tab-pane label="图表">
              <api-chart :message3="groupId"></api-chart>
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
  import groupApi from './group_api.vue';
  import apiChart from '../chart/ApiChart.vue'
export default {
  name: 'tabpage',
  components: {
    vSearchinput,
    groupApi,
    apiChart,
  },
  data() {
    return {
      labelPosition: "right", //lable对齐方式
      labelWidth: "80px", //lable宽度
      userIcon: null,
      groupId:'',
      group: {
        id: null,
        name: null,
        des: null,
        creater: null,
        cdt: null,
      }
    }
  },
    methods: {

    },
    mounted(){
      var group = this.$route.query.group;
      this.group.id=group.id
      this.groupId = group.id
      this.group.name=group.name
      this.group.des = group.description
      this.group.creater = group.createBy
      this.group.cdt=group.cdt
      console.log(group)
    }
}
</script>
