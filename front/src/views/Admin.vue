<template>
    <el-row style="height: 100%;background-color: #f3f4f9">
      <el-row style="height: 10%;border-bottom-style: solid;border-bottom-color: #e6e6e6;background-color: #ffffff">
        <Header></Header>
      </el-row>
      <el-row :gutter="20" style="height: 90%">
        <el-col :span="4" style="height: 100%;">
          <Navigator></Navigator>
        </el-col>
        <el-col :span="20" style="border-top: 1px solid #2c3e50;padding: 0;height: 100%">
          <el-row>
            <el-col :span="4">
                <h1>管理员功能</h1>
            </el-col>
          </el-row>
          <el-row>
            <el-form>
                <el-row>
                    <el-col :span="20" :offset="2">
                        <el-form-item label="用户地址：">
                            <el-row>
                                <el-col :span="6">
                                    <el-input type="primary" v-model="userAddress"></el-input>
                                </el-col>
                                <el-col :span="2">
                                    <el-button type="primary" @click="getUserInfo">查询</el-button>
                                </el-col>
                            </el-row>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-divider></el-divider> 
            </el-form>
            <el-form v-if="transForm.show">

                <el-form-item label="公司地址:">
                    <el-col :span="10">
                        {{ transForm.companyAddress }}
                    </el-col>
                </el-form-item>
                
                <el-form-item label="公司名称:">
                    <el-col :span="10">
                        {{ transForm.companyName }}
                    </el-col>

                </el-form-item>
                
                <el-form-item label="地址:">
                  <el-col :span="10">
                      {{ transForm.location }}
                  </el-col>
              </el-form-item>
              <el-form-item label="经营范围:">
                <el-col :span="10">
                    {{ transForm.bussinuessScope }}
                </el-col>
            </el-form-item>
            </el-form>

            <el-form v-if="perChaseForm.show">

                <el-form-item label="公司地址:">
                    <el-col :span="10">
                        {{ perChaseForm.companyAddress }}
                    </el-col>
                </el-form-item>
                
                <el-form-item label="公司名称:">
                    <el-col :span="10">
                        {{ perChaseForm.companyName }}
                    </el-col>

                </el-form-item>
                
                <el-form-item label="物流订单:">
                  <el-col :span="10">
                      {{ perChaseForm.transList }}
                  </el-col>
              </el-form-item>
              <el-form-item label="采购订单:">
                <el-col :span="10">
                    {{ perChaseForm.perchaseList }}
                </el-col>
            </el-form-item>
            </el-form>
            
          </el-row>
        </el-col>
      </el-row>
      
    </el-row>
  
  </template>
  
  <script>
  
  import Header from "@/components/Header";
  import Navigator from "@/components/Navigator";
  
  export default {
    components: {Header, Navigator},
    data(){
      return{
        formActive:0,
        userAddress:"",
        transForm: {
          transList:"",
          companyName:"",
          location:"",
          bussinuessScope:"",
          companyAddress:"",
          show:false
        },
        perChaseForm:{
          transList:"",
          companyName:"",
          perchaseList:"",
          companyAddress:"",
          show:false
        }
      }
    },
    mounted() {
  
    },
    methods:{
      getUserInfo() {
        this.axios.get(`/api/user/queryCompanyMsg?company_address=${this.userAddress}`).then((res) => {
          if (res.data.code == 200) {
            if (res.data.data.role == 1) {
              this.transForm.transList = res.data.data.chain.fields.transList;
              this.transForm.companyName = res.data.data.chain.fields.companyName;
              this.transForm.location = res.data.data.chain.fields.location;
              this.transForm.bussinuessScope = res.data.data.chain.fields.bussinessScope;
              this.transForm.companyAddress = res.data.data.chain.primaryKey;
              this.transForm.show = true;
              this.perChaseForm.show = false;
            } else {
              this.perChaseForm.transList = res.data.data.chain.fields.transList;
              this.perChaseForm.companyName = res.data.data.chain.fields.companyName;
              this.perChaseForm.perchaseList = res.data.data.chain.fields.perchaseList;
              this.perChaseForm.companyAddress =  res.data.data.chain.primaryKey;
              this.perChaseForm.show = true;
              this.transForm.show = false;
            }
          }else {
            alert(`请求失败，原因为${res.data.data}`)
          }
        })
      }

    }
  }
  </script>
  
  <style scoped>
  .container{
    height: 100%;
    width: 100%;
    position: fixed;
  }
  .aside{
    background-color: #545c64;
  }
  .main{
    background-color: #fff;
  }
  .form{
    width: 400px;
    margin: 0 auto;
  }
  </style>