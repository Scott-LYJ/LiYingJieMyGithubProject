<template>

  <div class="pie2">
    <div id="pie13">
      <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
      <div id="main2" style="float:right;width:400px;height: 300px"></div>
    </div>
  </div>

</template>

<script>// 引入基本模板
let echarts = require('echarts/lib/echarts')
// 引入饼状图组件
require('echarts/lib/chart/pie')
// 引入提示框和title组件
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')

import apiChart from '../chart/ApiChart.vue'
export default {

  name:"InterfaceChart",
  props: ['message3'],
  components:{
    apiChart,
  },
  watch: {
    message3: function (newVal, oldVal) {
      console.log("wwwwwwwwwww", newVal)
      this.groupId = newVal
      console.log("groupId", this.groupId)
      this.initData();
    }
  },
  created(){
  },
  data() {
    return {
      groupId: "",
      total:"",
      Yesvalue:"",
      Novalue:"",
    }
  },
  // mounted(){
  //   this.initData();
  // },
  methods:{
    //初始化数据
    initData() {
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main2'));
      let param = {
        groupId: this.groupId
      }
      this.$ajax({
        method: "post",
        url: "/group/queryGroupApiCount",
        contentType: 'application/json; charset=UTF-8',// contn
        data: param
      }).then(res => {
        this.total=res.data.data;
        this.Yesvalue=res.data.count;
        this.Novalue=this.total-this.Yesvalue;
        console.log("值",this.total,this.Yesvalue,this.Novalue);

        // 绘制图表
        myChart.setOption({
          title : {
            text: '该项目接口分配',//主标题
            subtext: '接口总数:'+res.data.data,//副标题
            x:'center',//x轴方向对齐方式
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            orient: 'vertical',
            bottom: 'bottom',
            data: ['已分配接口','未分配接口']
          },
          series : [
            {
              name: '访问来源',
              type: 'pie',
              radius : '55%',
              center: ['50%', '60%'],
              data:[
                {value:res.data.count, name:'已分配接口'},
                {value:res.data.data-res.data.count, name:'未分配接口'},
              ],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        });

      })

    },
  }
}
</script>
