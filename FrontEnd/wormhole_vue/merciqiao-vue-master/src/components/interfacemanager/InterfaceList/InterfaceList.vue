<template>
  <section>
    <div class="table"> <!-- 页面表格begin -->
      <div class="crumbs"> <!-- 页面标题begin -->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item><i class="el-icon-tickets"></i>接口列表</el-breadcrumb-item>
        </el-breadcrumb>
      </div> <!-- 页面标题end-->
      <div class="container"><!-- 页面内容区begin -->
        <div class="handle-box"> <!-- 搜索区begin -->
          <!--工具条-->
          <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
              <el-form-item>
                API名称:<el-input v-model="filters.name"  placeholder="API名称" style="width:200px; heght:30px;" size="mini"></el-input>
              </el-form-item>
              <el-form-item>
                API编号:<el-input v-model="filters.id"   placeholder="API编号" style="width:200px; heght:30px;" size="mini"></el-input>
              </el-form-item>
              <el-form-item>
                dubbo服务名:<el-input v-model="filters.serviceName"  placeholder="dubbo服务名" style="width:200px; heght:30px;" size="mini"></el-input>
              </el-form-item>
              <br>
              <el-form-item>
              dubbo方法名:<el-input v-model="filters.methodName"  placeholder="dubbo方法名" style="width:200px; heght:30px;" size="mini"></el-input>
            </el-form-item>
              <el-form-item label="接口分组:">

              <el-select v-model="groupId" placeholder="请选择" size="mini">
                <el-option
                  v-for="item in groupIds"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="getResult(1)" size="mini">搜索</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </div> <!-- 搜索区end -->
        <!--删除按钮-->
        <el-button type="danger" icon="el-icon-delete" @click="handleDeleteList" size="mini" round v-has="'/interface/deleteList'">{{$t("SysUser.delete")}}</el-button>
        <template>
          <!--表格数据及操作-->
          <el-table   :data="tableData" size="mini"  highlight-current-row border   class="el-tb-edit mgt20" ref="multipleTable" tooltip-effect="dark" v-loading="listLoading" @selection-change="selectChange">
            <!--//展开行-->
            <el-table-column type="expand"  >
              <template slot-scope="props">

                <el-tabs type="border-card" style="width:1000px;height: 300px"  @tab-click="handleClick1($event,props.row.id)">
                  <el-tab-pane label="接口基本信息">
                    <el-form label-position="left"  class="demo-table-expand" :inline="true">
                      <el-form-item label="API名称">
                        <span>{{ props.row.name }}</span>
                      </el-form-item>
                      <el-form-item label="APIid">
                        <span>{{ props.row.id }}</span>
                      </el-form-item>
                      <el-form-item label="版本">
                        <span>{{ props.row.authVersion }}</span>
                      </el-form-item>
                      <el-form-item label="隶属分组">
                        <span>{{ props.row.groupName }}</span>
                      </el-form-item>
                      <el-form-item label="接口状态">
                        <span>{{ props.row.status?'启用':'禁止' }}</span>
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        <span>{{ props.row.isAuth?'需要鉴权':'不需要鉴权' }}</span>
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        <span>{{ props.row.isLogin?'需要登录':'不需要登录 ' }}</span>
                      </el-form-item>
                      <el-form-item label="接口描述">
                        <span>{{ props.row.description }}</span>
                      </el-form-item>
                    </el-form>
                  </el-tab-pane>
                  <el-tab-pane label="dubbo相关信息">
                    <el-form label-position="left"  class="demo-table-expand" :inline="true">
                      <el-form-item label="ZK地址">
                        <span>{{ props.row.zookeeper }}</span>
                      </el-form-item>
                      <el-form-item label="服务名称">
                        <span>{{ props.row.serviceName }}</span>
                      </el-form-item>
                      <el-form-item label="接口名称">
                        <span>{{ props.row.interfaceName }}</span>
                      </el-form-item>
                      <el-form-item label="方法名称">
                        <span>{{ props.row.serviceMethod }}</span>
                      </el-form-item>

                    </el-form>
                  </el-tab-pane>
                  <el-tab-pane label="请求参数信息">
                    <el-table
                      :data="props.row.gatewayServiceRequestDOS"
                      border
                      style="width: 550px">
                      <el-table-column prop="paramsIndex" label="参数位置" style="width: 50px;">
                      </el-table-column>
                      <el-table-column prop="typeName" label="参数类型" style="width: 100px;">
                      </el-table-column>
                      <el-table-column prop="example" label="参数示例" style="width: 200px;">
                      </el-table-column>
                      <el-table-column prop="description" label="参数描述" style="width: 200px;">
                      </el-table-column>
                    </el-table>
                  </el-tab-pane>
                  <el-tab-pane label="接口文档信息">
                  </el-tab-pane>
                </el-tabs>

              </template>
            </el-table-column>
            <!--勾选框-->
            <el-table-column type="selection" width="55">
            </el-table-column>

            <!--索引-->
            <el-table-column type="index" :index="indexMethod">
            </el-table-column>
            <el-table-column prop="name" label="API名称">
              <template slot-scope="scope">
                <el-button @click="" type="text" size="small">{{scope.row.name}}</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="id" label="API编号" >
            </el-table-column>
            <el-table-column prop="description" label="API描述" :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column prop="status" label="状态"
                             :filters="[{ text: '启用', value: '启用' }, { text: '禁用', value: '禁用' }]"
                             :filter-method="filterTag"
                             filter-placement="bottom-end">
              <template slot-scope="scope">
                <el-tag
                  :type="(scope.row.status?'启用':'禁用') === '启用' ? 'success' : 'danger'"
                  disable-transitions>{{scope.row.status?'启用':'禁用'}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="serviceVersion" label="服务版本" >
            </el-table-column>
            <el-table-column prop="groupName" label="隶属分组" >
            </el-table-column>

            <el-table-column  fixed="right" label="操作"min-width="200">
              <template slot-scope="scope">
                <el-button size="mini" plain type="primary" @click="toTest( scope.row)" v-has="'/interface/show'">测试</el-button>
                <el-button size="mini" @click="handleEdit(scope.$index, scope.row)" v-has="'/interface/update'">编辑</el-button>
                <el-button size="mini" plain type="danger" @click="handleDelete(scope.$index, scope.row)" v-has="'/interface/delete'">删除</el-button>
              </template>
            </el-table-column>

          </el-table>
        </template>
        <br>
        <br>
        <!-- 分页 -->
        <el-pagination @current-change="getResult"  :current-page="currentPage" :page-size="pageSize" layout="total, prev, pager, next" :total="count" >
        </el-pagination>
      </div><!-- 页面内容区end-->
    </div><!-- 页面表格end -->
    <!--编辑界面-->
    <el-dialog :title="$t('SysUser.edit')" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="接口基本信息修改" name="first">
          <el-form ref="apiEdit" :model="apiEdit" label-width="80px">
            <el-form-item label="API名称">
              <el-input v-model="apiEdit.name"></el-input>
            </el-form-item>
            <el-form-item label="API编号">
              <el-input v-model="apiEdit.id" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="版本">
              <el-input-number v-model="apiEdit.authVersion" @change="handleChange" :min="1"  label="描述文字"></el-input-number>
            </el-form-item>
            <el-form-item label="分组名称">
              <el-select v-model="apiEdit.groupId" placeholder="请选择" >
                <el-option
                  v-for="item in groupIds"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="接口状态">
              <el-switch
              v-model="apiEdit.status"
              :active-value="1"
              :inactive-value="0"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用">
            </el-switch>
              &nbsp;
              &nbsp;
              &nbsp;
              &nbsp;
              <el-switch
                v-model="apiEdit.isAuth"
                :active-value="1"
                :inactive-value="0"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="鉴权"
                inactive-text="非鉴权">
              </el-switch>
              &nbsp;
              &nbsp;
              &nbsp;
              &nbsp;
              <el-switch
                v-model="apiEdit.isLogin"
                :active-value="1"
                :inactive-value="0"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="登录"
                inactive-text="非登录">
              </el-switch>
              &nbsp;
              &nbsp;
              &nbsp;
              &nbsp;
            </el-form-item>
            <el-form-item label="接口描述">
              <el-input type="textarea" v-model="apiEdit.description"></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="dubbo相关信息修改" name="second">
          <el-form ref="apiEdit" :model="apiEdit" label-width="80px">
            <el-form-item label="ZK地址">
              <el-input v-model="apiEdit.zookeeper"></el-input>
            </el-form-item>
            <el-form-item label="服务名称">
              <el-input v-model="apiEdit.serviceName" ></el-input>
            </el-form-item>
            <el-form-item label="接口名称">
              <el-input v-model="apiEdit.interfaceName" ></el-input>
            </el-form-item>
            <el-form-item label="方法名称">
              <el-input v-model="apiEdit.serviceMethod" ></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="请求参数信息修改" name="third">
          <el-table :data="requestParamsForm.tableData" size="mini"  highlight-current-row border  max-height="400"   class="el-tb-edit mgt20" ref="multipleTable" tooltip-effect="dark" >
            <el-table-column type="index" :index="indexMethod" >
            </el-table-column>

            <el-table-column  label="参数名称" width="200">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" auto-complete="off" ></el-input>
              </template>
            </el-table-column>
            <el-table-column label="参数类型" width="200">
              <template slot-scope="scope">
                <el-select v-model="scope.row.typeName" placeholder="请选择" change="selectType">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>

              </template>
            </el-table-column>
            <el-table-column label="请求示例" width="320">
              <template slot-scope="scope">
                <el-input type="textarea" v-model="scope.row.example"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="参数描述" width="320">
              <template slot-scope="scope">
                <el-input type="textarea" v-model="scope.row.description"></el-input>
              </template>
            </el-table-column>

          </el-table>
        </el-tab-pane>
        <el-tab-pane label="接口文档编辑" name="fourth" v-has="'/interface/documentEdit'">
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editCancle">{{$t('SysUser.cancle')}}</el-button>
        <el-button type="primary" @click="editSubmit" :loading="editLoading">{{$t('SysUser.submit')}}</el-button>
      </div>
    </el-dialog>

  </section>
</template>

<script>

  export default {
    name: 'Interfacelist',

    data() {
      return {
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
        requestParamsForm: {
          tableData:[],
          size:'',
        },
        apiEdit:{
            name: '',
            id: '',
            authVersion: '',
            description: '',
            isRequest: '',
            groupId: '',
            zookeeper:"",
            serviceName:"",
            interfaceName:'',
            serviceMethod:'',
            status:'',
        },
        activeName: 'first',
        //
        groupIds: '',
        groupId: '',
        //

        versions: [{
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }],


        selectedOptions: [],

        //搜索区域参数
        filters: {

        },
        //table返回的数据
        tableData: [],
        //列表Loading加载
        listLoading: false,
        //批量选中data
        selectList: [],
        //分页
        count: 0,
        currentPage: 1,
        pageSize: 5,
        //新增界面是否显示
        addFormVisible: false,
        //添加按钮Loading加载
        addLoading: false,
        //输入框验证
        addFormRules: {
          name: [
            { required: true, message: "请输入登录名", trigger: "blur" }
          ],
          description: [
            { required: true, message: "请输入登录密码", trigger: "blur" }
          ],
        },
        //新增界面数据
        addForm: {
          id: "",
          name: "",
          description: "",
          version:"",
          status: "",
        },
        //编辑界面是否显示
        editFormVisible: false,
        //编辑按钮Loading加载
        editLoading: false,
        //编辑界面数据
        editForm: {
          id: "",
          name: "",
          status:"",
          version:"",
          description:"",
          updateBy:this.$common.getSessionStorage("username"),
        },
        toTestForm:{
          zookeeper:'',
          id:'',
          groupId:'',
          serviceVersion:'',
          serviceName:'',
          interfaceName:'',
          serviceMethod:'',
        },
      };
    },
    methods: {
      toShowApiDocument(sid){
        console.log(sid)
        this.$router.push({
          path: '/apiDocumentDetail2',
          query:{
            sid:sid,
          }

        });
      },
      toEditApiDocument(){
        console.log( this.apiEdit)
        let apiDetails={
          id:this.apiEdit.id,
          name:this.apiEdit.name,
          createBy:this.apiEdit.createBy,
          zookeeper:this.apiEdit.zookeeper,
          cdt:this.apiEdit.cdt,
          serviceName:this.apiEdit.serviceName+"/"+this.apiEdit.interfaceName+"/"+this.apiEdit.serviceMethod,
          url:"",
          desctiption:this.apiEdit.description,
        }
        console.log(apiDetails)
        this.$router.push({
          path: '/apiDocumentDetail',
          query:{
            apiDetails:apiDetails,
          }

        });

      },
      handleChange(value) {
        console.log(value);
      },
      onSubmit() {
        console.log('submit!');
      },
      handleClick(tab, event) {
        if (tab.index==3){
          this.editFormVisible=false
          this.toEditApiDocument();
        }
        console.log(tab.index);
      },
      handleClick1(tab, event,sid) {
        if (tab.index==3){
          this.toShowApiDocument(event);
        }

      },
      //查看接口
      //查看分组和接口
      toTest(row){
        const loading = this.$loading({
          lock: true,
          text: '正在创建或加载模板请稍后......',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });

        console.log(row)
        //
        this.toTestForm.zookeeper=row.zookeeper
        this.toTestForm.id=row.id
        this.toTestForm.groupId=row.groupId
        this.toTestForm.serviceVersion=row.serviceVersion
        this.toTestForm.serviceName=row.serviceName
        this.toTestForm.interfaceName=row.interfaceName
        this.toTestForm.serviceMethod=row.serviceMethod
        //
        var _this = this
        let param={
          zookeeper:row.zookeeper,
          groupId:row.groupId,
          createBy:row.createBy,
          serviceVersion:row.serviceVersion,
        }
        this.$ajax({
          method: "post",
          url: "/interface/zkConfig/createDubboModel",
          data: param
        }).then((resultData) =>{
          let param={
            zk:row.zookeeper,
            groupId:row.groupId,
            createBy:row.createBy,
            serviceVersion:row.serviceVersion,
            serviceName:row.serviceName,
            interfaceName:row.interfaceName,
            serviceMethod:row.serviceMethod,
            id:row.id,
          }
          this.$ajax({
            method: "post",
            url: "/interface/beforeTestInterface/createDubboModel",
            data: param
          }).then((resultData) =>{

            var _this = this
            console.log("大萨达撒多",_this.toTestForm)
            let index = this.toTestForm.interfaceName.lastIndexOf("/");
            let provider = this.toTestForm.interfaceName.substr(index+1)
            console.log("provider",provider)
            loading.close();
            this.$router.push({
              path: '/TestInterface',
              query:{zk: this.toTestForm.zookeeper,
                serviceName:this.toTestForm.serviceName,
                provider:provider,
                methodName:this.toTestForm.serviceMethod,
                id:this.toTestForm.id
              }

            });
          });
        });
       // _this.$router.push({path: 'api', query: {group: row}})
      },

      filterTag(value, row) {
        return (row.status?'启用':'禁用') === value;
      },

      editCancle(){
        this.editFormVisible = false
        this.$refs["editForm"].resetFields();
      },
      addCancle(){
        this.addFormVisible = false
        this.$refs["addForm"].resetFields();
      },


      //初始化分组信息
      getGroup:function(){
        var _this = this;
        let param = Object.assign(
          {},
        );

        this.$ajax({
          method: "post",
          url: "/group/querySysGroupList",
          data: param
        }).then(function(resultData) {

          console.log(resultData)
          let dataList =  resultData.data.data
          _this.groupIds=dataList
          console.log(_this.groupIds)
        });


      },



      //初始化信息
      getResult: function(val) {
        var _this = this;
        this.listLoading = true;
        let param = Object.assign(
          {},
          {
            skip: (val-1)*5,
            size: 5,
            name: this.filters.name,
            id: this.filters.id,
            serviceName: this.filters.serviceName,
            methodName: this.filters.methodName
          }
        );
        //delSysUserByUserId,querySysUserList
        this.$ajax({
          method: "post",
          url: "/interface/interfaceList/querySysInterfaceList",
          data: param
        }).then(function(resultData) {

          console.log(resultData)
          let dataList =  resultData.data.data
          _this.tableData = resultData.data.data;
          _this.count = resultData.data.count;
          _this.listLoading = false;
        });
      },
      //

      //批量删除
      handleDeleteList: function() {
        const length = this.selectList.length;
        let id = "";
        for (let i = 0; i < length; i++) {
          id += this.selectList[i].id + ",";
        }

        //去掉结尾,
        id = id.substring(0, id.length - 1);

        let param = Object.assign(
          {},
          {
            ids:id
          }
        );
        this.$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
          .then(() => {
            this.listLoading = true;
            // let param = new URLSearchParams();
            // param.append("ids", id);
            console.log(param);
            this.$ajax({
              method: "post",
              url: "/interface/interfaceList/delApiByIds",
              contentType: 'application/json; charset=UTF-8',// contn
              data: param
            }).then(res => {
              this.listLoading = false;
              this.$message({
                message: "删除成功"+id,
                type: "success"
              });
              this.selectList = [];
              this.getResult(1);
            });
          })
          .catch(() => {});
      },
      //单个删除
      handleDelete: function(index,row) {
        let id = row.id

        let param = Object.assign(
          {},
          {
            ids:id
          }
        );
        this.$confirm("确认删除该记录吗?", "提示", {
          type: "warning"
        })
          .then(() => {
            this.listLoading = true;
            // let param = new URLSearchParams();
            // param.append("ids", id);
            console.log(param);
            this.$ajax({
              method: "post",
              url: "/interface/interfaceList/delApiByIds",
              contentType: 'application/json; charset=UTF-8',// contn
              data: param
            }).then(res => {
              this.listLoading = false;
              this.$message({
                message: "删除成功"+id,
                type: "success"
              });
              this.selectList = [];
              this.getResult(1);
            });
          })
          .catch(() => {});
      },


      //显示编辑界面
      handleEdit: function(index, row) {
        this.editFormVisible = true;
        this.apiEdit = Object.assign({}, row);
        this.requestParamsForm.tableData =  row.gatewayServiceRequestDOS;
        console.log(this.apiEdit)
      },
      //编辑
      editSubmit: function() {

            this.$confirm("确认提交吗？", "提示", {}).then(() => {
              this.editLoading = true;
              // this.editForm.updateBy=this.$common.getSessionStorage("username")
               let param = Object.assign({}, this.apiEdit);
              for (var i=0;i<this.requestParamsForm.tableData.length;i++){
                var requestParam = this.requestParamsForm.tableData[0];
                console.log(requestParam.typeName)
                switch (requestParam.typeName){
                  case 'java.lang.Float':requestParam.type=5;break;
                  case 'java.lang.Integer':requestParam.type=2;break;
                  case 'java.lang.Long':requestParam.type=3;break;
                  case 'java.util.Map':requestParam.type=7;break;
                  case 'java.lang.String':requestParam.type=1;break;
                }
              }
               let param1 = Object.assign([], this.requestParamsForm.tableData);
               console.log(param1)
              // let param ={
              //   apiEdit:this.apiEdit,
              //   requestParams:this.requestParamsForm.tableData
              // }
              this.$ajax({
                method: "post",
                url: "/interface/interfaceList/updateApiById",
                data: param
              }).then(res => {
                this.$ajax({
                  method: "post",
                  url: "/interface/interfaceList/updateRequestById",
                  data: param1,
                }).then(res => {
                  this.editLoading = false;
                   this.$message({
                     message: "提交成功",
                     type: "success"
                   });
                   console.log("qqqqqqqqqqqqqqq")
                   // this.$refs.requestParamsForm.resetFields();
                   this.editFormVisible = false;
                   this.getResult(1);
                });

              });

        });
      },
      //批量选中
      selectChange: function(val) {
        this.selectList = val;
      },
      //table序号
      indexMethod(index) {
        return index + 1;
      },


    },
    mounted() {
      //初始加载
      this.getResult(1);
      //加载分组信息
      this.getGroup();
    }
  };
</script>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    width: 178px;
    height: 178px;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .user-avator{
    margin-left: 20px;
  }
  .user-avator img{
    display: block;
    width:60px;
    height:60px;
    border-radius: 50%;
  }
</style>
