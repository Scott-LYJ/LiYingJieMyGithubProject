<template >
  <div>
    <div style="height: 200px;width: 200px;position: absolute;right: 200px;top: 100px" ref="icon">
      <div style="width: 200px;height: 170px;" class="user-avator">
        <el-form ref="icon">
          <img :src="userIcon">
        </el-form>
      </div>

    </div>
    <div style="width: 200px;height: 170px;position: absolute;right: 280px;top: 300px">
      <el-upload
        class="upload-demo"
        action="/user/upload"
        name="picture"
        :before-upload="beforeUpload"
        :on-success="handleSuccess"
        :show-file-list="true"
       >
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg文件，且不超过500kb</div>
      </el-upload>
    </div>
    <div class="container form02">
      <el-form :label-position="labelPosition" :rules="rules" ref="editUser" :label-width="labelWidth" :inline="false" :model="editUser" class="demo-form-inline">
        <el-form-item label="id" prop="id">
          <el-input v-model="editUser.id"  :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="登录名" prop="name">
          <el-input v-model="editUser.name" ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" min="0" v-model="editUser.password"  :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="" prop="">
          <el-checkbox v-model="checked">是否修改密码</el-checkbox>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input   v-model="editUser.address" placeholder="请输入地址"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input  v-model="editUser.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="创建时间" prop="cdt">
          <el-date-picker  v-model="editUser.cdt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
        </el-form-item>
        <el-form-item label="修改时间" prop="udt">
          <el-date-picker  v-model="editUser.udt" type="date"  placeholder="选择日期" value-format="yyyy-MM-dd" disabled> </el-date-picker>
        </el-form-item>

        <el-form-item label=" ">
          <el-button type="primary" @click="onSubmit2">修改</el-button>
        </el-form-item>
      </el-form>
    </div>


    <!--修改密码界面-->
    <el-dialog title="修改密码" :rules="passwordRules" ref="editPassword" :visible.sync="checked" :model="editPassword" :close-on-click-modal="false">
      <el-form :inline="true"  label-width="80px" >

        <el-form-item label="原密码" prop="loginName">
          <el-input v-model="editPassword.orignPassword" @focus.capture.native='changePasswordTip(true)' @blur.capture.native='changePasswordTip(false)' auto-complete="off" type="password" placeholder='请输入旧密码进行验证' ></el-input>
          <div style="position: absolute">
            <verify-pass-word-tip :password="editPassword.orignPassword" :isShowTip = 'editPassword.isShowTip'></verify-pass-word-tip>
          </div>
        </el-form-item>
        <el-button type="success" icon="el-icon-check" v-model="this.editPassword.orignpassword" circle @click="check"></el-button>
        <br/>
        <el-form-item label="新密码" prop="password" >
          <el-input v-model="editPassword.password" @focus.capture.native='changePasswordTip2(true)' @blur.capture.native='changePasswordTip2(false)' auto-complete="off" type="password" placeholder='请输入新密码' :disabled="passwordCheck"></el-input>
          <div style="position: absolute">
            <verify-pass-word-tip :password="editPassword.password" :isShowTip = 'editPassword.isShowTip2'></verify-pass-word-tip>
          </div>        </el-form-item>
        <br/>
        <el-form-item label="确认密码" prop="mobile" >
          <el-input v-model="editPassword.repassword" @focus.capture.native='changePasswordTip3(true)' @blur.capture.native='changePasswordTip3(false)' auto-complete="off" type="password" placeholder='请确认密码' :disabled="passwordCheck"></el-input>
          <div style="position: absolute">
            <verify-pass-word-tip :password="editPassword.repassword" :isShowTip = 'editPassword.isShowTip3'></verify-pass-word-tip>
          </div>        </el-form-item>
        <br/>
        <br/>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="passwordCancle">取消</el-button>
        <el-button type="primary" @click="passwordSubmit">提交</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<style lang="scss">
  // 设置输入框的宽度

  .form02 {
    .el-form-item__content {
      width: 430px;
      .el-select {
        width: 430px;
      }
    }
  }
  .form03 {
    .el-form-item__content {
      width: 220px;
      .el-select {
        width: 220px;
      }
    }
  }
  .el-checkbox-group .el-checkbox {
    margin-left: 0;
    // padding-right: 30px;
  }
  .el-checkbox{
    width:173px;
  }
  .user-avator{
    margin-left: 15px;
  }
  .user-avator img{
    display: block;
    width:170px;
    height:170px;
    border-radius: 50%;
  }
</style>
<script>
  import {formatDate} from '../../common/date.js'
  import verifyPassWordTip from './verifyPassWordTip'

  export default {
    name: "UserEdit",
    components: {
      verifyPassWordTip,
    },
    data() {

      return {
        isLocked:"0",
        passwordCheck:true,
        editPassword:{
          isShowTip: false,
          isShowTip2: false,
          isShowTip3: false,
          orignPassword:"",
          password:"",
          repassword:""
        },
        update:true,
        userIcon:this.$common.getSessionStorage('icon'),
        checked:false,
        editUser: {
          //表单对象
          id:"",
          name: "",
          password: "",
          address: "",
          phone:"",
          cdt:"",
          udt:"",
          avatar:this.userIcon,
        },
        labelPosition: "right", //lable对齐方式
        labelWidth: "80px", //lable宽度
        rules:  {
          name: [
            { required: true, message: "请输入登录名", trigger: "blur" },

          ],
          phone: [{ required: true, message: "请输入手机号", trigger: "blur"},
            // { min: 7, max: 13, message: "长度在 7 到 13 个字符"}
            ],
          address: [
            { required: true, message: "请输入地址", trigger: "blur" }
          ],
        },
        passwordRules:  {
          orignPassword: [
            { required: true, message: "请输入原密码", trigger: "blur" },

          ],
          password: [{ required: true, message: "请输入新密码", trigger: "blur"},
            // { min: 7, max: 13, message: "长度在 7 到 13 个字符"}
          ],
          repassword: [
            { required: true, message: "请确认密码", trigger: "blur" }
          ],
        }
      };
    },
    computed:{

      geticon(val){
        //let path='E://ScottProject//LiYingJieMyGithubProject//image'
        // require('C://tmp//'+item.url+'.png')
        let userIcon = this.$common.getSessionStorage('icon');
        console.log("图片",userIcon)
        return userIcon ? userIcon:'/static/img/user/hongbao.png'
        //return '/static/img/user/hongbao.png'
      },
    },
    methods: {
      passwordSubmit(){
        if (""==this.editPassword.repassword){
          this.$message({
            message: "确认密码不能为空",
            type: "error"
          });
        }
        this.editUser.password = this.editPassword.password;
        this.checked=false;
       // this.$refs["editPassword"].resetFields();
        this.isLocked="1";
       console.log(this.isLocked)
      },
      passwordCancle(){
        this.editUser.password = this.editPassword.password;
        this.$refs["editPassword"].resetFields();

      },
      changePasswordTip(isShow) {
        if (isShow) {
          this.editPassword.isShowTip = true;
        } else {
          this.editPassword.isShowTip = false;
        }
      },
      changePasswordTip2(isShow) {
        if (isShow) {
          this.editPassword.isShowTip2 = true;
        } else {
          this.editPassword.isShowTip2 = false;
        }
      },
      changePasswordTip3(isShow) {
        if (isShow) {
          this.editPassword.isShowTip3 = true;
        } else {
          this.editPassword.isShowTip3 = false;
          if (this.editPassword.password==this.editPassword.repassword){
            return
          }else{
            this.$alert('两次密码不一致，请再次输入密码', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.editPassword.repassword="";
              }
            });
          }
        }

      },
      check(){
        let param = {
          name:this.editUser.name,
          password : this.editPassword.orignPassword
        }
        this.$ajax({
          method: "post",
          url: "/user/checkPassword",
          data: param
        }).then(res => {
          this.addLoading = false;
          console.log(res)
          if (res.data.status=="200") {
            this.$message({
              message: "验证成功",
              type: "success"
            });
            this.passwordCheck=false;
          }else{
            this.$message({
              message: "验证失败请重新验证",
              type: "error"
            });
            this.passwordCheck=true;

          }
          //this.$refs["addForm"].resetFields();
          //this.$refs.upload.clearFiles();
        });
      },
      //文件上传成功的钩子函数
      handleSuccess(res, file) {
        this.$message({
          type: 'info',
          message: '图片上传成功',
          duration: 6000
        });
        console.log(file)
        if (file.response.status=='200') {
          console.log(file.response.data)
          //this.editForm.picture = file.response.data; //将返回的文件储存路径赋值picture字段
         // this.userIcon=this.$common.setSessionStorage("icon",file.response.data)
          this.userIcon=file.response.data;
          this.editUser.picture = file.response.data;
        }
      },
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

      onSubmit1() {
        this.$message({
          type: "success",
          message: "表单提交成功"
        });
      },
      //表单2提交
      onSubmit2() {
        this.$refs.editUser.validate(valid => {

          if (valid) {
            let param={
              id:this.editUser.id,
              name:this.editUser.name,
              password:this.editUser.password,
              address:this.editUser.address,
              phone:this.editUser.phone,
              picture:this.editUser.picture,
              isLocked:this.isLocked
            }

            this.$ajax({
              method: "post",
              url: "/user/updateUser",
              data: param
            }).then(res => {
              this.addLoading = false;
              this.$message({
                message: "提交成功",
                type: "success"
              });
          })
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      onSubmit3() {
        this.$message({
          type: "success",
          message: "表单提交成功"
        });
      },

    },
    mounted() {
      console.log(this.$common.getSessionStorage("userInfo"))
      this.editUser.id=this.$common.getSessionStorage("id");
      this.editUser.name=this.$common.getSessionStorage("username");
      this.editUser.password=this.$common.getSessionStorage("token");
      this.editUser.address=this.$common.getSessionStorage("address");
      this.editUser.phone=this.$common.getSessionStorage("phone");

      //时间处理
      console.log(this.$common.getSessionStorage("cdt"))
      let cdt = this.$common.getSessionStorage("cdt")
      let udt = this.$common.getSessionStorage("udt")
      this.editUser.cdt=cdt
      this.editUser.udt=udt
      //图片处理
      //this.userIcon = this.$common.getSessionStorage('icon');
    }
  };
</script>
