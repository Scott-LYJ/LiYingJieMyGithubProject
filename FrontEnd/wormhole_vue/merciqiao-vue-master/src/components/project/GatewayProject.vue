<template>
    <div class="container messageboard">
        <!-- 查询区----start -->
        <el-form :label-position="labelPosition" :label-width="labelWidth" :inline="true" ref="formSearch" :model="formSearch" class="demo-form-inline">
            <el-form-item label="项目id" prop="id">
                <el-input v-model="formSearch.id" placeholder="模糊匹配"></el-input>
            </el-form-item>
            <el-form-item label="项目名称" prop="name">
                <el-input v-model="formSearch.name" placeholder="城市"></el-input>
            </el-form-item>

            <el-form-item label="创建者id" prop="createrid">
                <el-input type="number" v-model="formSearch.createrid" placeholder="年龄"></el-input>
            </el-form-item>
            <br/>
            <el-form-item label="创建时间" prop="cdt">
                <el-date-picker
                    v-model="formSearch.cdt"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label=" " style="margin-left:50px;">
                <el-button type="primary" @click="onSearch">查询</el-button>
                <el-button type="warning" plain @click="onReset">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 查询区----end -->
        <!-- 操作区----start -->
        <el-row class="mgb15">
            <el-button size="small" round type="primary" @click="handleAdd">新增</el-button>
            <el-button size="small" round type="danger" @click="deleteMany">批量删除</el-button>
        </el-row>
        <!-- 操作区----end -->
        <!-- 表格---start -->
        <el-table :data="tableData" v-loading="listLoading"  border stripe style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="60">
            </el-table-column>
          <el-table-column prop="id" label="id" align="center" width="90px">
          </el-table-column>
          <el-table-column
            prop="img"
            label="头像"
            sortable
            width="90px">
            <!--插入图片链接的代码-->
            <template slot-scope="scope">
              <img  :src="scope.row.avatar" alt="" @click="openDetail(scope.row)" style="width: 50px;height: 50px">
            </template>
          </el-table-column>
            <el-table-column prop="name" label="项目名" width="150" align="center" sortable>
                 <template slot-scope="scope">
                    <a href="javacript:;" style="color: #00D1B2" @click="handleDetail(scope.$index, scope.row)">{{ scope.row.name}}</a>
                </template>
              <!--openDetail(scope.row)-->
            </el-table-column>
            <el-table-column prop="des" label="项目描述" align="center" width="250">
            </el-table-column>
             <el-table-column prop="authUser.name" label="创建人" align="center" width="100">
            </el-table-column>
            <el-table-column prop="cdt" label="创建日期" :formatter="this.$common.timestampToTime" width="180" sortable>
            </el-table-column>
            <el-table-column label="操作" fixed="right" min-width="250">
                <template slot-scope="scope">
                  <el-button size="mini" plain type="primary" @click="toGroupAndApi( scope.row)">接口/分组</el-button>
                    <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" plain type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination background layout="total,sizes,prev, pager, next,jumper" :current-page="pageInfo.currentPage" :page-size="pageInfo.pageSize" :total="pageInfo.pageTotal" :page-sizes="[5, 10, 20, 50]" @size-change="handleSizeChange" @current-change="handleCurrentChange">
        </el-pagination>
        <!-- 表格---end -->

        <!-- 编辑弹框---start -->
        <el-dialog  :title="formEditTitle" :visible.sync="dialogEdittVisible" width="600px" @close="closeDialog('formEdit')">
            <el-form :label-position="labelPosition" :label-width="labelWidth" :rules="rulesEdit" :disabled="formEditDisabled" :inline="true" ref="formEdit" :model="formEdit" class="demo-form-inline">
              <el-form-item :label="$t('SysUser.icon')" prop="imageurl">
                <el-upload
                  v-model="formEdit.imageurl"
                  ref="upload"
                  action="/project/upload"
                  name="picture"
                  list-type="picture-card"
                  :limit="1"
                  :file-list="fileList"
                  :on-exceed="onExceed"
                  :before-upload="beforeUpload"
                  :on-preview="handlePreview"
                  :on-success="handleSuccess"
                  :on-remove="handleRemove"
                  :show-file-list="true"
                >
                  <i class="el-icon-plus"></i>
                </el-upload>
                <el-dialog :visible.sync="dialogVisible">
                  <img width="100%" :src="dialogImageUrl" alt="">
                </el-dialog>
              </el-form-item>
              &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;
              <br/>
                 <el-form-item label="项目名称" prop="name">
                    <el-input v-model="formEdit.name" placeholder="姓名" ></el-input>
                </el-form-item>
                <el-form-item label="项目描述" prop="city">
                  <el-input type="textarea" v-model="formEdit.des" auto-complete="off" ></el-input>
                </el-form-item>
              <el-form-item label="创建人id" prop="city" display="none">
                <el-input v-model="formEdit.createrid" placeholder="创建人id" :disabled="true"></el-input>
              </el-form-item>
                 <el-form-item label="创建人" prop="type">
                   <el-input  v-model="formEdit.creater" auto-complete="off" :disabled="true"></el-input>
                </el-form-item>
              <el-form-item :label="$t('SysUser.cdt')" prop="cdt" v-if="formEditTitle=='detail'">
                <el-date-picker  v-model="formEdit.cdt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancle">取 消</el-button>
                <el-button v-if="!formEditDisabled" type="primary" @click="handleSave">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 编辑弹框---end -->

    </div>
</template>

<style lang="scss">
.messageboard{
    // 设置输入框的宽度
    .el-form-item__content {
        width: 220px;
    }
}

</style>

<script>
import apis from '../../apis/apis';
export default {
    name: 'messageboard',
    data() {
        return {
          //文件上传的参数
          dialogImageUrl: '',
          dialogVisible: false,
          //图片列表（用于在上传组件中回显图片）
          fileList: [{name: '', url: ''}],
            listLoading : false,//
            pageInfo: { //分页
                currentPage: 1,
                pageSize: 5,
                pageTotal: 80
            },
            formSearch: { //表单查询
                id: null,
                name:null,
                createrid:null,
                creater:null,
                cdt:null,
            },
            formEdit: { //表单编辑
                imageurl:null,
                id:null,
                name: '',
                des:'',
                picture:'',
                createrid:this.$common.getSessionStorage("id"),
                creater:this.$common.getSessionStorage("username")
            },
             rulesEdit:  {
                name: [
                    { required: true, message: "请输入项目名称", trigger: "blur" },
                    { min: 2, max: 4, message: "长度在 2 到 4 个字符", trigger: "blur" }
                ],
                des:[{ required: true, message: "请输入项目的基本描述", trigger: "blur" }]
                ,

            },
            formEditTitle:'编辑',//新增，编辑和查看标题
            formEditDisabled:false,//编辑弹窗是否可编辑
            dialogEdittVisible: false,  //编辑弹框显示
            dialogType:'',//弹框类型：add,edit,show
            tableData: [  //表单列表
                {   id:"1",
                    createtime: "2016-05-02",
                    name: "李紫婷",
                    address: "上海市普陀区金沙江路 1518 弄"
                },
                {
                     id:"2",
                    createtime: "2016-05-04",
                    name: "杨超越",
                    address: "上海市普陀区金沙江路 1517 弄"
                },
                {
                     id:"3",
                    createtime: "2016-05-01",
                    name: "赖小七",
                    address: "上海市普陀区金沙江路 1519 弄"
                },
                {
                    id:"4",
                    createtime: "2016-05-03",
                    name: "张紫宁",
                    address: "上海市普陀区金沙江路 1516 弄"
                }
            ],
            labelPosition: 'right', //lable对齐方式
            labelWidth: '80px', //lable宽度
            formLabelWidth: '120px',
            multipleSelection: []
        };
    },
    computed:{

    },
    filters: {
        convertType: function (type) {
            if(type==1){
                return '留言';
            }
            else if(type==2)
            {
                return '建议';
            }
            else if(type==3){
                return 'BUG';
            }
        }
    },
    mounted(){
        this.onSearch();
        // var loginLog = {
        //     ip: returnCitySN["cip"],
        //     city: returnCitySN["cname"] + "-增删改查页"
        // };

       // apis.shiroApi.loginLog(loginLog);
    },
    methods: {
      //查看分组和接口
      toGroupAndApi(row){
        var _this = this

        _this.$router.push({path: 'groupAndapi', query: {project: row}})
      },
      cancle(){
        this.dialogEdittVisible = false
        this.$refs["formEdit"].resetFields();
        this.$refs.upload.clearFiles();
        this.formEdit.imageurl="";
        this.formEdit.picture=""

      },
      //上传图片的方法
      //文件上传成功的钩子函数
      handleSuccess(res, file) {
        this.$message({
          type: 'info',
          message: '图片上传成功',
          duration: 6000
        });

        console.log(file)
        if (file.response.status='200') {
          console.log(file.response.message)
          this.formEdit.picture = file.response.data; //将返回的文件储存路径赋值picture字段
        }
      },
      //新增
      //删除文件之前的钩子函数
      handleRemove(file, fileList) {
        this.$message({
          type: 'info',
          message: '已删除原有图片',
          duration: 6000
        });
      },
      //点击列表中已上传的文件事的钩子函数
      handlePreview(file) {
      },
      //上传的文件个数超出设定时触发的函数
      onExceed(files, fileList) {
        this.$message({
          type: 'info',
          message: '最多只能上传一个图片',
          duration: 6000
        });
      },
      //文件上传前的前的钩子函数
      //参数是上传的文件，若返回false，或返回Primary且被reject，则停止上传
      beforeUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isGIF = file.type === 'image/gif';
        const isPNG = file.type === 'image/png';
        const isBMP = file.type === 'image/bmp';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG && !isGIF && !isPNG && !isBMP) {
          this.$message.error('上传图片必须是JPG/GIF/PNG/BMP 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return (isJPG || isBMP || isGIF || isPNG) && isLt2M;
      },
        /**
         * 查询列表
         */
        onSearch(){
            this.listLoading=true;

            if(this.formSearch.createtime){
                this.formSearch.startdate=this.formSearch.createtime[0];
                this.formSearch.enddate=this.formSearch.createtime[1];
            }
            let param = Object.assign({}, this.formSearch,this.pageInfo);
            apis.msgApi.getList(param)
            .then((data)=>{
              console.log("data",data)
                this.listLoading=false;
                if (data && data.data) {

                        var json = data.data;
                        console.log("json",json)
                        if (json.status == '200') {
                            this.pageInfo.pageTotal=json.count;
                            this.tableData=json.data;
                        }
                        else if (json.message) {
                            this.$message({message: json.message,type: "error"});
                        }
                }
            })
            .catch((err)=>{
                this.listLoading=false;
                this.$message({message: '查询异常，请重试',type: "error"});
            });
        },
        handleSave(){
            if(this.dialogType=='add'){
                this.save();
              //dialogEdittVisible = false
              this.dialogEdittVisible = false
              this.$refs["formEdit"].resetFields();
              this.$refs.upload.clearFiles();
              this.formEdit.imageurl=""
              this.formEdit.picture=""

            }
            else if(this.dialogType=='edit'){
                this.update();
              this.dialogEdittVisible = false
              this.$refs["formEdit"].resetFields();
              this.$refs.upload.clearFiles();
              this.formEdit.imageurl=""
              this.formEdit.picture=""
            }
            else{
                this.$message({message: '操作异常',type: "error"});
            }
        },
        /**
         * 保存
         */
        save() {
            this.$refs["formEdit"].validate(valid => {
                if(valid){
                    let param = Object.assign({}, this.formEdit);
                    apis.msgApi.add(param)
                    .then((data)=>{
                        if(data&&data.data){
                            var json=data.data;
                             if(json&&json.status=='200'){
                                this.$message({message: '执行成功',type: "success"});
                                this.dialogEdittVisible = false;
                                this.onSearch();
                                return;
                            }
                        }
                       this.$message({message: '执行失败，请重试',type: "error"});
                    })
                    .catch((err)=>{
                        this.$message({message: '执行失败，请重试',type: "error"});
                    });
                }


            });
        },
         /**
         * 更新
         */
        update() {
            this.$refs["formEdit"].validate(valid => {
                if(valid){
                    let param = Object.assign({}, this.formEdit);
                    apis.msgApi.update(param)
                    .then((data)=>{
                        if(data&&data.data){
                            var json=data.data;
                             if(json&&json.status=='200'){
                                this.$message({message: '执行成功',type: "success"});
                                this.dialogEdittVisible = false;
                                this.onSearch();
                                return;
                            }
                        }
                       this.$message({message: '执行失败，请重试',type: "error"});
                    })
                    .catch((err)=>{
                        this.$message({message: '执行失败，请重试',type: "error"});
                    });
                }


            });
        },
         /**
         * 删除
         */
        handleDelete(index, rowData) {
             if(rowData.name=='使用文档'){
                this.$message('使用文档不可删除');
                return;
            }
            var id=rowData.id;
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                    apis.msgApi.delete({id:id})
                    .then((data)=>{
                        console.log("删除成功")
                        this.onSearch();
                        this.$common.isSuccess(data,()=>{
                            debugger;
                            this.onSearch();
                        });
                    })
                    .catch((err)=>{
                        debugger;
                        this.$message({message: '执行失败，请重试',type: "error"});
                    });

            }).catch(() => {
                this.$message({type: 'info',message: '已取消删除'});
            });

        },
        /**
         * 批量删除
         */
        deleteMany() {
            var ids= this.multipleSelection.map(item => item.id);
            if(ids.length==0){
                 this.$message({message: '请选择要删除的项',type: "warn"});
                return;
            }
            debugger;
            this.$confirm('此操作将批量永久删除文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                        apis.msgApi.deleteBatch({ids:ids})
                        .then((data)=>{
                            this.$common.isSuccess(data,()=>{
                                debugger;
                                this.onSearch();
                            });
                        })
                        .catch((err)=>{
                            debugger;
                            this.$message({message: '执行失败，请重试',type: "error"});
                        });

                }).catch(() => {
                    this.$message({type: 'info',message: '已取消删除'});
                });

        },
        onReset(){
            this.$refs['formSearch'].resetFields();
        },
        /**
         * 打开编辑弹窗
         */
        handleAdd() {
            this.dialogEdittVisible = true;
            this.$nextTick(()=>{
                this.dialogType='add';
                this.formEditTitle='新增';
                this.formEditDisabled=false;
            });


        },
        /**
         * 打开编辑弹窗
         */
        handleEdit(index, rowData) {
            //var msg = "索引是:" + index + ",行内容是:" + JSON.stringify(rowData);
            //this.$message({message: msg,type: "success"});
            this.dialogEdittVisible = true;//等dom渲染完，读取data中初始值，然后再复制，这样重置的是data中初始值
            this.$nextTick(()=>{
                this.dialogType='edit';
                this.formEditTitle='编辑';
                this.formEditDisabled=false;
                this.formEdit= Object.assign({}, rowData);
                this.formEdit.picture=""
                this.formEdit.creater=rowData.authUser.name
                this.formEdit.gender+='';//必须转换成字符串才能回显
            });
        },
        /**
         * 打开详情页
         */
        handleDetail(index,rowData){
            this.dialogEdittVisible = true;
            this.$nextTick(()=>{
                 this.dialogType='show';
                this.formEditTitle='详情';
                this.formEditDisabled=true;
                this.formEdit= Object.assign({}, rowData) ;
                this.formEdit.creater=rowData.authuer.name;
                this.formEdit.gender+='';
            });

        },
        /**
         * 关闭弹框，数据重置
         */
        closeDialog(formName){
            this.$refs[formName].resetFields();
        },
        /**
         * 分页大小切换
         */
        handleSizeChange(val) {
            this.pageInfo.pageSize = val;
            this.onSearch();
        },
        /**
         * 分页切换
         */
        handleCurrentChange(val) {
            this.pageInfo.currentPage = val;
            this.onSearch();
        },
        /**
         * 点击选择
         */
        handleSelectionChange(val) {
            this.multipleSelection = val;
            // this.$message({
            //     message: '选中的项是:' + JSON.stringify(this.multipleSelection),
            //     type: "success"
            // });
        },
        /**
         * 打开详情页
         */
           openDetail(row){
             console.log(row)
            this.$common.OpenNewPage(this,'detail',row.avatar);
        }


    }
};
</script>
