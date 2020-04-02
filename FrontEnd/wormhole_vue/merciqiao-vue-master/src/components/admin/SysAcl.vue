<template>
    <div class="table">
        <!-- 页面表格begin -->
        <div class="crumbs">
            <!-- 页面标题begin -->
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-tickets"></i> 角色资源管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <!-- 页面标题end-->
        <div class="container">
            <!-- 页面内容区begin -->
            <div id="app">
                <div style=" width:50%;float:left;">
                    <div>
                        角色名称：
                        <el-input v-model="s_rolename" label="角色名称" placeholder="角色名称" style="width:200px; heght:30px;" size="mini"></el-input>
                      <br/>
                        角色编码：
                        <el-input v-model="s_rolecode" label="角色编码" placeholder="角色编码" style="width:200px; heght:30px;" size="mini"></el-input>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <el-button type="success" icon="el-icon-search" @click="getResult(1)" size="mini" >搜索</el-button>

                    </div>

                    <!--表格数据及操作-->
                    <el-table :data="tableData" class="mgt20" border style="width: 100%" stripe ref="multipleTable" tooltip-effect="dark" @row-click="clickRow">
                        <!--勾选框-->
                        <el-table-column type="selection" style="width: 10%">
                            <template slot-scope="scope">
                                <el-radio v-model="curentroleid" :label="scope.row.id">{{null}}</el-radio>
                            </template>
                        </el-table-column>
                        <!--索引-->
                        <el-table-column type="index" :index="indexMethod" style="width: 10%">
                        </el-table-column>
                        <el-table-column prop="name" label="角色名称" style="width: 40%" sortable>
                        </el-table-column>
                        <el-table-column prop="id" label="角色编码" style="width: 40%">
                        </el-table-column>
                    </el-table>
                    <br>
                    <br>
                    <!--分页条total, sizes, prev, pager, next, jumper-->
                    <el-pagination @size-change="handleSizeChange" @current-change="getResult" :current-page="currentPage" :page-size="pageSize" layout="total, prev, pager, next" :total="roletotal">
                    </el-pagination>
                </div>
                <div style=" width:45%;float:right;">
                    <el-button type="success" icon="el-icon-search" @click="saveRoleacl" size="mini" v-has="'/auth/resource/save'">保存</el-button>
                    <el-tree :data="treeData" class="mgt20" show-checkbox default-expand-all node-key="id" ref="tree" highlight-current :check-strictly=true :props="defaultProps">
                    </el-tree>
                </div>
            </div>
        </div>
        <!-- 页面内容区end-->
    </div>
    <!-- 页面表格end -->
</template>
<script>
export default {
    data() {
        return {
            tableData: [],
            roleTypeData: [],
            treeData: [],
            input: "",
            curentroleid: "",
            curentrow: null,
            //列表Loading加载
            listLoading: false,
            //添加按钮Loading加载
            addLoading: false,
            roletotal: 0,
            currentPage: 1,
            pageSize: 10,
            //检索字段
            s_rolename: "",
            s_rolecode: "",
            //     s_roleType:"",
            defaultProps: {
                children: 'children',
                label: 'name'
            },

        }
    },

    methods: {
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

                    _this.tableData = resultData.data.data;
                    _this.roletotal = resultData.data.count;
                    _this.listLoading = false;
                },
                function(resultData) {
                    // _this.tableData.message = "Local Reeuest Error!";
                    //console.log(resultData);
                }
                );

        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.getResult(1);

        },
        getresourceData() {
            var _this = this;
            //this.listLoading = true;

            let param = Object.assign({}, {});
            this.$ajax({
                method: "post",
                url: "/resource/getSysResourceList",
                data: param
            }).then(
                function(resultData) {
                  console.log(resultData)
                    _this.treeData = resultData.data.data;
                     console.log(_this.treeData);

                },
                function(resultData) {
                    // _this.tableData.message = "Local Reeuest Error!";
                    //console.log(resultData);
                }
                );
        },
        clickRow: function(row) {
            var _this = this;
            _this.roleuserSelect = [];
            _this.curentroleid = row.id;
            _this.curentrow = row;
            let param = Object.assign({}, { roleId: _this.curentroleid });
            this.$ajax({
                method: "post",
                url: "/permission/getSysAclList",
                data: param
            }).then(
                function(resultData) {
                  console.log(resultData)
                    let list = [];

                    // resultData.data.data.forEach(item => {
                    //     list.push(item.resoureId);
                    // });
                    list = resultData.data.data
                    //alert(list);
                    console.log(JSON.stringify(list))
                    _this.$refs.tree.setCheckedKeys(list);

                },
                function(resultData) {
                    // _this.tableData.message = "Local Reeuest Error!";
                    //console.log(resultData);
                }
                );


        },

        saveRoleacl: function() {
            var _this = this;
            if (this.curentroleid == "") {
                alert("请选择要添加的角色，单击选择行。");
            }
            var sellist = this.$refs.tree.getCheckedKeys();
            //    var treeNodes = this.$refs.tree.getCheckedNodes(true);
            //    for(var i = 0; i < treeNodes.length; i++) {
            //    if(sellist.indexOf(treeNodes[i].parentId)<0){
            //     sellist.push(treeNodes[i].parentId);
            //    }
            //    }
            var roleid = this.curentroleid;
            if (sellist.length > 0) {

                this.listLoading = true;
                let param = Object.assign({}, { id: 0, roleId: roleid, resourceids: sellist });
                this.$ajax({
                    method: "post",
                    url: "/permission/insertSysAcl",
                    data: param
                }).then(
                    function(resultData) {
                        alert(resultData.data.message);
                        _this.clickRow(_this.curentrow);

                    }
                    );
            }

        }

    },
    mounted() {
        //获取列表
        this.getResult(1);
        this.getresourceData();


    }
};
</script>
<style>
/* #app {
        font-family: Helvetica, sans-serif;
        text-align: center;
    } */

#roleuser {
    font-family: Helvetica, sans-serif;
    text-align: center;
}

el-input {
    width: 200px;
    height: 50px;
}
</style>
