<template>
     <section>
        <div class="table"> <!-- 页面表格begin -->
          <div class="crumbs"> <!-- 页面标题begin -->
              <el-breadcrumb separator="/">
                  <el-breadcrumb-item><i class="el-icon-tickets"></i>分组管理</el-breadcrumb-item>
              </el-breadcrumb>
          </div> <!-- 页面标题end-->
          <div class="container"><!-- 页面内容区begin -->
             <div class="handle-box"> <!-- 搜索区begin -->
  <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                分组名称:<el-input v-model="filters.name"  placeholder="分组名称" style="width:200px; heght:30px;" size="mini"></el-input>
                </el-form-item>
                 <el-form-item>
               分组编号:<el-input v-model="filters.id"   placeholder="分组编号" style="width:200px; heght:30px;" size="mini"></el-input>
                </el-form-item>
                 <el-form-item>
                 分组创建人:<el-input v-model="filters.createBy"  placeholder="分组创建人" style="width:200px; heght:30px;" size="mini"></el-input>
                </el-form-item>
                <br>
                 <el-form-item>
                  分组修改人:<el-input v-model="filters.updateBy"  placeholder="分组修改人" style="width:200px; heght:30px;" size="mini"></el-input>
                </el-form-item>
                <el-form-item>
                   <el-button type="primary" icon="el-icon-search" @click="getResult(1)" size="mini">搜索</el-button>
                </el-form-item>
            </el-form>
        </el-col>
         </div> <!-- 搜索区end -->
          <!--新增按钮-->
            <el-button type="success" icon="el-icon-circle-plus-outline" @click="handleAdd" size="mini" round>{{$t("SysUser.add")}}</el-button>
            <el-button type="danger" icon="el-icon-delete" @click="handleDeleteList" size="mini" round>{{$t("SysUser.delete")}}</el-button>
            <template>
        <!--表格数据及操作-->
        <el-table :data="tableData" size="mini"  highlight-current-row border   class="el-tb-edit mgt20" ref="multipleTable" tooltip-effect="dark" v-loading="listLoading" @selection-change="selectChange">
          <!--//展开行-->
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left"  class="demo-table-expand" :inline="true">
                <el-form-item label="分组名称">
                  <span>{{ props.row.name }}</span>
                </el-form-item>
                <el-form-item label="分组id">
                  <span>{{ props.row.id }}</span>
                </el-form-item>
                <el-form-item label="创建人">
                  <span>{{ props.row.createBy }}</span>
                </el-form-item>
                <el-form-item label="创建时间">
                  <span>{{ props.row.cdt }}</span>
                </el-form-item>
                <el-form-item label="修改人">
                  <span>{{ props.row.updateBy }}</span>
                </el-form-item>
                <el-form-item label="修改时间">
                  <span>{{ props.row.udt }}</span>
                </el-form-item>
                <el-form-item label="分组状态">
                  <span>{{ props.row.status?'启用':'禁止' }}</span>
                </el-form-item>
                <el-form-item label="商品描述">
                  <span>{{ props.row.description }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
            <!--勾选框-->
            <el-table-column type="selection" width="55">
            </el-table-column>

            <!--索引-->
            <el-table-column type="index" :index="indexMethod">
            </el-table-column>
            <el-table-column prop="name" label="分组名称">
               <template slot-scope="scope">
              <el-button @click="" type="text" size="small">{{scope.row.name}}</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="id" label="编号" >
            </el-table-column>
             <el-table-column prop="description" label="描述" >
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
             <el-table-column prop="version" label="版本" >
            </el-table-column>
              <el-table-column  fixed="right" label="操作"min-width="200">
                 <template slot-scope="scope">
                   <el-button size="mini" plain type="primary" @click="toApi( scope.row)">接口</el-button>
                   <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                   <el-button size="mini" plain type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
     <!--新增界面-->
		<el-dialog :title="$t('SysUser.add')" :visible.sync="addFormVisible" :close-on-click-modal="false">
			<el-form :inline="true" :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="分组名称" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
        <br/>
        <el-form-item label="是否启用" prop="">
          <el-radio v-model="addForm.status" label="1" border>启用</el-radio>
          <el-radio v-model="addForm.status" label="0" border>禁用</el-radio>
				</el-form-item>
        <br/>

        <el-form-item label="版本" prop="">
          <el-select v-model="addForm.version" placeholder="请选择">
            <el-option
              v-for="item in versions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
      </el-form-item>
        <br/>
        <el-form-item label="分组描述" prop="description">
          <el-input type="textarea" v-model="addForm.description" ></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="addCancle">{{$t("SysUser.cancle")}}</el-button>
				<el-button type="primary" @click="addSubmit" :loading="addLoading">{{$t("SysUser.submit")}}</el-button>
			</div>
		</el-dialog>
     <!--编辑界面-->
		<el-dialog :title="$t('SysUser.edit')" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :inline="true" :model="editForm" label-width="80px"  ref="editForm">
        <el-form-item label="分组名称" >
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <br/>
        <el-form-item label="是否启用" >
          <el-radio v-model="editForm.status" label="1" border>启用</el-radio>
          <el-radio v-model="editForm.status" label="0" border >禁用</el-radio>
        </el-form-item>
        <br/>

        <el-form-item label="版本" >
          <el-select v-model="editForm.version" placeholder="请选择">
            <el-option
              v-for="item in versions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <br/>
        <el-form-item label="分组描述">
          <el-input type="textarea" v-model="editForm.description" ></el-input>
        </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="editCancle">{{$t('SysUser.cancle')}}</el-button>
				<el-button type="primary" @click="editSubmit" :loading="editLoading">{{$t('SysUser.submit')}}</el-button>
			</div>
		</el-dialog>

  </section>
 </template>

 <script>

   export default {
   name: 'GatewayApiGroup',

  data() {
    return {
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
      pageSize: 10,
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

    };
  },
  methods: {
     //查看接口
    //查看分组和接口
    toApi(row){
      var _this = this

      _this.$router.push({path: 'api', query: {group: row}})
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
    getResult: function(val) {
      var _this = this;
      this.listLoading = true;
      let param = Object.assign(
        {},
        {
          skip: (val-1)*10,
          size: 10,
          name: this.filters.name,
          id: this.filters.id,
          createBy: this.filters.createBy,
          updateBy: this.filters.updateBy
        }
      );
      //delSysUserByUserId,querySysUserList
      this.$ajax({
        method: "post",
        url: "/group/querySysGroupList",
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
    //显示新增界面
    handleAdd: function() {
      this.addFormVisible = true;
    },
    //新增
    addSubmit: function() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          if (this.addForm.name == "") {
            this.$message({
              message: "请填写分组名",
              type: "error"
            });
            return;
          }
          if (this.addForm.description == "") {
            this.$message({
              message: "请填写分组描述",
              type: "error"
            });
            return;
          }
          if (this.addForm.version == "") {
            this.$message({
              message: "请选择版本",
              type: "error"
            });
            return;
          }
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.addLoading = true;
            let param = Object.assign({}, {
              name:this.addForm.name,
              version:this.addForm.version,
              status: this.addForm.status,
              description: this.addForm.description,
              createBy:this.$common.getSessionStorage("username")
            });
            console.log('param',param);
            this.$ajax({
              method: "post",
              url: "/group/addSubmit",
              data: param
            }).then(res => {
              this.addLoading = false;
              this.$message({
                message: "提交成功",
                type: "success"
              });
              this.$refs["addForm"].resetFields();
              this.addFormVisible = false;
              this.getResult(1);
            });
          });
        }
      });
    },

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
            url: "/group/delSysGroupByIds",
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
            url: "/group/delSysGroupByIds",
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
      this.editForm = Object.assign({}, row);
      console.log(this.editForm)
    },
    //编辑
    editSubmit: function() {
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.editLoading = true;
            this.editForm.updateBy=this.$common.getSessionStorage("username")
            let param = Object.assign({}, this.editForm);
            this.$ajax({
              method: "post",
              url: "/group/updateGroup",
              data: param
            }).then(res => {
              this.editLoading = false;
              this.$message({
                message: "提交成功",
                type: "success"
              });
              this.$refs["editForm"].resetFields();
              this.editFormVisible = false;
              this.getResult(1);
            });
          });
        }
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
