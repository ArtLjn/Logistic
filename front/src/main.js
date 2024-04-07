import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueRouter from 'vue-router'
import routes from './router'
import axios from "axios";
import VueAxios from 'vue-axios'
import * as echarts from 'echarts'
import Moment from 'moment'
import VueCookies from 'vue-cookies'

Vue.use(VueCookies)
Vue.prototype.$moment = Moment
Vue.use(VueAxios,axios)
Vue.use(VueRouter)
Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.prototype.$echarts = echarts
axios.defaults.baseURL = '/api'
axios.defaults.withCredentials = true

const router = new VueRouter({
  routes
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

// axios.interceptors.response.use(
//     res=>{
//       if (res.data.code == 401){
//         router.push('/Login')
//       }
//     }
// )
