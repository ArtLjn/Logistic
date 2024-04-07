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
          <el-col :span="6">
            <h1>采购业务功能页面</h1>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="2" :offset="1">
            <el-button type="primary" @click="activeNewOrderDialog">新建采购订单</el-button>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="4" :offset="1">
            <h3>公司现有采购订单</h3>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22" :offset="1">
            <el-table :data="procureTable">
              <el-table-column prop="primaryKey" label="订单号"></el-table-column>
              <el-table-column prop="fields.materials" label="采购物资"></el-table-column>
              <el-table-column prop="fields.perchaseTime" label="采购时间"></el-table-column>
              <el-table-column prop="fields.perchasementCycle" label="采购周期"></el-table-column>
              <el-table-column prop="fields.perchaseCompany" label="采购公司"></el-table-column>
            </el-table>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" :offset="1">
            <h3>公司现有运输订单</h3>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22" :offset="1">
            <el-table :data="transData">
              <el-table-column prop="primaryKey" label="订单号"></el-table-column>
              <el-table-column prop="fields.perchaseIndex" label="采购订单号"></el-table-column>
              <el-table-column prop="fields.perchaseCompany" label="采购公司"></el-table-column>
              <el-table-column prop="fields.transCompany" label="运输公司"></el-table-column>
              <el-table-column prop="fields.clearanceLocation" label="出关地点"></el-table-column>
              <el-table-column prop="fields.entryLocation" label="入关地点"></el-table-column>
              <el-table-column prop="fields.clearanceTime" label="出关时间"></el-table-column>
              <el-table-column prop="fields.entryTime" label="入关时间"></el-table-column>
              <el-table-column prop="fields.situation" label="物资状态"></el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-dialog title="新建采购订单"  :visible.sync="showDialog">
      <el-row>
        <el-col :span="6" :offset="9">
          <el-form>
            <el-form-item label="采购物资">
              <el-input type="primary" v-model="newProcureOrderFrom.materials"></el-input>
            </el-form-item>
            <el-form-item label="采购时间">
              <el-date-picker v-model="newProcureOrderFrom.perChaseTime" type="date" placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="采购周期">
              <el-input type="primary" v-model="newProcureOrderFrom.perChasementCycle"></el-input>
            </el-form-item>
          </el-form>
          <el-row>
            <el-button type="primary" @click="submitProcureOrder">提交</el-button>
          </el-row>
        </el-col>
      </el-row>
      
    </el-dialog>
  </el-row>
</template>
  
<script>

import Header from "@/components/Header";
import Navigator from "@/components/Navigator";

export default {
  // name: "main",
  components: { Header, Navigator },
  data() {
    return {
      showDialog: 0,
      newProcureOrderFrom: {
        "materials":'',
        "perChaseTime":'',
        'perChasementCycle':0
      },
      procureTable: [],
      transData:[]
    }
  },
  mounted() {
    this.queryProcureOrder();
    this.queryPerChaseOrderByTransOrder();
  },
  methods: {
    activeNewOrderDialog() {
      this.showDialog = 1
    },
    submitProcureOrder() {
      this.newProcureOrderFrom.perChaseTime = new Date(this.newProcureOrderFrom.perChaseTime).getTime();
      this.newProcureOrderFrom.perChasementCycle = parseInt(this.newProcureOrderFrom.perChasementCycle)
      this.axios.post('/api/perChase/createPerChaseOrder',this.newProcureOrderFrom,{
        headers:{
          'Content-Type': 'application/json',
          'Authorization': this.$cookies.get("token")
        }
      }).then((res) => {
        if (res.data.code == 200) {
          this.queryProcureOrder()
          alert("创建成功！")
        } else {
          alert(res.data.info)
        }
      })
    },
    queryPerChaseOrderByTransOrder() {
      this.axios.get('/api/trans/queryPerChaseOrderByTransOrder',{
        headers:{
          'Content-Type': 'application/json',
          'Authorization': this.$cookies.get("token")
        }
      }).then((res) => {
        this.transData = res.data.data  
      })
    },
    queryProcureOrder() {
      this.axios.get(`/api/perChase/queryPerChaseOrder`,{
        headers:{
          'Content-Type': 'application/json',
          'Authorization': this.$cookies.get("token")
        }
      }).then((res) => {
        if (res.data.code == 200) {
          this.procureTable = res.data.data
        }
      })
    }


  }
}
</script>
  
<style scoped>
.container {
  height: 100%;
  width: 100%;
  position: fixed;
}

.aside {
  background-color: #545c64;
}

.main {
  background-color: #fff;
}

.form {
  width: 400px;
  margin: 0 auto;
}
</style>