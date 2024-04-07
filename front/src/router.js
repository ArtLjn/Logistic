import Login from "@/views/Login";
import Register from "@/views/Register";
import Home from "@/views/Home";
import Admin from "@/views/Admin";
import Individual from "@/views/Individual";
import Procure from "@/views/Procure";
import Transport from '@/views/Transport'



export default [
    {path: '/', redirect: '/home'},
    //  用户登录
    {path: '/login', component: Login},
    //  用户注册
    {path: '/register', component: Register},
    {
        path: '/home', component: Home
    },
    {
        path: '/admin', component: Admin
    },
    {
        path: '/individual', component: Individual
    },
    {
        path: '/procure', component: Procure
    },
    {
        path: '/transport', component: Transport
    },
]