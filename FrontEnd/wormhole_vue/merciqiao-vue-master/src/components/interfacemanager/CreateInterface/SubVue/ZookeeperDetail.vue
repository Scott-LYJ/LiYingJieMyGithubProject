<template>
  <div>
    <el-row >
      <el-col :offset="2">
        <el-table
          :data="zkAddress"
          style="width: 100%">
          <el-table-column
            label="IP"
            width="480">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="danger"
                @click="deleteZk(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <br/>
    <br/>
    <el-form :model="zookeeperDetailForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="zk地址" prop="zookeeper">
        <el-input placeholder="请输入内容" v-model="zookeeperDetailForm.zookeeper" class="input-with-select">
          <el-button slot="append" icon="el-icon-s-promotion" @click="zkTest">zk测试</el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="pass">
        <el-input type="password" v-model="zookeeperDetailForm.pass" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <!--<el-button type="primary" @click="submitForm('ruleForm')">测试</el-button>-->
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSave()">保存</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

    export default {
      name: "ZookeeperDetail",
      data() {

        var checkZookeeper = (rule, value, callback) => {
          if (!value) {
            return callback(new Error('zk不能为空'));
          }
        };
        var validatePass = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请输入密码'));
          } else {
          }
        };
        return {
          zkAddress: [],
          zookeeperDetailForm: {
            pass: '',
            zookeeper: '',

          },
          rules: {
            pass: [
              { validator: validatePass, trigger: 'blur' }
            ],
            zookeeper: [
              { validator: checkZookeeper, trigger: 'blur' }
            ]
          }
        };
      },
      methods: {
          onSave(){
            this.$notify({
              title: '提示',
              message:  'zk信息保存成功',
              type: 'success'
            });
            console.log(this.zkAddress);
            this.zookeeperDetailForm.zookeeper=this.zkAddress[0]
            this.$emit("zookeeperDetailSave",this.zookeeperDetailForm)
          },
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              alert('submit!');
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        },
        resetForm(formName) {
          this.$refs[formName].resetFields();
        },
        zkTest(){
            if (this.zookeeperDetailForm.pass==''){
              this.$notify({
                title: '提示',
                message:  '请输入密码',
                type: 'success'
              });
            }
          else{

              const loading = this.$loading({
                lock: true,
                text: '正在初始化ZK配置,请耐心等待......',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
              });


              this.$ajax ({
                method: "get",
                url: "/interface/zkConfig/config?zookeeper="+this.zookeeperDetailForm.zookeeper+"&pass="+this.zookeeperDetailForm.pass,
              }).then((resultData) => {

                this.$ajax({
                  method: "post",
                  url: "/interface/zkConfig/createDubboModel",
                  data:{"zookeeper":this.zookeeperDetailForm.zookeeper,
                  "groupId":11,
                  "serviceVersion":11,
                  "createBy":""}
                }).then((res) => {
                  console.log(res)
                  this.$common.setSessionStorage("isZkEnable",1);
                })
                console.log(resultData)
                let code = resultData.data.code;
                if(code == 0){
                  this.$notify({
                    title: '提示',
                    message:  '保存成功',
                    type: 'success'
                  });
                   loading.close();
                }else{
                  let error = resultData.data.error
                  this.$notify.error({
                    title: '错误',
                    message: error
                  });
                }
                this.getConfigs();

                setTimeout(() => {
                  loading.close();
                }, 500);
              });
          }
        },
        getConfigs(){

          this.$ajax({
            method: "get",
            url: "/interface/zkConfig/configs",
            data: {}
          }).then((res) =>{
            let zkAddress = res.data.data;
            console.log("返回的zk地址:",zkAddress);
            this.zkAddress = zkAddress;
            //存入局部变量
            this.$common.setSessionStorage("isZkEnable",0);
            console.log(this.$common.getSessionStorage("isZkEnable"))
          });
        },



        deleteZk(index,value){
          let encodedZk = encodeURI(value);
          let params = {
            "zk":this.zookeeperDetailForm.zookeeper,
            "password":this.zookeeperDetailForm.pass
          };

          this.$ajax({
            method: "get",
            url: "/interface/zkConfig/zk/del?zk="+encodedZk+"&password="+this.zookeeperDetailForm.pass,
            data: params
          }).then((res)=> {
            let code = res.data.code;
            if(code == 0){
              this.$message.success("删除成功");
            }else{
              let error = res.data.error
              this.$message.error(error);
            }
            this.getConfigs();
          });
        }

      },
      mounted(){
        this.getConfigs();
      }
    }
</script>

<style scoped>

</style>
