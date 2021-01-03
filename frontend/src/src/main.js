import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import {BootstrapVue, IconsPlugin} from "bootstrap-vue";
import router from "./router"

Vue.config.productionTip = false

// Bootstrap
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

// Router
Vue.use(VueRouter)

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
