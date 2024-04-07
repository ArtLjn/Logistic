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
                        <h1>个人主页功能</h1>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12" :offset="6">
                        <el-form>
                            <el-form-item label="用户地址:">
                                <el-col :span="10">
                                    {{ userForm.address }}
                                </el-col>
                            </el-form-item>
                            <el-form-item label="用户名:">
                                <el-col :span="10">
                                    {{ userForm.username }}
                                </el-col>
                            </el-form-item>
                            <el-form-item label="公司名称:">
                                <el-col :span="10">
                                    {{ userForm.companyName }}
                                </el-col>
    
                            </el-form-item>

                            <el-form-item label="用户角色:">
                                <el-col :span="10">
                                    {{ userForm.role }}
                                </el-col>
    
                            </el-form-item>
                        </el-form>
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
    components: { Header, Navigator },
    data() {
        return {
            userForm: {
                address: '',
                username: '',
                companyName: '',
                role: ''
            },
        }
    },
    mounted() {
        this.setUserInfo()
    },
    methods: {
        setUserInfo() {
            this.userForm.address = this.$cookies.get('company_address')
            this.userForm.username = this.$cookies.get('username')
            this.userForm.companyName = this.$cookies.get('company_name')
            if (this.$cookies.get('role') == 2) {
                this.userForm.role = '采购公司'
            }else if (this.$cookies.get('role') == 1) {
                this.userForm.role = '运输公司'
            }
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