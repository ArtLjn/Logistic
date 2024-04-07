<template>
  <el-row>
    <el-col :span="8" :offset="8">
      <h2>用户注册</h2>
      <el-form label-width="130px">
        <el-form-item label="用户名:">
          <el-input v-model="form.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码:">
          <el-input v-model="form.password" show-password clearable></el-input>
        </el-form-item>
        <el-form-item label="公司名称:">
          <el-input v-model="form.company_name" clearable></el-input>
        </el-form-item>
        <el-form-item label="公司类型:">
          <el-select v-model="form.role" placeholder="请选择:">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司实际地址" v-if="form.role == 1">
          <el-input v-model="form.location"></el-input>
        </el-form-item>
        <el-form-item label="公司经营范围" v-if="form.role == 1">
          <el-input v-model="form.business_scope"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onRegister" style="margin-right: 60px">注册</el-button>
          <el-button type="primary" @click="goback" style="margin-right: 60px">返回</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      form: {
        username: '',
        password: '',
        company_name: '',
        role: '',
        location: 'default',//公司实际地址
        business_scope: 'default' //公司经营范围
      },
      options: [
      {
        value: '1',
        label: '运输公司'
      }
    , {
        value: '2',
        label: '采购公司'
      }]
    }
  },
  methods: {
    onRegister() {
        this.axios.post('/api/user/register', this.form).then((res) => {
          let code = res.data.code
          if (code == 200) {
            alert("注册成功")
            this.$router.push('/Login')
          } else {
            alert(`注册失败，原因为${res.data.info}`)
          }
        })
          .catch((res) => {
            console.log(res);
          })
    },
    goback() {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped></style>