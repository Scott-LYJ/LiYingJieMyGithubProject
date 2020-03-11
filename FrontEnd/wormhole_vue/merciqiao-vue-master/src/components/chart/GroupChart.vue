<template>

    <div class="pie">
        <div id="pie1">
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div id="main1" style="float:left;width:400px;height: 300px"></div>
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


export default {
  name: "GroupChart",
  props: ['message'],
  watch: {
    message: function (newVal, oldVal) {
      console.log("11123123123", newVal)
      this.projectId = newVal
      console.log("projectid", this.projectId)
      this.getResult();
      this.initData();
    }
  },
  created() {
  },
  data() {
    return {
      projectId: "",
      total:"",
      Yesvalue:"",
      Novalue:"",
    }
  },
  // mounted() {
  //   this.getResult();
  //   this.initData();
  // },
  methods: {
    getResult() {
      console.log("11111111111")

      console.log(22222)
    },

    //初始化数据
    initData() {
      console.log("echarts初始化",this.total)
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main1'));
      //
      let param = {
        projectId: this.projectId
      }
      this.$ajax({
        method: "post",
        url: "/projectGroup/queryGroupCount",
        contentType: 'application/json; charset=UTF-8',// contn
        data: param
      }).then(res => {
        console.log(res)
        this.listLoading = false;
        this.$message({
          message: "加载数据成功",
          type: "success"
        });
        this.total=res.data.data;
        this.Yesvalue=res.data.count;
        this.Novalue=this.total-this.Yesvalue;
        console.log("值",this.total,this.Yesvalue,this.Novalue);

       // 绘制图表
        myChart.setOption({
          title: {
            text: '该项目分组分配',//主标题
            subtext: '分组总数:'+res.data.data,//副标题
            x: 'center',//x轴方向对齐方式
          },
          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            orient: 'vertical',
            bottom: 'bottom',
            data: ['已分配分组', '未分配分组']
          },
          series: [
            {
              name: '访问来源',
              type: 'pie',
              radius: '55%',
              center: ['50%', '60%'],
              data: [
                {value: res.data.count, name: '已分配分组'},
                {value: res.data.data-res.data.count, name: '未分配分组'},
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
        //



      });


    },

  }
}
</script>
