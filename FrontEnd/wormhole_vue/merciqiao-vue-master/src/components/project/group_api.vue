<!--export default {-->
<!--name: "GroupDetail"-->
<!--}-->
<template>

  <div style="margin-top: 15px;">
    <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
      <el-select v-model="select" slot="prepend" placeholder="请选择">
        <el-option label="编号" value="id"></el-option>
        <el-option label="API名称" value="name"></el-option>
      </el-select>
      <el-button slot="append" @click="getResult" icon="el-icon-search"></el-button>
    </el-input>

    <el-table
      border
      height="270"
      :data="tableData"
      style="width: 100%">
      <el-table-column type="index" :index="indexMethod">
      </el-table-column>
      <el-table-column
        label="编号"
        sortable
        prop="id">
      </el-table-column>
      <el-table-column
        label="API名称"
        prop="name">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>名称: {{ scope.row.name }}</p>
            <p>API描述: {{ scope.row.description }}</p>
            <p>创建时间: {{ scope.row.cdt }}</p>
            <p>最近更新时间: {{ scope.row.udt }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="创建人"
        prop="createBy">
      </el-table-column>
      <el-table-column
        label="最近修改人"
        prop="updateBy">
      </el-table-column>
      <el-table-column
        label="版本"
        sortable
        prop="authVersion">
      </el-table-column>
      <el-table-column
        label="授予"
        width="200">
        <template slot-scope="scope">
          <el-switch
            @change="apiAuthorized($event,scope.row)"
            style="display: block"
            v-model="scope.row.groupauthorized"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="授予"
            inactive-text="解除">
          </el-switch>
        </template>
      </el-table-column>
    </el-table>

  </div>

</template>

<script>
  export default {
    name: "group_api",
    props:['message'],
    watch:{
      message:function(newVal,oldVal){
        console.log("newVal",newVal)
        this.groupId = newVal
        console.log("groupId",this.groupId)
        this.getResult();
      }
    },
    data() {
      return {
        update:false,
        groupId:'',
        input3: '',
        select: '',
        tableData: [
        ],
        search: ''
      }
    },
    methods: {
      //授权
      apiAuthorized(data,row){
        console.log("3333333333333")
        console.log(data)
        console.log(row)
        let param={
          groupId:this.groupId,
          id:row.id
        }
        console.log(param)
        if (data){
          this.$ajax({
            method: "post",
            url: "/group/insertGroupAuthorized",
            data: param
          }).then(res => {
            this.addLoading = false;
            this.$message({
              message: "授权成功",
              type: "success"
            });
            this.getResult();
            // this.$emit('update', this.update)
          });
        }else{
          let param1={
            id:row.id
          }
          this.$ajax({
            method: "post",
            url: "/group/deleteGroupAuthorized",
            data: param1
          }).then(res => {
            this.addLoading = false;
            this.$message({
              message: "解除成功",
              type: "success"
            });
            this.getResult();
            // this.$emit('update', this.update)
          });

        }//over else
      },
      //table序号
      indexMethod(index) {
        return index + 1;
      },
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      getResult: function() {
        var _this = this;
        this.listLoading = true;
        let param = Object.assign(
          {},
          {
            name: this.select=="name"?this.input3:"",
            id: this.select=="id"?this.input3:"",
            group_id :this.groupId
          }
        );
        console.log(param)
        //delSysUserByUserId,querySysUserList
        this.$ajax({
          method: "post",
          url: "/group/queryApi",
          data: param
        }).then(function(resultData) {
          console.log(resultData)
          _this.tableData = resultData.data.data;
          _this.listLoading = false;
        });
      },
    },
    // mounted(){
    //   //初始加载
    //   this.getResult();
    // }
  }
</script>
<style>
  .el-select .el-input {
    width: 130px;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>
