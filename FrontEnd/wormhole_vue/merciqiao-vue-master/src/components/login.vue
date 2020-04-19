<template>
    <div class="login_wrapper" :style ="note">
        <div class="login">
            <el-form :model="formLogin" :rules="rules" ref="formLogin">
                <el-form-item>
                    <h2 class="title">WormHoleApi</h2>
                </el-form-item>
                <el-form-item prop="loginName">
                    <el-input v-model="formLogin.loginName"  placeholder="账号"><template slot="prepend"><i class="el-icon-user"></i></template></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="formLogin.password"  show-password placeholder="密码"><template slot="prepend"><i class="el-icon-lock"></i></template></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="verify">登录</el-button>
                    <span v-show="this.errorInfo.isShowError" class='error'>
                        {{this.errorInfo.text}}
                    </span>

                </el-form-item>

            </el-form>

        </div>
        <p class="bei">京ICP备18050367号-1</p>
        <p  class ='recover' @click="rollBackTables">如果登陆数据异常,点此恢复数据</p>

    </div>
</template>

<style lang="scss">
$input_width:300px;

.login_wrapper {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    .login {
        width: 460px;
        height: 296px;
        margin-top: -150px;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        .title {
            text-align: center;
            color: #505458;
        }
        .el-form-item__content {
            width: $input_width;
        }
        .el-button {
            width: $input_width;
        }
        .el-form {
            margin: 30px 80px auto 80px;
            .error {
                display: block;
                text-align: center;
                color: red;
            }
        }
    }
}
.recover{
    position:absolute;
    bottom:0px;
    cursor:pointer;
    color:#E6A23C;
    // display: none;
}
.bei{
    position:absolute;
    bottom:20px;
    cursor:pointer;
    color:#505458;
}
</style>
<script>
  import {formatDate} from '../common/date.js'
import apis from '../apis/apis';
export default {
    name: 'login',

    data() {
        return {
          fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
          isVerify:false,
            formLogin: {   //表单对象
                loginName: 'admin',
                password: '123456'
            },
            errorInfo: {
                text: '登陆失败,请重试',
                isShowError: false //显示错误提示
            },
          note: {
            backgroundImage: "url(" + require("../assets/background.gif") + ")",
            backgroundRepeat: "no-repeat",
            backgroundSize: "100% 100%",
            marginTop: "0px",
          },
          rules: {
            loginName: [
              { required: true, message: '请输入用户名', trigger: 'blur' },
            ],
            password: [
              { required: true, message: '请输入密码', trigger: 'blur' }
            ],
          }

        }
    },
    mounted() {
        let message=this.$route.query.message;
        if (message!=null){
          this.$alert(message, '警告', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${ action }`
              });
            }
          });
        }
        document.onkeydown = (event) => {
            var router=this.$route.path;
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode == 13&&this.$route.path=='/login') { // enter 键
                this.login();
            }
        };
         var loginLog = {
                ip: returnCitySN["cip"],
                city: returnCitySN["cname"] + '-' + '进入首页'
            };

            apis.shiroApi.loginLog(loginLog);
    },
    methods: {
      verify(){
        this.$refs['formLogin'].validate((valid) => {
          if (valid) {
            var _this = this
            var captcha1 = new TencentCaptcha('2005990633', function(res) {
              console.log(res)
              // res（用户主动关闭验证码）= {ret: 2, ticket: null}
              // res（验证成功） = {ret: 0, ticket: "String", randstr: "String"}
              if(res.ret === 0){
                _this.login()
              }
            });
            captcha1.show(); // 显示验证码
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },
      login() {
        var _this = this
        const loading = this.$loading({
          lock: true,
          text: '正在加载请稍后......',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        apis.shiroApi.loginIn(this.formLogin)
          .then(data=>{
            //监听事件
            if (data && data.data) {
              var json = data.data.result.data.data;
              console.log('json',json);
              if (data.data.result.status == 'SUCCESS') {
                this.$common.setSessionStorage('token', data.data.token);
                console.log('token',this.$common.getSessionStorage('token'))
                this.$common.setSessionStorage('username',json.userInfo.name);
                this.$common.setSessionStorage('id',json.userInfo.id);

                this.$common.setSessionStorage('cdt',formatDate(new Date(json.userInfo.cdt), 'yyyy-MM-dd hh:mm'));
                this.$common.setSessionStorage('udt',formatDate(new Date(json.userInfo.udt), 'yyyy-MM-dd hh:mm'));
                this.$common.setSessionStorage('phone',json.userInfo.phone);
                this.$common.setSessionStorage('avatar',json.userInfo.avatar);
                this.$common.setSessionStorage('address',json.userInfo.address);

                this.$common.setSessionStorage('userInfo',json.userInfo)
                console.log('userInfo',this.$common.getSessionStorage('userInfo'))
                this.$common.setSessionStorage('lev',json.sysRoleVoList);
                console.log('lev',this.$common.getSessionStorage('lev'))
                //存储按钮权限
                this.$store.dispatch("add_Permissions", data.data.permission);
                //存入菜单,渲染菜单
                this.$store.dispatch("add_Menus",json.sysMenuVoList);

                console.log('add_Menus',json.sysMenuVoList)
                //存入头像
                this.$common.setSessionStorage('icon',json.userInfo.avatar);
                console.log('icon',json.userInfo.avatar)


                //动态设置路由
                this.$store.dispatch("add_Routes", json.sysMenuVoList);
                console.log('add_Routes',json.sysMenuVoList)

                setTimeout(()=>{   //设置延迟执行
                  this.$router.replace({ path: "/index" });
                  loading.close();
                  },1000);

                var loginLog={
                  ip:returnCitySN["cip"],
                  city:returnCitySN["cname"]+'-'+json.userInfo.name+'-登陆'
                };

                apis.shiroApi.loginLog(loginLog);

                return;
              }

            }
            this.errorInfo.isShowError = true;
            this.$store.dispatch("loginLog",loginLog);

          }) .catch((err) => {
          console.log('error:', err);
          this.errorInfo.isShowError = true;
          this.errorInfo.text = '系统接口异常';
          loading.close();
        });
      },

         rollBackTables() {
            var text = '数据还原';
            apis.shiroApi.rollBackTables()
                .then(data => {
                    var alertText='';
                    if(data.data.status=='SUCCESS'){
                        text += '成功';
                        alertText=text+',请重新登陆';
                    }
                    else{
                        text += '失败';
                        alertText=text+',请重试';
                    }
                    this.$alert(alertText, '提示', {
                        confirmButtonText: '确定',
                    });
                    log(text);
                })
                .catch(e => {
                    this.$alert('数据还原异常,请重试', '提示', {
                        confirmButtonText: '确定',
                    });
                    text += '失败';
                    log(text);
                });
            console.log(text);

            function log(text){
                 var loginLog = {
                ip: returnCitySN["cip"],
                city: returnCitySN["cname"] + '-' + text
            };

            apis.shiroApi.loginLog(loginLog);
            }

        }
    }
}
</script>
