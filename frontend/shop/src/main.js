import { createApp } from "vue"
import "./style.css"
import App from "./App.vue"
import router from "./router"
// import {
//     Button,
//     Cell,
//     CellGroup,
//     Form,
//     Field,
//     Notify,
//     Toast,
//     Tabbar,
//     TabbarItem,
//     Dialog,
//     NoticeBar,
//     GridItem,
//     Grid,
//     NavBar,
//     Uploader,
//     Tab,
//     Tabs,
//     Swipe,
//     SwipeItem,
//     Search,
//     Card,
//     Divider,
//     SubmitBar,
//     Popup,
//     AddressEdit,
//     Empty,
//     Image as VanImage,
//     RadioGroup,
//     Radio,
//     Col,
// } from "vant"

import Vant from "vant"
import "vant/lib/index.css"

const app = createApp(App)
app.use(router)
app.use(Vant)
// app.use(Button)
// app.use(Cell)
// app.use(CellGroup)
// app.use(Form)
// app.use(Field)
// app.use(Notify)
// app.use(Toast)
// app.use(Tabbar)
// app.use(TabbarItem)
// app.use(Dialog)
// app.use(NoticeBar)
// app.use(Grid)
// app.use(GridItem)
// app.use(NavBar)
// app.use(Uploader)
// app.use(Tab)
// app.use(Tabs)
// app.use(Swipe)
// app.use(SwipeItem)
// app.use(Search)
// app.use(Card)
// app.use(Divider)
// app.use(SubmitBar)
// app.use(Popup)
// app.use(AddressEdit)
// app.use(Empty)
// app.use(VanImage)
// app.use(Radio)
// app.use(RadioGroup)
app.mount("#app")
