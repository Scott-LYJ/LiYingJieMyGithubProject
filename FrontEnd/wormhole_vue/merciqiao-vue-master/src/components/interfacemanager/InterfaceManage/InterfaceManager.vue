<template>
  <el-tabs :tab-position="tabPosition" style="height: 100%;"  v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="接口状况" name="first" :key="'first'" v-has="'/interface/status/show'">
      <echarts></echarts>
    </el-tab-pane>
    <el-tab-pane label="接口列表" name="second" :key="'second'" v-has="'/interface/list/show'">
      <interface-list v-if="isChildUpdate2"></interface-list>
    </el-tab-pane>
    <el-tab-pane label="新建接口" name="third" :key="'third'" v-has="'/interface/create/show'">
      <create-interface v-if="isChildUpdate3"></create-interface>
    </el-tab-pane>
    <el-tab-pane label="测试"  name="forth" :key="'forth'" v-has="'/interface/test/show'">
      <test-interface v-if="isChildUpdate4"></test-interface>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
  import interfaceList from '../InterfaceList/InterfaceList.vue'
  import createInterface from '../CreateInterface/CreateInterface.vue'
  import testInterface from '../TestInterface/TestInterface.vue'
  import echarts from '../../test/echats.vue'
  export default {
    components: {
      interfaceList,
      createInterface,
      testInterface,
      echarts

    },
    name:"InterfaceManager",
    data() {
      return {

        //默认第一个选项卡
        activeName: "first",
        isChildUpdate1:true,
        isChildUpdate2:false,
        isChildUpdate3:false,
        isChildUpdate4:false,
        tabPosition: 'left'
      };
    },
    methods:{
      handleClick(tab) {
        if(tab.name == "first") {
          this.isChildUpdate1 = true;
          this.isChildUpdate2 = false;
          this.isChildUpdate3 = false;
          this.isChildUpdate4 = false;
        } else if(tab.name == "second") {
          this.isChildUpdate1 = false;
          this.isChildUpdate2 = true;
          this.isChildUpdate3 = false;
          this.isChildUpdate4 = false;
        }else if(tab.name == "third") {
          this.isChildUpdate1 = false;
          this.isChildUpdate2 = false;
          this.isChildUpdate3 = true;
          this.isChildUpdate4 = false;
        }else if(tab.name == "forth") {
          this.isChildUpdate1 = false;
          this.isChildUpdate2 = false;
          this.isChildUpdate3 = false;
          this.isChildUpdate4 = true;
        }
      }
    }
  };
</script>
