<template>
    <div>
      <br/>
      <br/>
      <el-form ref="requestParamsForm" :model="requestParamsForm" label-width="80px">

        <el-button type="success" @click="AddListRow()" round>添加参数信息</el-button>
        <el-table :data="requestParamsForm.tableData" size="mini"  highlight-current-row border  max-height="400"   class="el-tb-edit mgt20" ref="multipleTable" tooltip-effect="dark" >
          <el-table-column type="index" :index="indexMethod" >
          </el-table-column>

          <el-table-column  label="参数名称" width="200">
            <template slot-scope="scope">
              <!--<el-input v-model="scope.row.MROCardName ">-->

                <!--<i slot="suffix" @click="openCardCode('MROCardName','MROCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
              <!--</el-input>-->
              <el-input v-model="scope.row.name" auto-complete="off" ></el-input>

            </template>
          </el-table-column>
          <el-table-column label="参数类型" width="200">
            <template slot-scope="scope">
              <!-- {{ scope.row.MROAmount }}-->
              <!--<el-input v-model="scope.row.MROAmount" type="number"></el-input>-->
              <el-select v-model="scope.row.typeName" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>

            </template>
          </el-table-column>
          <el-table-column label="请求示例" width="320">
            <template slot-scope="scope">
              <!--<el-input v-model="scope.row.DZCardName">-->
                <!--<i slot="suffix" @click="openCardCode('DZCardName','DZCardCode',scope.$index)" class="el-input__icon el-icon-search"></i>-->
              <!--</el-input>-->
              <el-input type="textarea" v-model="scope.row.example"></el-input>

            </template>
          </el-table-column>
          <el-table-column label="参数描述" width="320">
            <template slot-scope="scope">
              <!--   {{ scope.row.DZAmount }}-->
              <!--<el-input v-model="scope.row.DZAmount"  type="number"></el-input>-->
              <el-input type="textarea" v-model="scope.row.description"></el-input>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="100"  fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-form-item>
          <el-button type="primary" @click="onSave()">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
    export default {
        name: "RequestParams",
        data() {
          return {
            //
            options: [{
              value: 'java.lang.Integer',
              label: 'java.lang.Integer'
            }, {
              value: 'java.lang.String',
              label: 'java.lang.String'
            }, {
              value: 'java.util.Map',
              label: 'java.util.Map'
            }, {
              value: 'java.lang,Long',
              label: 'java.lang,Long'
            }, {
              value: 'java.lang.Float',
              label: 'java.lang.Float'
            }],
            //
            groupIds:'',
            requestParamsForm: {
              tableData:[],
              size:'',
            },
          }
        },
        methods: {
          //
          //table序号
          indexMethod(index) {
            return index + 1;
          },
          //
          AddListRow() {
            this.requestParamsForm.tableData.push({
              "name":"",
              "typeName":"",
              "example":"",
              "description":"",
            });
          },

          //
          handleDelete(index) {
            this.requestParamsForm.tableData.splice(index, 1);
          },

          //
          onSave() {
            var _this = this
            // const h = this.$createElement;

            console.log(this.requestParamsForm)
            this.requestParamsForm.size = this.requestParamsForm.tableData.length;
            // console.log("111111111")
            // let status = this.requestParamsForm.status ? '1':'0';
            // console.log("status",status)
            // this.requestParamsForm.status = status
            this.$emit("requestParamsSave",this.requestParamsForm)
            // this.$emit('isSave',true)
            this.$notify({
              title: '提示',
              message:  '接口信息保存成功',
              type: 'success'
            });
            console.log('save!');
          },
          getVal(){
            return this.requestParamsForm;
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
