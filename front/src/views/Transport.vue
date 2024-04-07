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
              <h1>运输业务功能页面</h1>
            </el-col>
          </el-row>
          <el-divider></el-divider>
          <el-row>
            <el-form>
              <el-form-item label="采购订单：">
                <el-col :span="6" :offset="1">
                  <el-input type="primnary" v-model="procureIndex"></el-input>
                </el-col>
                <el-col :span="4" :offset="1">
                  <el-button type="primary" @click="queryProcureOrder">查询</el-button>
                </el-col>
              </el-form-item>
            </el-form>
          </el-row>
          <el-divider></el-divider>
          <el-row>
            <el-form v-if="activeProcureOrder">
              <el-form-item label="采购订单ID:">
                {{ procureOrder.index}}
              </el-form-item>
              <el-form-item label="采购商品:">
                {{ procureOrder.materials}}
              </el-form-item>
              <el-form-item label="采购时间:">
                {{ procureOrder.time }}
              </el-form-item>
              <el-form-item label="采购周期:">
                {{  procureOrder.cycle}}
              </el-form-item>
              <el-form-item label="采购公司:">
                {{ procureOrder.company }}
              </el-form-item>
            </el-form>
            <el-button type="primary" @click="showTransOrder">新建运输订单</el-button>
          </el-row>
          <el-divider></el-divider>
          <el-row>
            <el-col :span="22" :offset="1">
              <el-table :data="transTable">
                <el-table-column prop="primaryKey" label="采购订单号"></el-table-column>
                <el-table-column prop="fields.perchaseCompany" label="采购公司"></el-table-column>
                <el-table-column prop="fields.transCompany" label="运输公司"></el-table-column>
                <el-table-column prop="fields.clearanceLocation" label="出关地点"></el-table-column>
                <el-table-column prop="fields.entryLocation" label="入关地点"></el-table-column>
                <el-table-column prop="fields.clearanceTime" label="出关时间"></el-table-column>
                <el-table-column prop="fields.entryTime" label="入关时间"></el-table-column>
                <el-table-column prop="fields.situation" label="物资状况"></el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-dialog title="新建运输订单"  :visible.sync="showDialog">
        <el-row>
          <el-col :span="12" :offset="6">
            <el-form>
              <el-form-item label="采购订单ID:">
                {{ procureOrder.index}}
              </el-form-item>
              <el-form-item label="采购商品:">
                {{ procureOrder.materials}}
              </el-form-item>
              <el-form-item label="采购时间:">
                {{ procureOrder.time }}
              </el-form-item>
              <el-form-item label="采购周期:">
                {{  procureOrder.cycle}}
              </el-form-item>
              <el-form-item label="采购公司:">
                {{ procureOrder.company }}
              </el-form-item>
              <el-form-item label="出关地点">
                <el-input type="primary" v-model="transOrder.clearanceLocation"></el-input>
              </el-form-item>
              <el-form-item label="入关地点">
                <el-input type="primary" v-model="transOrder.entryLocation"></el-input>
              </el-form-item>
              <el-form-item label="出关时间">
                <el-date-picker v-model="transOrder.clearanceTime" type="date" placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="入关时间">
                <el-date-picker v-model="transOrder.entryTime" type="date" placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="物资状况">
                <el-input type="primary" v-model="transOrder.situation"></el-input>
              </el-form-item>
            </el-form>
            <el-row>
              <el-button type="primary" @click="submitTransOrder">提交</el-button>
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
    components: {Header, Navigator},
    data(){
      return{
        procureOrder: {
          "index":"",
          "materials":"",
          "time":"",
          "cycle":"",
          "company":""
        },
        transOrder: {
          perChaseIndex:"",
          perChaseCompany:"",
          transCompany:"",
          clearanceLocation:"",
          entryLocation:"",
          clearanceTime:"",
          entryTime:"",
          situation:""
        },
        procureIndex: "",
        activeProcureOrder: false,
        transTable: [],
        showDialog: false
        
      }
    },
    mounted() {
      this.queryTranOrder();
    },
    methods:{
      queryProcureOrder() {
        this.axios.get(`/api/perChase/queryPerChaseOrderById?orderId=${this.procureIndex}`).then((res) => {
          if (res.data.code == 200) {
            this.activeProcureOrder = true;
            this.procureOrder.index = res.data.data.primaryKey;
            this.procureOrder.materials = res.data.data.fields.materials;
            this.procureOrder.time = res.data.data.fields.perchaseTime;
            this.procureOrder.cycle = res.data.data.fields.perchasementCycle;
            this.procureOrder.company = res.data.data.fields.perchaseCompany;
          }
        })
        
      },
      showTransOrder() {
        this.showDialog = true
      },
      submitTransOrder() {
        this.transOrder.transCompany = this.$cookies.get("company_address");
        this.transOrder.perChaseIndex = this.procureOrder.index;
        this.transOrder.perChaseCompany = this.procureOrder.company;

        this.axios.post('/api/trans/createTrans', this.transOrder,
      {
        headers:{
          'Content-Type': 'application/json',
          'Authorization': this.$cookies.get("token")
        }
      }).then((res) => {
          if (res.data.code == 200) {
            this.queryTranOrder();
            alert("执行成功")
          }else {
            alert(`注册失败，原因为${res.data.info}`)
          }
        })
      },
      queryTranOrder(){
        this.axios.get('/api/trans/queryTransOrder',
      {
        headers:{
          'Content-Type': 'application/json',
          'Authorization': this.$cookies.get("token")
        }
      }).then((res) => {
        if (res.data.code == 200) {
          this.transTable = res.data.data
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