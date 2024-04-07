<template>
  <el-row class="tac" style="height: 100%;">
    <el-menu class="el-menu-vertical-demo" style="height: 100%;" :default-active=active background-color="#344154" text-color="#fff" active-text-color="#ffd04b">
      <el-menu-item index="1"  @click="click_to('home', '1')" >
        <span slot="title" style="font-size: large" >主页</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <span style="font-size: large">物流业务</span>
        </template>
        <el-menu-item index="2-1" style="padding-left:80px" @click="click_to('procure', '2-1')">采购业务</el-menu-item>
        <el-menu-item index="2-2" style="padding-left:80px" @click="click_to('transport', '2-2')">运输业务</el-menu-item>
      </el-submenu>
      <el-menu-item index="3" style="font-size: large" @click="click_to('admin', '2-2')">管理员功能</el-menu-item>
      <el-menu-item index="4" style="font-size: large" @click="click_to('individual', '2-2')">个人主页</el-menu-item>
     
    </el-menu>
  </el-row>

</template>

<script>
export default {
  name: "Navigator",
  data() {
    return {
      post_role:0,
      active:"1"
    }
  },
  mounted() {
    this.set_user_info()
  },
  methods: {
    set_user_info(){
      this.username = this.$cookies.get('username')
      this.post_role = this.$cookies.get('role')
      this.active = this.$cookies.get('active')
    },
    click_to(url, thisActive) {
      this.$router.push(url)
      this.active = thisActive
      this.$cookies.set("active", this.active)
    }
  }
}
</script>

<style scoped>

</style>