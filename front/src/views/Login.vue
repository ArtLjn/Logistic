<template>
  <div class="Login">
    <h2>欢迎登录</h2>
    <el-form class="form" label-width="80px">
      <el-form-item label="用户名" label-width="80px">
        <el-input v-model="loginform.username" clearable></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="loginform.password" show-password clearable></el-input>
      </el-form-item>
      <el-alert v-show = "code == 201" title="用户不存在" type="error" center show-icon></el-alert>
      <el-alert v-show = 'code == 202' title="密码错误" type="error" center show-icon></el-alert>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">登录</el-button>
        <el-button @click="onRegister">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      loginform:{
        username:'',
        password:''
      },
      code:'0',
    }
  },
  methods:{
    onSubmit(){
      this.axios.post('/api/user/login',this.loginform)
      .then((res)=>{
        let code = res.data.code
        this.code = code
        if(code == 200){
          alert("登录成功")
          let token = res.data.data.token
          let role = res.data.data.role
          let username = res.data.data.username
          let company_address = res.data.data.company_address
          let company_name = res.data.data.company_name
          let location = res.data.data.location
          let bussiness_scope = res.data.data.business_scope
          this.axios.defaults.headers.common["token"] = token;
          this.$cookies.set('token',token)
          this.$cookies.set('role',role)
          this.$cookies.set('username', username)
          this.$cookies.set('company_address', company_address)
          this.$cookies.set('company_name', company_name)
          this.$cookies.set('location', location)
          this.$cookies.set('business_scope', bussiness_scope)
          this.$router.push('/home')
        }
      })
      .catch((res)=>{
        console.log(res);
      })
    },
    onRegister(){
      //新建页面跳转
      let routerUrl = this.$router.resolve({
        path:'/register'
      })
      window.open(routerUrl.href,'_black')
    }
  }
}
</script>

<style scoped>
.Login{
  /*height: 100%;*/
  /*width: 100%;*/
  /*position: fixed;*/
  /*background-color: #42b983;*/
  border: 1px solid aqua;
  border-radius: 10px;
  width: 400px;
  margin: 150px auto;
}
.form{
  width: 350px;
  margin: 0 auto;
}
.el-button{
  margin-right: 30px;
}
</style>