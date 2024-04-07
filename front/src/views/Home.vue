<template>
  <el-row style="height: 100%;background-color: #f3f4f9">
    <el-row style="height: 10%;border-bottom-style: solid;" class="header">
      <Header></Header>
    </el-row>
    <el-row :gutter="20" style="height: 90%">
      <el-col :span="4" style="height: 100%;">
        <Navigator></Navigator>
      </el-col>
      <el-col :span="20" style="border-top: 1px solid #2c3e50;padding: 0;height: 100%">
        <el-row>
          <el-col :span="12">
            <h1>欢迎访问，区块链海运物流管理系统</h1>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="10" :offset="2">
            <h2>当前区块数量： {{ height }}</h2>
          </el-col>
          <el-col :span="10">
            <h2>当前用户数量： {{ userCount }}</h2>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="6">
            <h2>近期新交易情况</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22" :offset="1">
            <el-table :data="tableData" style="width: 100%" height="300">
              <el-table-column prop="pk_id" label="pk_id">
              </el-table-column>
              <el-table-column prop="block_height" label="区块高度">
              </el-table-column>
              <el-table-column prop="block_hash" label="当前区块哈希">
              </el-table-column>
              <el-table-column prop="block_time_stamp" label="区块时间">
              </el-table-column>
              <el-table-column prop="tx_hash" label="交易哈希">
              </el-table-column>
              <el-table-column prop="tx_index" label="交易索引">
              </el-table-column>
              <el-table-column prop="value" label="值">
              </el-table-column>
            </el-table>
          </el-col>

        </el-row>
        


      </el-col>
    </el-row>
  </el-row>

</template>

<script>

import Header from "@/components/Header";
import Navigator from "@/components/Navigator";

export default {
  // name: "main",
  components: {Header, Navigator},
  data(){
    return{
      height: '', // 区块高度
      userCount: "", //系统用户数量
      tableData: [] 
      
    }
  },
  mounted() {
    this.isLogin()
    this.getHomeView()
  },
  methods:{
    getHomeView() {
      this.axios.get("/home_page_query").then((res) => {
        if (res.data.code == 200) {
          console.log("获取主页详情执行正确")
          this.height = res.data.data.height;
          this.userCount = res.data.data.user_count;
          this.tableData = res.data.data.tx_list
        }
      })
    },
    isLogin() {
      let token = this.$cookies.get('token')
      this.axios.defaults.headers.common["token"] = token;
      if (token == null) {
        this.$router.push("/login")
      }
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
.header{
  background-color: #2c3e50;
}
</style>