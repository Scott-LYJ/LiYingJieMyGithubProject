<template>
<div>
<el-container>
  <el-header>
    <div style="margin-top: 20px">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item><i class="el-icon-tickets"></i>接口信息</el-breadcrumb-item>
    </el-breadcrumb>
    </div>
  </el-header>
  <el-main>
    <el-form :model="apiDetails" :rules="rules" ref="apiDetails" label-width="100px" class="demo-apiDetails">
    <el-form-item label="接口名称" prop="name">
      <el-input v-model="apiDetails.name"></el-input>
    </el-form-item>
      <el-form-item label="接口创建者" prop="name" >
        <el-input v-model="apiDetails.createBy" style="width: 100px;float: left"></el-input>
        <span style="float: left">&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <el-tag type="success" style="float: left;margin-top: 4px"  effect="dark">开发者</el-tag>

      </el-form-item>
      <el-form-item label="zk地址" prop="name">
        <el-input v-model="apiDetails.zookeeper"></el-input>
      </el-form-item>
      <el-form-item label="服务名称" prop="name">
        <el-input v-model="apiDetails.serviceName"></el-input>
      </el-form-item>
      <el-form-item label="接口URL" prop="name">
        <el-input v-model="apiDetails.url"></el-input>
      </el-form-item>

    <el-form-item label="数据格式" prop="region">
      <el-select v-model="apiDetails.dataStyle" placeholder="请选择数据格式" style="float: left;width: 200px;">
        <el-option label="JSON" value="JSON"></el-option>
      </el-select>
    </el-form-item>
      <el-form-item label="编写日期" required>

          <el-form-item prop="date1">
            <el-date-picker type="date" placeholder="选择日期" v-model="apiDetails.cdt" style="width: 200px; float: left"></el-date-picker>
          </el-form-item>

      </el-form-item>
    <el-form-item label="接口描述" prop="desc">
      <el-input type="textarea" v-model="apiDetails.description"></el-input>
    </el-form-item>
  </el-form>
  </el-main>
</el-container>

<el-container>
  <el-footer>
    <div style="margin-top: 20px">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-tickets"></i>请求参数信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  </el-footer>
</el-container>

<el-container>
  <el-aside width="200px">参数信息</el-aside>
  <el-main>
    <div>
    <el-form ref="requestParamsForm" :model="requestParamsForm" label-width="80px" style="width: 100%">

      <el-button type="success" @click="AddListRow()" round style="float: left;" size="small">添加参数信息</el-button>
      <el-table :data="requestParamsForm.tableData" style="float: left;" size="mini"  highlight-current-row border  max-height="400"   class="el-tb-edit mgt20" ref="multipleTable" tooltip-effect="dark" >
        <el-table-column type="index" :index="indexMethod" >
        </el-table-column>

        <el-table-column  label="参数" width="100">
        <template slot-scope="scope">
          <!--<el-input v-model="scope.row.MROCardName ">-->

          <!--<i slot="suffix" @click="openCardCode('MROCardName','MROCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
          <!--</el-input>-->
          <el-input v-model="scope.row.param" auto-complete="off" ></el-input>

        </template>
      </el-table-column>
        <el-table-column  label="参数名称" width="100">
          <template slot-scope="scope">
            <!--<el-input v-model="scope.row.MROCardName ">-->

            <!--<i slot="suffix" @click="openCardCode('MROCardName','MROCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
            <!--</el-input>-->
            <el-input v-model="scope.row.name" auto-complete="off" ></el-input>

          </template>
        </el-table-column>
        <el-table-column label="参数类型" width="200">
          <template slot-scope="scope">
            <!-- {{ scope.row.MROAmount }}-->
            <!--<el-input v-model="scope.row.MROAmount" type="number"></el-input>-->
            <el-select v-model="scope.row.typeName" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>

          </template>
        </el-table-column>
        <el-table-column  label="是否必填" width="80">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.required"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="true"
              inactive-value="false">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column  label="最大长度" width="200">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.maxLength" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>

          </template>
        </el-table-column>
        <el-table-column label="示例值" width="320">
          <template slot-scope="scope">
            <!--<el-input v-model="scope.row.DZCardName">-->
            <!--<i slot="suffix" @click="openCardCode('DZCardName','DZCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
            <!--</el-input>-->
            <el-input type="textarea" v-model="scope.row.example"></el-input>

          </template>
        </el-table-column>
        <el-table-column label="参数描述" width="320">
          <template slot-scope="scope">
            <!--   {{ scope.row.DZAmount }}-->
            <!--<el-input v-model="scope.row.DZAmount"  type="number"></el-input>-->
            <el-input type="textarea" v-model="scope.row.description"></el-input>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="100"  fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    </div>
  </el-main>
</el-container>

<el-container>
  <el-header>
    <div style="margin-top: 20px">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-tickets"></i>响应参数信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  </el-header>
  <el-container>
    <el-aside width="200px">参数信息</el-aside>
    <el-main>
      <div>
      <el-form ref="responseParamsForm" :model="responseParamsForm" label-width="80px" style="width: 100%">

        <el-button type="success" @click="AddListRow2()" round style="float: left;" size="small">添加参数信息</el-button>
        <el-table :data="responseParamsForm.tableData" style="float: left;" size="mini"  highlight-current-row border  max-height="400"   class="el-tb-edit mgt20" ref="multipleTable" tooltip-effect="dark" >
          <el-table-column type="index" :index="indexMethod2" >
          </el-table-column>

          <el-table-column  label="参数" width="100">
            <template slot-scope="scope">
              <!--<el-input v-model="scope.row.MROCardName ">-->

              <!--<i slot="suffix" @click="openCardCode('MROCardName','MROCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
              <!--</el-input>-->
              <el-input v-model="scope.row.param" auto-complete="off" ></el-input>

            </template>
          </el-table-column>
          <el-table-column  label="参数名称" width="100">
            <template slot-scope="scope">
              <!--<el-input v-model="scope.row.MROCardName ">-->

              <!--<i slot="suffix" @click="openCardCode('MROCardName','MROCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
              <!--</el-input>-->
              <el-input v-model="scope.row.name" auto-complete="off" ></el-input>

            </template>
          </el-table-column>
          <el-table-column label="参数类型" width="200">
            <template slot-scope="scope">
              <!-- {{ scope.row.MROAmount }}-->
              <!--<el-input v-model="scope.row.MROAmount" type="number"></el-input>-->
              <el-select v-model="scope.row.typeName" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>

            </template>
          </el-table-column>
          <el-table-column  label="是否必填" width="80">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.required"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="true"
                inactive-value="false">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column  label="最大长度" width="200">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.maxLength" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>

            </template>
          </el-table-column>
          <el-table-column label="示例值" width="320">
            <template slot-scope="scope">
              <!--<el-input v-model="scope.row.DZCardName">-->
              <!--<i slot="suffix" @click="openCardCode('DZCardName','DZCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
              <!--</el-input>-->
              <el-input type="textarea" v-model="scope.row.example"></el-input>

            </template>
          </el-table-column>
          <el-table-column label="参数描述" width="320">
            <template slot-scope="scope">
              <!--   {{ scope.row.DZAmount }}-->
              <!--<el-input v-model="scope.row.DZAmount"  type="number"></el-input>-->
              <el-input type="textarea" v-model="scope.row.description" ></el-input>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="100"  fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" type="danger" @click="handleDelete2(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </div>
    </el-main>
  </el-container>
</el-container>

<el-container>
    <el-header>
      <div style="margin-top: 20px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item><i class="el-icon-tickets"></i>响应码信息</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">响应码列表</el-aside>
      <el-main>
        <div>
          <el-form ref="responseCodesForm" :model="responseCodesForm" label-width="80px" style="width: 100%">

            <el-button type="success" @click="AddListRow3()" round style="float: left;" size="small">添加响应吗信息</el-button>
            <el-table :data="responseCodesForm.tableData" style="float: left;" size="mini"  highlight-current-row border  max-height="400"   class="el-tb-edit mgt20" ref="multipleTable" tooltip-effect="dark" >
              <el-table-column type="index" :index="indexMethod3" >
              </el-table-column>

              <el-table-column  label="响应码" width="100">
                <template slot-scope="scope">
                  <!--<el-input v-model="scope.row.MROCardName ">-->

                  <!--<i slot="suffix" @click="openCardCode('MROCardName','MROCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
                  <!--</el-input>-->
                  <el-input v-model="scope.row.code" auto-complete="off" ></el-input>

                </template>
              </el-table-column>

              <el-table-column label="响应码描述" width="320">
                <template slot-scope="scope">
                  <!--<el-input v-model="scope.row.DZCardName">-->
                  <!--<i slot="suffix" @click="openCardCode('DZCardName','DZCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
                  <!--</el-input>-->
                  <el-input type="textarea" v-model="scope.row.description"></el-input>

                </template>
              </el-table-column>
              <el-table-column label="原因" width="320">
                <template slot-scope="scope">
                  <!--   {{ scope.row.DZAmount }}-->
                  <!--<el-input v-model="scope.row.DZAmount"  type="number"></el-input>-->
                  <el-input type="textarea" v-model="scope.row.reason"></el-input>
                </template>
              </el-table-column>
              <el-table-column label="解决方案" width="320">
                <template slot-scope="scope">
                  <!--   {{ scope.row.DZAmount }}-->
                  <!--<el-input v-model="scope.row.DZAmount"  type="number"></el-input>-->
                  <el-input type="textarea" v-model="scope.row.solution"></el-input>
                </template>
              </el-table-column>

              <el-table-column label="操作" width="100"  fixed="right">
                <template slot-scope="scope">
                  <el-button size="mini" type="danger" @click="handleDelete3(scope.$index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </el-container>

<el-container>
  <el-header>
    <div style="margin-top: 20px">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-tickets"></i>请求示例</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  </el-header>
  <el-container>
    <el-aside width="200px">上送信息</el-aside>
    <el-container>
      <el-main>
        <div>
          <el-card style="height: 500px;">
            <quill-editor v-model="upContent" ref="myQuillEditor" style="height: 200px;" :options="upEditorOption">
            </quill-editor>
          </el-card>
        </div>
      </el-main>
    </el-container>
  </el-container>
</el-container>

<el-container>
  <el-aside width="200px">下送信息</el-aside>
  <el-container>
    <el-header>建议：示例信息从测试界面复制</el-header>
    <el-main>
      <div>
        <el-card style="height: 500px;">
          <quill-editor v-model="downContent" ref="myQuillEditor" style="height: 200px;" :options="downEditorOption">
          </quill-editor>
        </el-card>
      </div>
    </el-main>
  </el-container>
</el-container>

<el-container>
    <el-header>
      <div style="margin-top: 20px">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item><i class="el-icon-tickets"></i>其他功能</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </el-header>
    <el-main>
      <el-form :label-position="labelPosition" label-width="80px" :model="otherFunction" :inline="true">
        <el-form-item label="是否评论">
          <el-switch
            v-model="otherFunction.discuss"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="true"
            inactive-value="false">
          </el-switch>
        </el-form-item>
        <el-form-item label="是否可赞">
          <el-switch
            v-model="otherFunction.thumbsUp"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="true"
            inactive-value="false">
          </el-switch>
        </el-form-item>
        <el-form-item label="是否可踩">
          <el-switch
            v-model="otherFunction.thumbsDown"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="true"
            inactive-value="false">
          </el-switch>
        </el-form-item>


        <el-form-item label="" style="float: right">
          <el-button type="primary" round @click="onSave">保存</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>

</div>
</template>
<style>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  /*.el-main {*/
    /*background-color: #E9EEF3;*/
    /*color: #333;*/
    /*!*text-align: center;*!*/
    /*line-height: 160px!important;*/
  /*}*/
  .el-main {
    background-color: #e9eef3;
    color: #333;
    /* text-align: center; */
    line-height: 20px !important;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>

<script>
  import {
    quillEditor
  } from 'vue-quill-editor'
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import 'quill/dist/quill.bubble.css'

  export default {
    name: "apiDocumentDetail",
    components: {
      quillEditor
    },
    data() {
      return {
        apiDocument:{
          _id:'',
          sid:'',
          apiDetails:null,
          otherFunction:null,
          upContent:'',
          downContent:'',
          requestParamList:null,
          responseParamList:null,
          responseCodeList:null,
        },
        //
        otherFunction:{
          discuss:'',
          thumbsUp:'',
          thumbsDown:'',
        },
        //
        apiDetails:{
            id:'',
            name:'',
            createBy:'',
            zookeeper:'',
            cdt:'',
            serviceName:'',
            url:'',
            dataStyle:'',
            description:'',
        },
        //
        comments:[{
          reply:[

      ],
        }
      ],
        //
        requestParamsForm:{
          tableData:[],
        },
        //
        responseParamsForm:{
          tableData:[],
        },
        responseCodesForm:{
          tableData:[],
        },
        labelPosition: 'right',
        //
        upContent: null,
        upEditorOption: {},
        //
        downContent: null,
        downEditorOption: {},
        //
        num: 1,
        //
        value: '100',
        //
        options: [{
          value: 'java.lang.Integer',
          label: 'java.lang.Integer'
        }, {
          value: 'java.lang.String',
          label: 'java.lang.String'
        }, {
          value: 'java.util.Map',
          label: 'java.util.Map'
        }, {
          value: 'java.lang,Long',
          label: 'java.lang,Long'
        }, {
          value: 'java.lang.Float',
          label: 'java.lang.Float'
        }],
        //
        rules: {
          name: [
            { required: true, message: '请输入接口名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          region: [
            { required: true, message: '请选择活动区域', trigger: 'blur' }
          ],
          date1: [
            {  required: true, message: '请选择日期', trigger: 'blur' }
          ],
          dataStyle: [
            { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
          ],
          description: [
            { required: true, message: '请输入接口描述', trigger: 'change' }
          ],
          serviceName: [
            { required: true, message: '请数据接口全路径', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      //
      handleChange(value) {
        console.log(value);
      },
      //
      //table序号
      indexMethod(index) {
        return index + 1;
      },
      indexMethod2(index) {
        return index + 1;
      },
      indexMethod3(index) {
        return index + 1;
      },
      //
      AddListRow() {
        this.requestParamsForm.tableData.push({
          "param": "",
          "name": "",
          "typeName": "",
          "required": "",
          "maxLength": "",
          "example": "",
          "description": "",
        });
      },
      AddListRow2() {
        this.responseParamsForm.tableData.push({
          "param": "",
          "name": "",
          "typeName": "",
          "required": "",
          "maxLength": "",
          "example": "",
          "description": "",
        });
      },
      AddListRow3() {
        this.responseCodesForm.tableData.push({
          "code": "",
          "description": "",
          "reason": "",
          "solution": "",
        });
      },

      //
      handleDelete(index) {
        this.requestParamsForm.tableData.splice(index, 1);
      },
      handleDelete2(index) {
        this.responseParamsForm.tableData.splice(index, 1);
      },
      handleDelete3(index) {
        this.responseCodesForm.tableData.splice(index, 1);
      },
      //
      onSave() {
        var _this = this
        this.apiDocument.sid = this.apiDetails.id
        // this.apiDocument.sid=this.apiDetails.id;
        // this.apiDocument.upContent = this.upContent;
        // this.apiDocument.downContent=this.downContent;
        // this.apiDocument.apiDetails=this.apiDetails;
        // this.apiDocument.apiDetails.id=1
        // this.apiDocument.otherFunction=this.otherFunction;
        // this.apiDocument.requestParamList=this.requestParamsForm.tableData;
        // this.apiDocument.responseParamList =this.responseParamsForm.tableData;
        // this.apiDocument.responseCodeList=this.responseCodesForm.tableData;
        //
        // console.log( this.apiDocument)
        let param = {
          sid: this.apiDetails.id,
          upContent: this.upContent,
          downContent: this.downContent,
          apiDetails: this.apiDetails,
          otherFunction: this.otherFunction,
          requestParamList: this.requestParamsForm.tableData,
          responseParamList: this.responseParamsForm.tableData,
          responseCodeList: this.responseCodesForm.tableData,
          comments: this.comments,
        }
        // let param={
        //   apiDocument:this.apiDocument
        // }

        var apiDocument = JSON.stringify(this.apiDocument);
        //ajax请求
        this.$ajax({
          method: "post",
          url: "/test/apiDocument/save/" + this.apiDocument.sid,
          data: param,
        }).then((resultData)=> {
          console.log(resultData)
          //
          let levList = this.$common.getSessionStorage('lev', true);
          let roleId = levList[0].id;
          let param = {
            userId: this.$common.getSessionStorage("id"),
            roleId: roleId,
            message: '创建了一个文档(' + this.apiDetails.name + ')',
            isRead: 0
          }
          console.log(param)
          this.$ajax({
            method: "post",
            url: "/message/send?userId=" + this.$common.getSessionStorage("id"),
            data: param
          }).then(function (resultData) {

          });


        });
        // this.$ajax({
        //   method: "get",
        //   url: "/test/apiDocument/findAll",
        // }).then(function(resultData) {
        //   console.log(resultData)
        // });
        //
        this.$notify({
          title: '提示',
          message: '接口信息保存成功',
          type: 'success'
        });
        this.$router.go(-1)
        // console.log('save!');
        // //创建接口
        // this.$ajax({
        //   method: "post",
        //   url: "/interface/createrInterface/insertInterface",
        //   data: this.submitInformation
        // }).then((res) => {
        //   console.log(res)
        //   this.submitInformation.id = res.data.data.id;
        //   this.dialogVisible = true;
        //   //
        //   let levList = this.$common.getSessionStorage('lev', true);
        //   let roleId = levList[0].id;
        //   let param = {
        //     userId: this.$common.getSessionStorage("id"),
        //     roleId: roleId,
        //     message: '创建了一个文档(' + this.apiDetails.name + ')',
        //     isRead: 0
        //   }
        //   this.$ajax({
        //     method: "post",
        //     url: "/message/send?userId=" + this.$common.getSessionStorage("id"),
        //     data: param
        //   }).then(function (resultData) {
        //
        //   });
        // })


      },
    },
    mounted(){
      this.apiDetails=this.$route.query.apiDetails;

    },
  }
</script>

