<template>
<div>
    <div style="padding: 6px;height: 310px;margin: auto">
      <div>
        <el-form ref="api" :model="api" label-width="80px"size="mini" label-position="left">
          <el-form-item label="创建人:" >
            <el-tag
              type=""
              effect="dark"
            >
              {{api.createBy}}
            </el-tag>
          </el-form-item >
          <el-form-item label="分组:" style="margin-top:-10px">
            <el-tag
              type="info"
              effect="dark"
            >
              {{api.createBy}}
            </el-tag>
          </el-form-item>

        </el-form>
        <el-form ref="api" :model="api" label-width="80px"size="mini" label-position="top">
          <el-form-item label="描述:" style="margin-top:-10px">
                <div style="margin-top:-10px" class="txt">
                  <font size="1px">与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；
                  在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。</font>
                </div>
          </el-form-item>
          <el-form-item  style="margin-top:-10px">
          <!--<el-tag >启用</el-tag>-->
          <el-tag type="danger">禁用</el-tag>
          &nbsp;&nbsp;&nbsp;
          <i class="el-icon-view"></i><span>22222</span>
          &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
          <i class="el-icon-time"></i><span>11111</span>
        </el-form-item>
          <el-form-item  style="margin-top:-10px">
            <el-button type="info" round style="float: left;width: 100px">详情</el-button>
            <el-button type="success" round style="float: right;width: 100px">测试</el-button>
          </el-form-item>
        </el-form>
  </div>
</div>
</div>
</template>

<script>
  export default {
    name: "apiCard",
    data(){
      return{
        api:{
          name:'分组名称',
          createBy:'创建人',
        },
        name:'分组名称',
        createBy:'创建人',

      }
    },
  }
</script>

<style scoped>

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
