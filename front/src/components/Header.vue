<template>
  <el-row>
    <span style="font-size: 30px;line-height: 60px">区块链海运物流管理系统</span>
    <div style="float: right;line-height: 60px">
      <span style="margin-right: 10px">欢迎用户: {{username}}</span>
      <el-button type="text" @click="logout">登出</el-button>
    </div>
  </el-row>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      username: '',
    }
  },
  mounted() {
    this.set_user_info()
  },
  methods: {
    set_user_info(){
      this.username = this.$cookies.get('username')
    },
    logout(){
      this.$cookies.remove('username')
      this.$cookies.remove('token')
      this.$cookies.remove('role')
      this.$cookies.remove('account_address')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

</style>