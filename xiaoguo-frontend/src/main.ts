import { createApp } from 'vue'
import App from './App.vue'
//import { Button, NavBar,Icon,Tabbar,TabbarItem,Tag} from 'vant';
import  * as VueRouter from 'vue-router'
import 'vant/lib/index.css';
import routes from "./config/route.ts";
import Vant from 'vant';
import "vant/es/toast/style"
import '../global.css'

const app = createApp(App)
app.use(Vant);

//router实例
const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes,
})

app.use(router)
app.mount('#app')

