<template>
  <div class="pwdTipContent" v-show="isShowTip">
    <dl>
      <dt>
        <i class="iWeak" :class="{active:  degreeNumber >= 3 , normal: degreeNumber==1 || degreeNumber == 2}"></i>
        <i class="iMiddle" :class="{active:  degreeNumber >= 3, normal:  degreeNumber == 2}"></i>
        <i class="iStrong" :class="{active:degreeNumber>=3}"></i>
        <b class="degreeWeak" v-show='degreeNumber==1'>弱</b>
        <b class="degreeMiddle" v-show='degreeNumber==2'>中</b>
        <b class="degreeStrong" v-show='degreeNumber>=3'>强</b>
      </dt>
      <dd>
        <i :class="{'el-icon-warning': !bPwd, 'active': bPwd === false, 'el-icon-success': bPwd}"></i>8-24 位
      </dd>
    </dl>
  </div>
</template>
<script>
  export default {
    name: "verifyPassWordTip",
    props: {
      password: {
        type: String
      },
      isShowTip: {
        type: Boolean
      }
    },
    data() {
      return {
        bPwd: null,//密码值和长度的验证
        degreeNumber: 0//密码复杂度的验证
      };
    },
    watch: {
      password() {
        this.initPassWord();
      }
    },
    methods: {
      /**
       * 密码加载
       **/
      initPassWord() {
        if (!this.password) {
          this.$data.bPwd = null; //没有值标签背景置灰
          this.$data.degreeNumber=0;
        } else {
          let objReturn = this.passwordValid(this.password);
          if (objReturn) {
            this.$data.bPwd = objReturn.bPwd;
            this.$data.degreeNumber = objReturn.degreeNumber;
          }
        }
      },
      /**
       * 密码验证（密码复杂度：(1)大写字母(2)小写字母(3)数字(4)特殊符号，密码至少需包含上述情形中的三种）
       * @param valPWD输入的密码信息
       * @return 返回验证信息
       **/
      passwordValid(valPwd) {
        let msg = "";
        let degreeNumber = 0;
        let bPwd = false;

        if (valPwd == null || valPwd.length < 8 || valPwd.length > 24 || valPwd.trim().length == "") {
          bPwd = false;
        } else {
          bPwd = true;
        }
        let arrVerify = [
          {regName: 'Number', regValue: /^.*[0-9]+.*/},
          {regName: 'LowerCase', regValue: /^.*[a-z]+.*/},
          {regName: 'UpperCase', regValue: /^.*[A-Z]+.*/},
          {regName: 'SpecialCharacters', regValue: /^.*[^a-zA-Z0-9]+.*/}
        ];
        let regNum = 0;// 记录匹配的次数
        for (let iReg = 0; iReg < arrVerify.length; iReg++) {
          if (arrVerify[iReg].regValue.test(valPwd)) {
            regNum = regNum + 1;
          }
        }
        degreeNumber = regNum;
        if (regNum <= 2) {
          msg = "密码必须包含大小写字母和数字";
        }
        let objReturn = {
          msg: msg,
          degreeNumber: degreeNumber,
          bPwd: bPwd
        };
        return objReturn;
      }
    }
  }
</script>
<style>
  .pwdTipContent .el-icon-warning{
    color:gray;
  }
  .pwdTipContent .el-icon-warning.active{
    color:red;
  }
  .pwdTipContent i.gray{
    color: gray;
  }
  .pwdTipContent .degreeWeak , .pwdTipContent .degreeMiddle{
    color: red;
  }
  .pwdTipContent .el-icon-success{
    color:green;
  }
  .pwdTipContent .degreeStrong {
    color: green;
  }
  i.iWeak.normal, i.iMiddle.normal  {
    background: red;
  }
  i.iWeak.active {
    background: green;
  }
  i.iMiddle.active {
    background: green;
  }
  i.iStrong.active {
    background: green;
  }
  b.active {
    color: red;
  }
  .degreeStrong  {
    color: green;
  }

  .pwdTipContent dt i {
    width: 28px;
    height: 10px;
    display: inline-block;
    background-color: gray;
    margin-right: 10px;
  }

  .pwdTipContent {
    color: #61688a;
    position: absolute;
    min-width: 200px;
    padding: 7px 0 7px 15px;
    background: #fff;
    top: -147px;
    border: 1px solid #e4e7ed;
    border-radius: 4px;
    box-shadow: 0 4px 12px 0 rgba(0, 0, 0, 0.4);
    font-size: 12px;
  }

  .pwdTipContent:after {
    width: 0;
    height: 0;
    overflow: hidden;
    content: "";
  }
  .pwdTipContent:after {
    position: absolute;
    bottom: -10px;
    left: 20px;
    border-top: 10px solid #fff;
    border-right: 10px dashed transparent;
    border-left: 10px dashed transparent;
  }
  .pwdTipContent dd, .pwdTipContent dt{
    text-align: left;
  }
</style>
