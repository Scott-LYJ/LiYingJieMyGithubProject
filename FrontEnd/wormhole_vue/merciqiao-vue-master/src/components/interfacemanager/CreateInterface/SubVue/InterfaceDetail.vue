<template>
    <div>
      <br/>
      <br/>
      <el-form ref="interfaceDetailForm" :model="interfaceDetailForm" label-width="80px" :rules="rules">
        <el-form-item label="名称:" style="width: 200px" prop="name">
          <el-input v-model="interfaceDetailForm.name" ></el-input>
        </el-form-item>
        <el-form-item  label=" 接口分组:">
         <el-select v-model="interfaceDetailForm.groupId" placeholder="请选择" >
          <el-option
            v-for="item in groupIds"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="是否启用">
          <el-switch v-model="interfaceDetailForm.status"
                     active-value="1"
                     inactive-value="0"
          ></el-switch>
        </el-form-item>
        <el-form-item label="是否鉴权">
          <el-radio-group v-model="interfaceDetailForm.isAuth">
            <el-radio :label="1">鉴权</el-radio>
            <el-radio :label="0">不鉴权</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="鉴权版本" style="width: 300px" >
          <el-input v-model="interfaceDetailForm.authVersion" :disabled="!interfaceDetailForm.isAuth"></el-input>
        </el-form-item>
        <el-form-item label="是否登录">
          <el-radio-group v-model="interfaceDetailForm.isLogin">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="接口描述" prop="description">
          <el-input type="textarea" v-model="interfaceDetailForm.description"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSave('interfaceDetailForm')">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
    export default {
        name: "InterfaceDetail",
        data() {
          return {
            groupIds: '',
            interfaceDetailForm: {
              name: '',
              groupId: '',
              description: '',
              isAuth: '',
              status: true,
              isLogin: '',
              authVersion: '',
            },
            rules: {
              name: [
                {required: true, message: '请输入API名称', trigger: 'blur'},
                {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
              ],
              description: [
                {required: true, message: '请输入API描述', trigger: 'blur'},
              ],
            }
          }
        },
        methods: {
          onSave(formName) {
            this.$refs[formName].validate((valid) => {
              if (valid) {
                var _this = this
                const h = this.$createElement;

                this.$notify({
                  title: '提示',
                  message:  '接口信息保存成功',
                  type: 'success'
                });
                console.log('save!');
                this.$emit("interfaceDetailSave",this.interfaceDetailForm)
                this.$emit('isSave',true)
              } else {
                console.log('error submit!!');
                return false;
              }
            });
          },

          getVal(){
            return this.interfaceDetailForm;
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
            }).then(resultData=> {

              console.log(resultData)
              let dataList =  resultData.data.data
              this.groupIds=dataList
              console.log(this.groupIds)
            });


          },
        },
      mounted(){
          this.getGroup()
      }
    }
</script>

<style scoped>

</style>
