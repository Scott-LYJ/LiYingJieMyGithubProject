<template>
    <div class="table">
        <!-- 页面表格begin -->
        <div class="crumbs">
            <!-- 页面标题begin -->
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-tickets"></i> 角色管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <!-- 页面标题end-->
        <div class="container">
            <!-- 页面内容区begin -->
            <div id="app">
                <div style=" width:55%;float:left;">
                    {{$t('SysRole.rolename')}}：
                    <el-input v-model="s_rolename" label="角色名称" :placeholder="$t('SysRole.rolename')" style="width:200px; heght:30px;" size="mini"></el-input>
                  <br/>
                  {{$t('SysRole.roleid')}}：
                    <el-input v-model="s_rolecode" label="角色编码" :placeholder="$t('SysRole.roleid')" style="width:200px; heght:30px;" size="mini"></el-input>
                  &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
                    <span style="float: right;">

                      <el-button type="success" icon="el-icon-search" @click="getResult(1)" size="mini">{{$t('SysRole.search')}}</el-button>

                    </span>
                    <br>
                    <br>

                    <!--新增按钮-->
                    <div class="clearfix">
                        <el-col :span="1" class="grid">
                            <el-button type="success" icon="el-icon-circle-plus-outline" @click="handleAdd" size="mini" round v-has="'/auth/role/add'">{{$t('SysRole.add')}}</el-button>
                        </el-col>
                    </div>

                    <!--表格数据及操作-->
                    <el-table :data="tableData" class="mgt20" border style="width: 100%" stripe ref="multipleTable" tooltip-effect="dark" @row-click="clickRow">
                        <!--勾选框-->
                        <el-table-column type="radio" style="width: 10%">
                              <template slot-scope="scope">
                                <el-radio v-model="curentroleid" :label="scope.row.id">{{null}}</el-radio>
                            </template>
                        </el-table-column>
                        <!--索引-->
                        <el-table-column type="index" style="width: 10%" :index="indexMethod">
                        </el-table-column>
                        <el-table-column prop="name" :label="$t('SysRole.rolename')" style="width: 20%" sortable>
                        </el-table-column>
                        <el-table-column prop="id" :label="$t('SysRole.id')" style="width: 20%">
                        </el-table-column>
                        <el-table-column :label="$t('SysRole.edit')"style="width: 20%" v-has="'/auth/role/update'">
                            <template slot-scope="scope">
                                <el-button type="primary" icon="el-icon-edit" size="mini" @click="enditRole(scope.row)" >{{$t('SysRole.edit')}}</el-button>
                            </template>
                        </el-table-column>
                        <el-table-column :label="$t('SysRole.delete')" style="width: 20%"  v-has="'/auth/role/delete'">
                            <template slot-scope="scope">
                                <el-button type="danger" icon="el-icon-delete" @click="delRole(scope.row)" size="mini">{{$t('SysRole.delete')}}</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <br>
                    <br>
                    <!--分页条total, sizes, prev, pager, next, jumper-->

                    <el-pagination @size-change="handleSizeChange" @current-change="getResult" :current-page="currentPage" :page-size="pageSize" layout="total, prev, pager, next" :total="roletotal">
                    </el-pagination>
                    <!--新增界面-->
                    <el-dialog :title="$t('SysRole.add')" :visible.sync="addFormVisible" :close-on-click-modal="false">
                        <el-form :model="addForm" :rules="addFormRules" ref="addForm" label-width="166px">
                            <el-form-item :label="$t('SysRole.rolename')" prop="name">
                                <el-input v-model="addForm.name" auto-complete="off" ></el-input>
                            </el-form-item>
                            <el-form-item :label="$t('SysRole.desc')" prop="des" >
                                <!--<el-input v-model="addForm.des" auto-complete="off" style="width:400px;"></el-input>-->
                              <el-input type="textarea" v-model="addForm.des" auto-complete="off" ></el-input>
                            </el-form-item>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="addFormVisible = false">{{$t('SysRole.cancle')}}</el-button>
                            <el-button type="primary" @click="addSubmit" :loading="addLoading">{{$t('SysRole.submit')}}</el-button>
                        </div>
                    </el-dialog>

                  <!--编辑界面-->
                  <el-dialog :title="$t('SysRole.edit')" :visible.sync="editFormVisible" :close-on-click-modal="true">
                    <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="166px">
                      <el-form-item :label="$t('SysRole.roleid')" prop="name">
                        <el-input v-model="editForm.id" auto-complete="off" :disabled="true"></el-input>
                      </el-form-item>
                      <el-form-item :label="$t('SysRole.rolename')" prop="name">
                        <el-input v-model="editForm.name" auto-complete="off" ></el-input>
                      </el-form-item>
                      <el-form-item :label="$t('SysRole.desc')" prop="des" >
                        <el-input type="textarea" v-model="editForm.des" auto-complete="off" ></el-input>
                      </el-form-item>
                      <el-form-item label="创建时间" prop="cdt">
                        <el-date-picker  v-model="editForm.cdt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
                      </el-form-item>
                      <el-form-item label="修改时间" prop="udt">
                        <el-date-picker  v-model="editForm.udt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
                      </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                      <el-button @click="editCancle">{{$t('SysRole.cancle')}}</el-button>
                      <el-button type="primary" @click="editSubmit" :loading="addLoading">{{$t('SysRole.submit')}}</el-button>
                    </div>
                  </el-dialog>
                </div>
                <div style=" width:40%;float:right;margin-top:74px; ">
                    <div class="clearfix">
                        <!--<el-col :span="3" class="grid">-->
                            <el-button type="success" icon="el-icon-circle-plus-outline" @click="addRoleUser(1)" size="mini" round v-has="'/auth/role/userAdd'">{{$t('SysRole.add')}}</el-button>
                        <!--</el-col>-->
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <!--<el-col :span="7" class="grid">-->
                            <el-button type="danger" icon="el-icon-delete" @click="deleteUserRole" size="mini" round v-has="'/auth/role/userDelete'">{{$t('SysRole.delete')}}</el-button>
                        <!--</el-col>-->
                    </div>

                    <el-table :data="roleUserList" class="mgt20" @selection-change="roleuserSelectChange" border style="width: 100%" stripe ref="roleUserList" tooltip-effect="dark">
                        <!--勾选框-->
                        <el-table-column type="selection" style="width: 20%">
                        </el-table-column>
                        <!--索引-->
                        <el-table-column type="index"style="width: 20%" :index="indexMethod">
                        </el-table-column>
                        <el-table-column prop="name" :label="$t('SysRole.name')" style="width: 30%" sortable>
                        </el-table-column>
                        <el-table-column prop="id" :label="$t('SysRole.id')" style="width: 30%" sortable>
                        </el-table-column>

                    </el-table>
                    <el-pagination @current-change="getRoleUserList" :current-page="roleusercurrentPage" :page-size="pageSize" layout="total, prev, pager, next" :total="roleusercount">
                    </el-pagination>
                    <br>
                    <!--新增界面-->
                    <el-dialog :title="$t('SysRole.adduser')" :visible.sync="addRoleUserVisible" :close-on-click-modal="false">
                        <el-row>
                            <el-col :span="200" class="grid">
                                <el-input v-model="s_username" :label="$t('SysRole.name')" placeholder="请输入用户名称" style="width:200px; heght:30px;" size="mini"></el-input>
                            </el-col>
                            <el-col :span="1" class="grid">
                                <el-button type="success" @click="addRoleUser(1)" icon="el-icon-search" size="mini">{{$t('SysRole.search')}}</el-button>
                            </el-col>
                        </el-row>
                        <br>
                        <el-table :data="userData" @selection-change="userSelectionChange" border style="width: 100%" stripe ref="multipleTable" tooltip-effect="dark">
                            <!--勾选框-->
                            <el-table-column type="selection"style="width: 20%">
                            </el-table-column>
                            <!--索引-->
                            <el-table-column type="index" :index="indexMethod" style="width: 20%">
                            </el-table-column>
                            <el-table-column prop="name" :label="$t('SysRole.name')" style="width: 30%">
                            </el-table-column>
                            <el-table-column prop="id" :label="$t('SysRole.id')" style="width: 30%" sortable>
                            </el-table-column>
                            <el-table-column prop="cdt" :label="$t('SysRole.cdt')" style="width: 30%" sortable>
                            </el-table-column>
                        </el-table>
                        <el-pagination @current-change="addRoleUser" :current-page="usercurrentPage" :page-size="pageSize" layout="total, prev, pager, next" :total="usercount">
                        </el-pagination>
                        <br>
                        <div slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="saveUserRole" :loading="addLoading">{{$t('SysRole.save')}}</el-button>
                            <el-button @click="addRoleUserVisible= false">{{$t('SysRole.close')}}</el-button>
                        </div>
                    </el-dialog>
                </div>
            </div>
        </div>
        <!-- 页面内容区end-->
    </div>
    <!-- 页面表格end -->
</template>
<script>
  import {formatDate} from '../../common/date.js'
export default {
    data() {
        return {
            tableData: [],
            roleTypeData: [],
            roleUserList: [],
            userData: [],
            roleuserSelect: [],
            input: "",
            curentroleid: "",
            curentrow: null,
            //列表Loading加载
            listLoading: false,
            //新增界面是否显示
            addFormVisible: false,
            //编辑界面是否显示
            editFormVisible:false,
            //添加按钮Loading加载
            addLoading: false,
            //新增角色用户
            addRoleUserVisible: false,
            roletotal: 0,
            usercount: 0,
            roleusercount: 0,
            currentPage: 1,
            usercurrentPage: 1,
            roleusercurrentPage: 1,
            pageSize: 10,
            //检索字段
            s_rolename: "",
            s_rolecode: "",
            s_username: "",
            //     s_roleType:"",
            //输入框验证
            addFormRules: {
                name: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
            des: [{ required: true, message: "请输入角色描述", trigger: "blur" }]
            },    //输入框验证
          editFormRules: {
            name: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
            des: [{ required: true, message: "请输入角色描述", trigger: "blur" }]
          },
            //新增界面数据
            addForm: [],
            editForm:{
              id:"",
              name:"",
              des:"",
              cdt:"",
              udt:""
            },
            UserSelection: []
        }
    },

    methods: {
        editCancle(){
          this.editFormVisible = false
          this.$refs["editForm"].resetFields();
        },
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        indexMethod(index) {
            return index;
        },
        //获取角色列表
        getResult: function(val) {

            var _this = this;
            this.listLoading = true;
              let param = Object.assign({}, { skip: (val-1)*10, size: 10, name: this.s_rolename?this.s_rolename:null, id: this.s_rolecode?this.s_rolecode:null });
            this.$ajax({
                method: "post",
                url: "/role/querySysRoleList",
                data: param
            }).then(
                function(resultData) {
                  var json = resultData.data;
                  console.log(json)
                  if (json.status=='ok'){
                    console.log(resultData)
                    let count =  json.count
                    console.log( resultData.data.data)
                    _this.tableData = json.data;
                    _this.roletotal = count;
                    _this.listLoading = false;
                  }else{
                    _this.$message({message: json.message,type: "error"});
                  }

                },
                );

        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.getResult(1);

        },
        //新增
        addSubmit: function() {
            this.$refs.addForm.validate(valid => {
                if (valid) {
                    this.$confirm("确认提交吗？", "提示", {}).then(() => {
                        this.addLoading = true;
                        let param = Object.assign({}, this.addForm);
                        this.$ajax({
                            method: "post",
                            url: "/role/addRole",
                            data: param
                        }).then(res => {
                          var json = res.data;
                          if (json.status=='ok'){
                            this.addLoading = false;
                            this.$message({
                              message: "提交成功",
                              type: "success"
                            });

                          }else{
                            this.$message({message: json.message,type: "error"});

                          }
                          this.$refs["addForm"].resetFields();
                          this.addFormVisible = false;
                          this.getResult(1);

                        });
                    });
                }
            });
        },
      //编辑提交
      editSubmit:function(){
        this.$refs.editForm.validate(valid => {
          if (valid) {
            this.$confirm("确认提交吗？", "提示", {}).then(() => {
              // this.editLoading = true;
              let param = Object.assign({}, this.editForm);
              this.$ajax({
                method: "post",
                url: "/role/editRole",
                data: param
              }).then(res => {
                var json = res.data;
                if (json.status=='ok'){
                  this.$message({
                    message: "编辑成功",
                    type: "success"
                  });
                }else {
                  this.$message({message: json.message,type: "error"});
                }
                 this.$refs["editForm"].resetFields();
                 this.editFormVisible = false;
                 this.getResult(1);
              });
            });
          }
        });

      },
        //显示新增界面
        handleAdd: function() {
            this.addFormVisible = true;
            this.addForm = {
                name: "",
                des: "",
            };
        },
        //显示编辑角色界面
        enditRole: function(row) {
            var _this = this;
            this.listLoading = true;
            this.editFormVisible = true;
            let param = new URLSearchParams();
            param.append("id", row.id);
            this.editForm.id = row.id,
            this.editForm.name=row.name
            this.editForm.des=row.des
            this.editForm.cdt=formatDate(new Date(row.cdt), 'yyyy-MM-dd hh:mm:ss')
            console.log(formatDate(new Date(row.cdt), 'yyyy-MM-dd hh:mm')+"===============")
            this.editForm.udt=formatDate(new Date(row.udt), 'yyyy-MM-dd hh:mm:ss')
          console.log(formatDate(new Date(row.udt), 'yyyy-MM-dd hh:mm')+"===============")

        },
        delRole: function(row) {
            // let param = new URLSearchParams();
            // param.append("id", row.id);
            let param = {
              id:row.id
            }

            this.$ajax({
                method: "post",
                url: "/role/deleteSysRoleByid",
                data: param
            })
            .then((res)=>{
                  var json = res.data;
                  if (json.status =='ok'){
                    this.$message({
                      message: "删除成功",
                      type: "success"
                    });
                  }else {
                    this.$message({message: json.message,type: "error"});
                  }
                  this.getResult(1)
            });

        },
        getRoleUserList: function(val) {
            var _this = this;
            this.listLoading = true;
            let param = Object.assign({}, { skip: (val-1)*10, size: 10, roleId: this.curentroleid });
            this.$ajax({
                method: "post",
                url: "/authorization/getRoleUserList",
                data: param
            }).then(
                function(resultData) {
                    console.log(resultData)
                    _this.roleUserList = resultData.data.data;
                    _this.roleusercount = resultData.data.count;
                }
                ).catch(
              function(resultData) {
                _this.roleUserList=null
                _this.roleusercount = null;
              }
            )
        },
        clickRow: function(row) {
            this.roleuserSelect = [];
            // var roleid=row.id;
            var _this = this;
            this.curentroleid = row.id;
            this.curentrow = row;
            _this.getRoleUserList(1)

        },
        addRoleUser: function(val) {
            this.addRoleUserVisible = true;
            var _this = this;
            this.listLoading = true;
            let param = Object.assign({}, { currentPage: (val-1)*10, pageSize: 10, name: this.s_username });
            this.$ajax({
                method: "post",
                url: "/user/querySysUserList",
                data: param
            }).then(
                function(resultData) {
                  var json = resultData.data
                  if (json.status=='ok'){
                    let dataList =  json.dataList;
                    for (var i = 0;i<dataList.length;i++){
                      let cdt = dataList[i].cdt;
                      var dateCdt = new Date(cdt);
                      dataList[i].cdt=formatDate(dateCdt, 'yyyy-MM-dd hh:mm')
                    }
                    _this.userData =dataList
                    _this.usercount = json.count;
                  }
                  else{
                      _this.$message({message: json.message, type:"error"});
                      console.log("Error========>"+json.success);
                  }
                }
                );

        },
        userSelectionChange: function(val) {
            //alert(val);
            this.UserSelection = val;
        },
        roleuserSelectChange: function(val) {
            this.roleuserSelect = val;
        },
        saveUserRole: function() {
            if (this.curentroleid == "") {
                alert("请选择要添加的角色，单击选择行。");
            }
            var rows = this.UserSelection;
            if (rows) {
                // rows.forEach(row => {
                //     var _this = this;
                //     this.listLoading = true;
                //     let param = Object.assign({}, { id: 0, roleId: this.curentroleid, targetId: row.id });
                //     this.$ajax({
                //         method: "post",
                //         url: "/api/sysrole-api/saveRoleUser",
                //         data: param
                //     }).then(
                //         function(resultData) {
                //
                //         }
                //         );
                //     // alert(row.id)
                // });
              console.log(rows)
              let userIds=[];
              for (var i=0;i<rows.length;i++){
                userIds[i]=rows[i].id
              }
              console.log(userIds)
              var _this = this;
              this.listLoading = true;
              let param= {
                roleId:this.curentroleid,
                userIds
              }
              this.$ajax({
                method: "post",
                url: "/authorization/saveRoleUser",
                data: param
            }).then(
                function(resultData) {

                  // alert("成功")
                 this.getRoleUserList(1)
                }
                ).catch(function (result) {
                this.getRoleUserList(1)
              })
            }
            // this.$refs["addRoleUser"].resetFields();
            this.addRoleUserVisible = false;
            this.clickRow(this.curentrow);
            //alert(this.UserSelection);
        },
        deleteUserRole: function() {
            var rows = this.roleuserSelect;
            if (rows) {
              let userIds=[];
              for (var i=0;i<rows.length;i++){
                userIds[i]=rows[i].id
              }
              console.log(userIds)
              var _this = this;
              this.listLoading = true;
              let param= {
                roleId:this.curentroleid,
                userIds
              }
                  this.$ajax({
                      method: "post",
                      url: "/authorization/deleteSysRoleUserByid",
                      data: param
                  }).then(
                        this.getRoleUserList(1)
                      );


            }
            this.addRoleUserVisible = false;
            this.clickRow(this.curentrow);
        }
    },
    mounted() {
        //获取列表
          this.getResult(1);
        // this.getRoleType();

    }
};
</script>
<style>
/* #app {
        font-family: Helvetica, sans-serif;
        text-align: center;
    } */


/* #roleuser {
        font-family: Helvetica, sans-serif;
        text-align: center;
    }
    el-input{width:200px;height: 50px;} */
</style>
