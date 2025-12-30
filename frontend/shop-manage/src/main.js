import { createApp } from "vue"
import "./style.css"
import App from "./App.vue"
import router from "./router"
import ElementPlus from "element-plus"
import "element-plus/dist/index.css"
// 导入Element Plus的中文语言包
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

const app = createApp(App)
app.use(router)
// 使用Element Plus时配置中文语言
app.use(ElementPlus, {
  locale: zhCn,
})
app.mount("#app")
