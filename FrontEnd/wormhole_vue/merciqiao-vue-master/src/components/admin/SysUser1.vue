<template>
     <section>
        <div class="table"> <!-- 页面表格begin -->
          <div class="crumbs"> <!-- 页面标题begin -->
              <el-breadcrumb separator="/">
                  <el-breadcrumb-item><i class="el-icon-tickets"></i>用户管理</el-breadcrumb-item>
              </el-breadcrumb>
          </div> <!-- 页面标题end-->
          <div class="container"><!-- 页面内容区begin -->
             <div class="handle-box"> <!-- 搜索区begin -->
  <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                {{$t("SysUser.name")}}:<el-input v-model="filters.name"  :placeholder="$t('SysUser.name')" style="width:200px; heght:30px;" size="mini"></el-input>
                </el-form-item>
                 <el-form-item>
                   {{$t("SysUser.id")}}:<el-input v-model="filters.id"   :placeholder="$t('SysUser.id')" style="width:200px; heght:30px;" size="mini"></el-input>
                </el-form-item>
                 <el-form-item>
                   {{$t("SysUser.phone")}}:<el-input v-model="filters.phone"  :placeholder="$t('SysUser.phone')" style="width:200px; heght:30px;" size="mini"></el-input>
                </el-form-item>
                <br>
                 <el-form-item>
                   {{$t("SysUser.cdt")}}:<el-date-picker v-model="filters.cdt" size="mini" type="date" :placeholder="$t('SysUser.selectDate')" value-format="timestamp">
                 </el-date-picker>
                </el-form-item>
                <el-form-item>
                   <el-button type="primary" icon="el-icon-search" @click="getResult(1)" size="mini">{{$t("SysUser.search")}}</el-button>
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
            <!--勾选框-->
            <el-table-column type="selection" width="55">
            </el-table-column>
            <!--索引-->
            <el-table-column type="index" :index="indexMethod">
            </el-table-column>
            <el-table-column prop="name" :label="$t('SysUser.name')">
               <template slot-scope="scope">
              <el-button @click="handleSelect(scope.$index,scope.row)" type="text" size="small">{{scope.row.name}}</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="id" :label="$t('SysUser.id')" >
            </el-table-column>
             <el-table-column prop="address" :label="$t('SysUser.address')" >
            </el-table-column>
             <el-table-column prop="phone" :label="$t('SysUser.phone')" >
            </el-table-column>
             <el-table-column prop="cdt" :label="$t('SysUser.cdt')" >
            </el-table-column>
            <el-table-column prop="udt" :label="$t('SysUser.udt')" >
            </el-table-column>
              <el-table-column  fixed="right" :label="$t('SysUser.operation')" width="150">
               <template slot-scope="scope">
                    			   	<el-button type="primary" plain size="small" @click="handleRole(scope.$index,scope.row)">{{$t("SysUser.role")}}</el-button>
                      	<el-button size="small" @click="handleEdit(scope.$index,scope.row)">{{$t("SysUser.add")}}</el-button>
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
        <el-form-item :label="$t('SysUser.icon')" prop="imageurl">
          <el-upload
            v-model="addForm.imageurl"
            ref="upload"
            action="/user/upload"
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
        <el-form-item :label="$t('SysUser.name')" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
        <br/>
        <el-form-item :label="$t('SysUser.password')" prop="password">
          <el-input v-model="addForm.password" @focus.capture.native='changePasswordTip(true)' @blur.capture.native='changePasswordTip(false)' auto-complete="off" type="password" :placeholder="$t('SysUser.Please enter your password')" ></el-input>
          <div style="position: absolute">
            <verify-pass-word-tip :password="addForm.password" :isShowTip = 'addForm.isShowTip'></verify-pass-word-tip>
          </div>
				</el-form-item>

        <el-form-item :label="$t('SysUser.repassword')" prop="repassword">
          <el-input v-model="addForm.repassword" @focus.capture.native='changePasswordTip2(true)'  @blur.capture.native='changePasswordTip2(false)' auto-complete="off" type="password" :placeholder="$t('Please enter your password again')" ></el-input>
          <div style="position: absolute">
            <verify-pass-word-tip :password="addForm.repassword" :isShowTip = 'addForm.isShowTip2'></verify-pass-word-tip>
          </div>
      </el-form-item>
        <br/>
        <el-form-item :label="$t('SysUser.Provinces')" prop="address">
            <el-cascader
              size="large"
              :options="options"
              v-model="addForm.address"
              @change="handleChange">
            </el-cascader>
        </el-form-item>
        <el-form-item :label="$t('SysUser.Address Supplement')" prop="address2">
          <el-input v-model="addForm.address2" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item :label="$t('SysUser.phone')" prop="phone">
					<el-input v-model="addForm.phone" auto-complete="off" @blur.capture.native='checkPhone()'></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="addCancle">{{$t("SysUser.cancle")}}</el-button>
				<el-button type="primary" @click="addSubmit" :loading="addLoading">{{$t("SysUser.submit")}}</el-button>
			</div>
		</el-dialog>
        <!--详细界面-->
		<el-dialog :title="$t('SysUser.information')" :visible.sync="selectFormVisible" :close-on-click-modal="false">

			<el-form :inline="true" :model="selectForm" label-width="80px"  ref="selectForm">
        <el-form-item :label="$t('SysUser.icon')" prop="name">
          <!-- 用户头像 -->
          <div class="user-avator"><img :src="selectForm.avatar" v-model="selectForm.avatar" ></div>
        </el-form-item>
        <br/>
        <el-form-item :label="$t('SysUser.name')" prop="name">
					<el-input v-model="selectForm.name" auto-complete="off" :disabled="true"></el-input>
				</el-form-item>
        	<el-form-item :label="$t('SysUser.password')" prop="password">
					<el-input v-model="selectForm.password" auto-complete="off" :disabled="true"></el-input>
				</el-form-item>
        <el-form-item :label="$t('SysUser.address')" prop="address">
					<el-input v-model="selectForm.address" auto-complete="off" :disabled="true"></el-input>

				</el-form-item>
         &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;
        <el-form-item :label="$t('SysUser.phone')" prop="phone">
					<el-input v-model="selectForm.phone" auto-complete="off" :disabled="true"></el-input>
				</el-form-item>
        <br/>
         <el-form-item :label="$t('SysUser.cdt')" prop="cdt">
          <el-date-picker  v-model="selectForm.cdt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
				</el-form-item>
          <el-form-item :label="$t('SysUser.udt')" prop="udt">
            <el-date-picker  v-model="selectForm.udt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
          </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="selectFormVisible = false">{{$t('SysUser.cancle')}}</el-button>
			</div>
		</el-dialog>
     <!--编辑界面-->
		<el-dialog :title="$t('SysUser.edit')" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :inline="true" :model="editForm" label-width="80px" :rules="addFormRules" ref="editForm">
        <el-form-item :label="$t('SysUser.icon')" prop="imageurl">
          <el-upload
            ref="upload2"
            action="/user/upload"
            name="picture"
            list-type="picture-card"
            :limit="1"
            :file-list="fileList"
            :on-exceed="onExceed2"
            :before-upload="beforeUpload2"
            :on-preview="handlePreview2"
            :on-success="handleSuccess2"
            :on-remove="handleRemove2"
            :show-file-list="true"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item :label="$t('SysUser.name')" prop="loginName">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
        <br/>
        	<el-form-item :label="$t('SysUser.password')" prop="password" >
					<el-input v-model="editForm.password" auto-complete="off" type="password" :disabled="!checked" ></el-input>
				</el-form-item>
        <el-form-item label="" prop="">
				<el-checkbox v-model="checked">{{$t('SysUser.Change your password')}}</el-checkbox>
				</el-form-item>
        <br/>
        <el-form-item :label="$t('SysUser.phone')" prop="mobile">
					<el-input v-model="editForm.phone" auto-complete="off"></el-input>
				</el-form-item>
        <br/>
        <el-form-item :label="$t('SysUser.address')" prop="address">
          <el-input v-model="editForm.address" auto-complete="off"></el-input>
        </el-form-item>
        <br/>
          &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;
        <el-form-item label="操作提示" prop="">
         <span style="color:#F00"> 修改登入密码请选中“修改密码”并点击保存；如果不需要修改密码请直接点击保存，密码将不会同步更新
         </span>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="editCancle">{{$t('SysUser.cancle')}}</el-button>
				<el-button type="primary" @click="editSubmit" :loading="editLoading">{{$t('SysUser.submit')}}</el-button>
			</div>
		</el-dialog>

       <!-- 查看角色 -->
              <el-dialog :title="$t('SysUser.role')" :visible.sync="roleFormVisible" :close-on-click-modal="false">
                <el-form :model="infoForm" label-width="80px"  ref="infoForm" :inline="true">
                  <el-form-item :label="$t('SysUser.name')" prop="positionName">
                    <el-input v-model="infoForm.name" auto-complete="off" :disabled="true"></el-input>
                  </el-form-item>
                <el-form-item :label="$t('SysUser.id')" prop="positionCode">
                    <el-input v-model="infoForm.id" auto-complete="off" :disabled="true"></el-input>
                  </el-form-item>
                </el-form>
                <el-form  label-width="80px" :inline="true">
                  <div v-for="item in roleData" :key="item.id">
                  <el-form-item :label="$t('SysUser.rolename')" prop="">
                    <el-input v-model="item.name" auto-completen="off" :disabled="true"></el-input>
                  </el-form-item>
                  <el-form-item :label="$t('SysUser.roleid')" prop="">
                    <el-input v-model="item.id" auto-complete="off" :disabled="true"></el-input>
                  </el-form-item>
                </div>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="roleFormVisible = false">{{$t('SysUser.cancle')}}</el-button>
                </div>
              </el-dialog>
  </section>
 </template>

 <script>
   import { provinceAndCityData, regionData, provinceAndCityDataPlus, regionDataPlus, CodeToText, TextToCode } from 'element-china-area-data'
   import verifyPassWordTip from './verifyPassWordTip'
   import {formatDate} from '../../common/date.js'
   export default {
   name: 'SysUser1',
   components: {
       verifyPassWordTip,
     },
  data() {
    return {
      //文件上传的参数
      dialogImageUrl: '',
      dialogVisible: false,
      //图片列表（用于在上传组件中回显图片）
      fileList: [{name: '', url: ''}],
      //拼成的地址
      address:"",
      //上传图片
      imageUrl: '',

      options: regionDataPlus,

      selectedOptions: [],

      //搜索区域参数
      filters: {
        // name: "",
        // phone: "",
        // id: "",
        // cdt: "",
        mobile: "",
        sex: "",
        //性别下拉框
        sexOptions: [
          {
            value: "1",
            label: "男"
          },
          {
            value: "0",
            label: "女"
          }
        ],
        //岗位列表搜索参数
        positionName: "",
        positionCode: "",
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
      //角色信息
      roleData:[],
      //新增界面是否显示
      addFormVisible: false,
      //添加按钮Loading加载
      addLoading: false,
      //输入框验证
      addFormRules: {
        name: [
          { required: true, message: "请输入登录名", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入登录密码", trigger: "blur" }
        ],
        repassword: [
           { required: true, message: "请再次输入登录密码", trigger: "blur"}
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur"}
        ],
        address: [
          { required: true, message: "请选择省市区", trigger: "blur"}
        ],
      },
      password:"",
      //新增界面数据
      addForm: {
        id: "0",
        icon:"",
        name: "",
        password: "",
        repassword:"",
        address: [],//省市区
        address2:"",
        phone: "",
        isShowTip: false,
        isShowTip2: false,
        avatar:"",
        imageurl:""
      },
       //查看详细
      selectForm: {
        name: "",
        password: "",
        phone: "",
        address: "",
        email: "",
        avatar: "",
        cdt:"",
        udt:"",
      },
      //详细界面是否显示
      selectFormVisible:false,

      //编辑界面是否显示
      editFormVisible: false,
      //是否修改密码复选框
      checked: false,
      //编辑按钮Loading加载
      editLoading: false,
      //编辑界面数据
      editForm: {
        id: "",
        name: "",
        password:"",
        phone:"",
        address:"",
        picture:""
      },
      //用户信息
      infoForm:[],
      //角色Loading加载
      roleListLoading:false,
      //角色信息
      roleData:[],
      //是否显示角色信息
      roleFormVisible:false,

    };
  },
  methods: {
    geticon(){
      let userIcon = this.$common.getSessionStorage('icon');
      console.log(userIcon+"------------")
      return userIcon ? userIcon:'static/img/user/hongbao.png'
    },
    editCancle(){
      this.editFormVisible = false
      this.$refs["editForm"].resetFields();
      this.$refs.upload2.clearFiles();
    },
    addCancle(){
      this.addFormVisible = false
      this.$refs["addForm"].resetFields();
      this.$refs.upload.clearFiles();
    },
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
        this.addForm.picture = file.response.data; //将返回的文件储存路径赋值picture字段
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
    //编辑
    //删除文件之前的钩子函数
    handleRemove2(file, fileList) {
      this.$message({
        type: 'info',
        message: '已删除原有图片',
        duration: 6000
      });
    },
    //点击列表中已上传的文件事的钩子函数
    handlePreview2(file) {
    },
    //上传的文件个数超出设定时触发的函数
    onExceed2(files, fileList) {
      this.$message({
        type: 'info',
        message: '最多只能上传一个图片',
        duration: 6000
      });
    },
    //文件上传前的前的钩子函数
    //参数是上传的文件，若返回false，或返回Primary且被reject，则停止上传
    beforeUpload2(file) {
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
    //文件上传成功的钩子函数
    handleSuccess2(res, file) {
      this.$message({
        type: 'info',
        message: '图片上传成功',
        duration: 6000
      });
      console.log(file)
      if (file.response.status=='200') {
        console.log(file.response.message)
        this.editForm.picture = file.response.data; //将返回的文件储存路径赋值picture字段
      }
    },


    //  @blur.capture.native='changePasswordTip(false)'
    //手机号校验
    checkPhone(){
      if(!(/^1[3456789]\d{9}$/.test(this.addForm.phone))){
        this.$notify.error({
          message: '手机号格式错误',
        });

      }
    },
    /**
     * 改变密码提示是否显示
     **/
    changePasswordTip(isShow) {
      if (isShow) {
        this.addForm.isShowTip = true;
      } else {
        this.addForm.isShowTip = false;
      }
    },
    changePasswordTip2(isShow) {
      if (isShow) {
        this.addForm.isShowTip2 = true;
      } else {
        this.addForm.isShowTip2 = false;
        if (this.addForm.password==this.addForm.repassword){
          return
        }else{
          this.$alert('两次密码不一致，请再次输入密码', '提示', {
            confirmButtonText: '确定',
            callback: action => {
              this.addForm.repassword="";
            }
          });
        }
      }
    },
    //checkPassword



    //省市区联动
    handleChange (value) {
      console.log(value)
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
          cdt: this.filters.cdt,
          phone: this.filters.phone
        }
      );
      //delSysUserByUserId,querySysUserList
      this.$ajax({
        method: "post",
        url: "/user/querySysUserList",
        data: param
      }).then(function(resultData) {
        console.log(resultData.data.result)
       let dataList =  resultData.data.result.dataList
        for (var i = 0;i<dataList.length;i++){
          let cdt = dataList[i].cdt;
          let udt = dataList[i].udt;
          var dateCdt = new Date(cdt);
          var dateUdt = new Date(udt)
          dataList[i].cdt=formatDate(dateCdt, 'yyyy-MM-dd hh:mm')
          dataList[i].udt=formatDate(dateUdt, 'yyyy-MM-dd hh:mm')
        }
        _this.tableData = resultData.data.result.dataList;
        _this.count = resultData.data.result.count;
        _this.listLoading = false;
      });
    },
    //


    //是否有效显示转换
    formatState: function(row, column) {
      return row.validateState == 1
        ? "有效"
        : row.validateState == 0 ? "无效" : "";
    },
    //是否锁定显示转换
    formatLocked: function(row, column) {
      return row.isLocked == 1 ? "锁定" : row.isLocked == 0 ? "未锁定" : "";
    },
    //查看角色
    handleRole: function(index, row) {
      //显示角色信息页面
      this.roleFormVisible=true;

      var _this = this;
      _this.infoForm=Object.assign({}, row);

      this.roleListLoading = true;
      let param = Object.assign({},{id: row.id});
      console.log(param)
      this.$ajax({
        method: "post",
        url: "/role/queryRoleUserList",
        data: param
      }).then(function(resultData) {
        console.log(resultData)
        _this.roleData = resultData.data.data;
        _this.roleListLoading = false;
        console.log(_this.roleData);
      }).catch(function (resultData) {
        _this.roleData = [];
        _this.roleListLoading = false;
      });
    },
    //显示新增界面
    handleAdd: function() {
      this.addFormVisible = true;
    },
    //新增
    addSubmit: function() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          //主机构必填提示
          if (this.addForm.name == "") {
            this.$message({
              message: "请填写登录名",
              type: "error"
            });
            return;
          }
          //密码必填提示
          if (this.addForm.password == "") {
            this.$message({
              message: "请填写密码",
              type: "error"
            });
            return;
          }
          //手机号必填提示
          if (this.addForm.phone == "") {
            this.$message({
              message: "请填写手机号",
              type: "error"
            });
            return;
          }
          //拼地址必填提示
          let value = this.addForm.address
          for (var i = 0;i<value.length;i++){
            this.address=this.address+CodeToText[value[i]];
          }
          console.log(this.address)
          this.address=this.address+this.addForm.address2
          if (this.address == "") {
            this.$message({
              message: "请填写地址",
              type: "error"
            });
            return;
          }
          //处理图片地址

          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.addLoading = true;
            let param = Object.assign({}, {
              name:this.addForm.name,
              password:this.addForm.password,
              address: this.address,
              phone: this.addForm.phone,
              avatar: this.addForm.picture
            });
            console.log(param);
            this.$ajax({
              method: "post",
              url: "/user/addSubmit",
              data: param
            }).then(res => {
              this.addLoading = false;
              this.$message({
                message: "提交成功",
                type: "success"
              });
              this.$refs["addForm"].resetFields();
              this.$refs.upload.clearFiles();
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
          // console.log("ids:" + param);
          this.$ajax({
            method: "post",
            url: "/user/delSysUserByUserId",
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
    }, //编辑
    editSubmit: function() {
      if (this.checked == true) {
        this.editForm.isLocked = "1";
      }
      if (this.checked == false) {
        this.editForm.isLocked = "0";
      }
      this.$refs.editForm.validate(valid => {
        if (valid) {
          this.$confirm("确认提交吗？", "提示", {}).then(() => {
            this.editLoading = true;
            let param = Object.assign({}, this.editForm);
            console.log(param);
            this.$ajax({
              method: "post",
              url: "/user/updateUser",
              data: param
            }).then(res => {
              this.editLoading = false;
              this.$message({
                message: "提交成功",
                type: "success"
              });
              this.$refs["editForm"].resetFields();
              this.$refs.upload2.clearFiles();
              this.editFormVisible = false;
              this.getResult(1);
            });
          });
        }
      });
    },
    //查看详细信息
    handleSelect: function(index, row) {

      this.selectFormVisible = true;
      this.selectForm = Object.assign({}, row);
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
