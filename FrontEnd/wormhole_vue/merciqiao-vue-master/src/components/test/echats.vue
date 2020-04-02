<template>
  <div class="row state-overview">
    <div class="col-lg-3 col-sm-6 layui-anim layui-anim-up">
      <section class="panel">
        <div class="symbol commred layui-anim layui-anim-rotate"> <i class="iconpx-user-add"></i>
        </div>
        <div class="value">
          <a href="#">
            <h1 id="count2">{{allInterfaceCount}}</h1>
          </a>
          <p>接口总数</p>
        </div>
      </section>
    </div>
    <div class="col-lg-3 col-sm-6 layui-anim layui-anim-up">
      <section class="panel">
        <div class="symbol articlegreen layui-anim layui-anim-rotate">
          <i class="iconpx-file-word-o"></i>
        </div>
        <div class="value">
          <a href="#">
            <h1 id="count3">{{allDocumentCount}}</h1>
          </a>
          <p>接口文档总数</p>
        </div>
      </section>
    </div>
    <div class="col-lg-3 col-sm-6 layui-anim layui-anim-up">
      <section class="panel">
        <div class="symbol rsswet layui-anim layui-anim-rotate">
          <i class="iconpx-check-circle"></i>
        </div>
        <div class="value">
          <a href="#">
            <h1 id="count4">{{allTestCount}}</h1>
          </a>
          <p>已测试接口总数</p>
        </div>
      </section>
    </div>
    <div>
    <div id="chartLineBox" style="width: 90%;height: 70vh;margin-top: 130px"> </div>
    </div>
  </div>
</template>

<script>
  import echarts from 'echarts'
    export default {
        name: "echats",
      data(){
          return{
            allInterfaceCount:'',
            allTestCount:'',
            allDocumentCount:'',
            time:[],
            documentCount:[],
            interfaceCount:[],
          }
      },
      mounted(){
        this.$ajax({
          method: "get",
          url: "/interface/interfaceStatus/showEcharts",
        }).then((resultData)=>{
          console.log(resultData)
         //
          this.allInterfaceCount=resultData.data.allInterfaceCount
          this.allTestCount=resultData.data.allTestCount
          this.allDocumentCount=resultData.data.allDocumentCount
          this.time=resultData.data.time
          this.documentCount=resultData.data.documentCount
          this.interfaceCount=resultData.data.interfaceCount


          //
          this.chartLine = echarts.init(document.getElementById('chartLineBox'));

          // 指定图表的配置项和数据
          var option = {
            tooltip: {              //设置tip提示
              trigger: 'axis'
            },

            legend: {               //设置区分（哪条线属于什么）
              data:['新建接口数','新建文档数']
            },
            color: ['#8AE09F', '#FA6F53'],       //设置区分（每条线是什么颜色，和 legend 一一对应）
            xAxis: {                //设置x轴
              type: 'category',
              boundaryGap: false,     //坐标轴两边不留白
              data: this.time,
              name: 'DATE',           //X轴 name
              nameTextStyle: {        //坐标轴名称的文字样式
                color: '#FA6F53',
                fontSize: 16,
                padding: [0, 0, 0, 20]
              },
              axisLine: {             //坐标轴轴线相关设置。
                lineStyle: {
                  color: '#FA6F53',
                }
              }
            },
            yAxis: {
              name: '接口状况图',
              nameTextStyle: {
                color: '#FA6F53',
                fontSize: 16,
                padding: [0, 0, 10, 0]
              },
              axisLine: {
                lineStyle: {
                  color: '#FA6F53',
                }
              },
              type: 'value'
            },
            series: [
              {
                name: '新建接口数',
                data:  this.interfaceCount,
                type: 'line',               // 类型为折线图
                lineStyle: {                // 线条样式 => 必须使用normal属性
                  normal: {
                    color: '#8AE09F',
                  }
                },
              },
              {
                name: '新建文档数',
                data: this.documentCount,
                type: 'line',
                lineStyle: {
                  normal: {
                    color: '#FA6F53',
                  }
                },
              }
            ]
          };

          // 使用刚指定的配置项和数据显示图表。
          this.chartLine.setOption(option);
          //
        });

      },
    }
</script>


<style scoped>
  @import '../../assets/css/layui.css';
  @import '../../assets/css/main.css';
  @import '../../assets/css/bootstrap.css';
  @import '../../assets/css/global.css';
</style>
