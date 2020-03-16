<template>
  <div>


    <div style="margin-top: 15px; width: 500px;margin: auto;margin-bottom: 15px">
      <el-input placeholder="请输入内容" v-model="keyword"  class="input-with-select">
        <template slot="prepend">接口</template>
        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>
    </div>
  <div class="right-extra">
    <div id="select" clstag="thirdtype|keycount|thirdtype|select" class="m">
      <div class="mt">
        <h1>
          接口 -<strong>&nbsp;接口筛选</strong>
        </h1>
      </div>
      <div class="mc attrs">
        <div data-id="100001" class="brand-attr">
          <div class="attr">

            <el-tooltip  effect="dark" content="点击重置" placement="left-start">
              <div class="a-key" @click="login">是否登录：</div>

            </el-tooltip>

            <div class="a-values">
              <div class="v-tabs">
                <div class="tabcon">

                  <div >
                    <a href="javascript:void(0);" @click="searchOnlogin()" ref="login">需要登录</a>
                  </div>
                  <div  >
                    <a href="javascript:void(0);" @click="searchOnunlogin()" ref="unlogin">不需要登录</a>
                  </div>
                </div>
              </div>
            </div>
          </div>

        </div>
        <div data-id="100001" class="brand-attr">
          <div class="attr">
            <el-tooltip  effect="dark" content="点击重置" placement="left-start">

            <div class="a-key" @click="auth">是否鉴权：</div>
            </el-tooltip>
            <div class="a-values">
              <div class="v-tabs">
                <div class="tabcon">

                  <div>
                    <a href="javascript:void(0);" ref="isAuth"  @click="searchOnauth()">鉴权</a>
                  </div>
                  <div>
                    <a href="javascript:void(0);" ref="isUnAuth"  @click="searchOnunauth()">未鉴权</a>
                  </div>
                </div>
              </div>
            </div>
          </div>

        </div>
        <div data-id="100001" class="brand-attr">
          <div class="attr">
            <el-tooltip  effect="dark" content="点击重置" placement="left-start">

            <div class="a-key" @click="isstatus">是否启用：</div>
            </el-tooltip>
            <div class="a-values">
              <div class="v-tabs">
                <div class="tabcon">

                  <div>
                    <a href="javascript:void(0);" ref="status"  @click="searchOnstatus()">启用</a>
                  </div>
                  <div>
                    <a href="javascript:void(0);" ref="unstatus"  @click="searchOnunstatus()">禁用</a>
                  </div>
                </div>
              </div>
            </div>
          </div>

        </div>
        <div data-id="100002" class="prop-attrs">
          <div class="attr">
            <el-tooltip  effect="dark" content="点击重置" placement="left-start">

            <div class="a-key" @click="authv">接口版本：</div>
            </el-tooltip>
            <div class="a-values">
              <div class="v-fold">
                <ul class="f-list">
                  <li><a href="javascript:void(0);" @click="oneToFive" ref="isoneToFive">1-5</a></li>
                  <li><a href="javascript:void(0);" @click="fiveToTen" ref="isfiveToTen">5-10</a></li>
                  <li><a href="javascript:void(0);" @click="tenToMax"  ref="istenToMax">10以上</a></li>
                  <li><a href="javascript:void(0);"></a></li>
                  <li><a href="javascript:void(0);"></a></li>
                  <li><a href="javascript:void(0);"></a></li>
                  <li><a href="javascript:void(0);"></a></li><li><a href="javascript:void(0);"></a></li>
                  <li><a href="javascript:void(0);"></a></li>

                    <el-popover
                      placement="bottom"
                      width="120"
                      style="height: 120px"
                      >
                      &nbsp;&nbsp;&nbsp;
                      <el-input v-model="input1"  size="mini" style="width:50px;"></el-input>
                      -
                      <el-input v-model="input2"  size="mini" style="width:50px;"></el-input>
                      <div style="text-align: right; margin: 0">
                        <el-button size="mini" type="text" @click="reset">重置</el-button>
                        <el-button type="primary" size="mini" @click="Screening">筛选</el-button>
                      </div>
                      <!--<el-button  >删除</el-button>-->
                      <el-button type="info" icon="el-icon-edit" size="mini"  slot="reference" style="margin-top: -2px"></el-button>

                    </el-popover>



                </ul>
              </div>
            </div>
          </div>
        </div>
        <div data-id="100002" class="prop-attrs">
          <div class="attr">
            <el-tooltip  effect="dark" content="点击重置" placemenvisiblet="left-start">

            <div class="a-key" @click="isserviceVersion">服务版本：</div>
            </el-tooltip>
            <div class="a-values">
              <div class="v-fold">
                <ul class="f-list">
                  <li><a href="javascript:void(0);" ref="local"  @click="searchOnlocal()">1.0.0.local</a></li>
                  <li><a href="javascript:void(0);" ref="dev"  @click="searchOndev()">1.0.0.dev</a></li>
                  <li><a href="javascript:void(0);" ref="daily"  @click="searchOndaily()">1.0.0.daily</a></li>
                  <li><a href="javascript:void(0);" ref="prehub"  @click="searchOnprehub()">1.0.0.prehub</a></li>
                  <li><a href="javascript:void(0);" ref="online"  @click="searchOnonline()">1.0.0.online</a></li>

                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div id="filter">
      <div class="cls"></div>
      <div class="fore1">
        <dl class="order">
          <dt>排序：</dt>
          <dd>
            <a href="javascript:void(0)" @click="sort1" ref="interfaceSort">接口版本<i ref="interfaceSortUp" class="el-icon-top"></i><i ref="interfaceSortdown"class="el-icon-bottom"></i></a><b></b>
          </dd>
          <dd>
            <a href="javascript:void(0)" @click="sort2" ref="cdtSort">创建时间<i  ref="cdtSortup" class="el-icon-top"></i><i ref="cdtSortdown" class="el-icon-bottom"></i></a><b></b>
          </dd>

        </dl>
        <dl class="activity">
          <dd></dd>
        </dl>
        <div class="pagin pagin-m">
          <span class="text"><i>当前{{currPage}}页</i>/共{{countPage}}页</span>
          <a href="javascript:void(0)" @click="changePage(-1)" class="prev">上一页<b></b></a>
          <a href="javascript:void(0)" @click="changePage(1)" class="next">下一页<b></b></a>
        </div>
        <div class="total">
			<span>共<strong>{{count}}</strong>个接口
			</span>
        </div>
        <span class="clr"></span>
      </div>
    </div>
  </div>
  </div>
</template>

<script>

    export default {
      name: "indexHeader",
      data() {
        return {
          count:'',
          bool1:true,
          bool:true,
          cdtSortdown:'',
          cdtSortup:'',
          cdtSort:'',
          interfaceSortdown:'',
          interfaceSortUp:'',
          interfaceSort:'',
          istenToMax:'',
          isoneToFive:'',
          isfiveToTen:'',
          input1:'',
          input2:'',
          local:'',
          dev:'',
          online:'',
          daily:'',
          prehub:'',
          unstatus:'',
          isUnAuth:'',
          unlogin:'',
          keyword: '',
          isLogin:'',
          isAuth:'',
          status:'',
          serviceVersion:'',
          authVersionStr:'',
          psort:'',
          currPage:'1',
          api:[],
          countPage:'',
        }
      },
      mounted(){
        this.search();
      },
      methods: {
        changePage(data){
          if (data>0) {
            if (this.currPage<this.countPage){
              this.currPage=this.currPage+1
              console.log(this.currPage)
              this.search()
            }else{
              this.search()
            }
          }else{
            if (this.currPage>1){
              this.currPage=this.currPage-1
              console.log(this.currPage)
              this.search()
            }else{
              this.search()
            }
          }
        },
        sort1(){
          if (this.bool==true) {
            this.psort = '1'
            if (this.psort == "1") {
              this.$refs.interfaceSort.style.color = "";
              this.$refs.interfaceSortUp.style.color = "#ff250f";
              this.$refs.interfaceSortdown.style.color = "";
              this.search();
              this.bool = false
            }
          }else{
            this.psort = '2'
            if (this.psort == "2") {
              this.$refs.interfaceSort.style.color = "";
              this.$refs.interfaceSortUp.style.color = "";
              this.$refs.interfaceSortdown.style.color = "#ff250f";
              this.search();
              this.bool = true
            }
          }
        },
        sort2(){
          if (this.bool1==true) {
            this.psort = '3'
            if (this.psort == "3") {
              this.$refs.cdtSort.style.color = "";
              this.$refs.cdtSortup.style.color = "#ff250f";
              this.$refs.cdtSortdown.style.color = "";
              this.search();
              this.bool1 = false
            }
          }else{
            this.psort = '4'
            if (this.psort == "4") {
              this.$refs.cdtSort.style.color = "";
              this.$refs.cdtSortup.style.color = "";
              this.$refs.cdtSortdown.style.color = "#ff250f";
              this.search();
              this.bool1 = true
            }
          }
        },
        authv(){
          this.authVersionStr=''
          this.$refs.isoneToFive.style.color= "";
          this.$refs.isfiveToTen.style.color= "";
          this.$refs.istenToMax.style.color= "";
          this.search();
        },
        oneToFive(){
          this.authVersionStr='';
          this.reset();
          this.$refs.isoneToFive.style.color= "#ff250f";
          this.$refs.isfiveToTen.style.color= "";
          this.$refs.istenToMax.style.color= "";
          this.authVersionStr='1-5'
          this.search()
        },
        fiveToTen(){
          this.authVersionStr='';
          this.reset();
          this.$refs.isoneToFive.style.color= "";
          this.$refs.isfiveToTen.style.color= "#ff250f";
          this.$refs.istenToMax.style.color= "";
          this.authVersionStr='5-10'
          this.search()
        },
        tenToMax(){
          this.authVersionStr='';
          this.reset();
          this.$refs.isoneToFive.style.color= "";
          this.$refs.isfiveToTen.style.color= "";
          this.$refs.istenToMax.style.color= "#ff250f";
          this.authVersionStr='10-'
          this.search()
        },
        reset(){
          this.input1=''
          this.input2=''
          this.authVersionStr=''
        },
        Screening(){
          this.authVersionStr=''
          this.authVersionStr=this.input1+"-"+this.input2;
          console.log(this.authVersionStr)
          this.search()
        },
        //
        login(){
          this.isLogin = '';
          this.$refs.login.style.color = "";
          this.$refs.unlogin.style.color= "";
          this.search();
        },
        auth(){
          this.isAuth = '';
          this.$refs.isAuth.style.color = "";
          this.$refs.isUnAuth.style.color= "";
          this.search();
        },
        isstatus(){
          this.status = '';
          this.$refs.status.style.color = "";
          this.$refs.unstatus.style.color= "";
          this.search();
        },
        isserviceVersion(){
          this.serviceVersion = '';
          this.$refs.local.style.color= "";
          this.$refs.dev.style.color=''
          this.$refs.online.style.color=''
          this.$refs.prehub.style.color=''
          this.$refs.daily.style.color=''
          this.search();
        },

        //
        searchOnlocal(){
          this.serviceVersion="1.0.0.local"
          this.$refs.local.style.color= "#ff250f";
          this.$refs.dev.style.color=''
          this.$refs.online.style.color=''
          this.$refs.prehub.style.color=''
          this.$refs.daily.style.color=''
          this.search();
        },
        searchOndev(){
          this.serviceVersion="1.0.0.dev"
          this.$refs.dev.style.color= "#ff250f";
          this.$refs.local.style.color=''
          this.$refs.online.style.color=''
          this.$refs.prehub.style.color=''
          this.$refs.daily.style.color=''
          this.search();
        },
        searchOnonline(){
          this.serviceVersion="1.0.0.online"
          this.$refs.online.style.color= "#ff250f";
          this.$refs.local.style.color=''
          this.$refs.dev.style.color=''
          this.$refs.prehub.style.color=''
          this.$refs.daily.style.color=''
          this.search();
        },
        searchOnprehub(){
          this.serviceVersion="1.0.0.prehub"
          this.$refs.prehub.style.color= "#ff250f";
          this.$refs.local.style.color=''
          this.$refs.dev.style.color=''
          this.$refs.online.style.color=''
          this.$refs.daily.style.color=''
          this.search();
        },
        searchOndaily(){
          this.serviceVersion="1.0.0.daily"
          this.$refs.daily.style.color= "#ff250f";
          this.$refs.local.style.color=''
          this.$refs.dev.style.color=''
          this.$refs.online.style.color=''
          this.$refs.prehub.style.color=''
          this.search();
        },

        //
        searchOnstatus(){
          this.status = '1'
          if (this.status=='1'){
            this.$refs.status.style.color= "#ff250f";
            this.$refs.unstatus.style.color=''
            this.search();
          }
        },
        searchOnunstatus(){
          this.status = '0'
          if (this.status=='0'){
            this.$refs.unstatus.style.color= "#ff250f";
            this.$refs.status.style.color=''
            this.search();
          }
        },
        searchOnauth(){
          this.isAuth = '1'
          if (this.isAuth=='1'){
            this.$refs.isAuth.style.color= "#ff250f";
            this.$refs.isUnAuth.style.color=''
            this.search();
          }
        },
        searchOnunauth(){
          this.isAuth = '0'
          if (this.isAuth=='0'){
            this.$refs.isUnAuth.style.color= "#ff250f";
            this.$refs.isAuth.style.color=''
            this.search();
          }
        },
        searchOnunlogin(){
          console.log(1111);
          this.isLogin = '0'
          if (this.isLogin=='0'){
            this.$refs.unlogin.style.color= "#ff250f";
            this.$refs.login.style.color=''
            this.search();
          }
        },
        searchOnlogin(){
          this.isLogin = '1';
          if (this.isLogin==1){
            this.$refs.login.style.color = "#ff250f";
            this.$refs.unlogin.style.color= "";
            this.search();
          }
        },
        search() {
          let param={
            "keyword":this.keyword,
          }
          this.$ajax({
            method: "get",
           // http://localhost:8085/solr/search?keyword=测试&isLogin=1&isAuth=1&status=1&serviceVersion=1&authVersionStr=1&psort=1 psort:''currtPage:'',
            url: "/solr/search?keyword="+this.keyword+"&isLogin="+this.isLogin+"&isAuth="+this.isAuth+"&status="+this.status+"&serviceVersion="+this.serviceVersion+"&authVersionStr="+this.authVersionStr+"&psort="+this.psort+"&currPage="+this.currPage,
            dataType: "json",
          }).then(res => {
            this.editLoading = false;
            console.log(res.data)
            this.count=res.data.count,
            this.currPage = res.data.currPage
            this.countPage =res.data.countPage
            this.api = res.data.list
            this.$emit("toFatherData",this.api);
            this.$message({
              message: "提交成功",
              type: "success"
            });
          })
        },
      }
    }
</script>


<style scoped>
  @import '../../static/base.css';
  @import '../../static/basic_list_210_561.css';
  @import '../../static/basic_list_810_153.css';
  @import '../../static/list-page-20141009.css';
  @import '../../static/plist20131112.css';
  /*@import '../../static/pop_compare.css';*/
  /*@import '../../static/ued.guang-min.css';*/
</style>
