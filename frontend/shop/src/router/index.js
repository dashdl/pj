import { createRouter, createWebHistory } from "vue-router"
import Login from "../views/Login/index.vue"
import Home from "../views/Home/index.vue"
import Settle from "../views/Settle/index.vue"
import Personal from "../views/Personal/index.vue"
import List from "../views/List/index.vue"
import Release from "../views/Release/index.vue"
import MyPush from "../views/MyPush/index.vue"
import MySelled from "../views/MySelled/index.vue"
import Order from "../views/Order/index.vue"
import ModifyInfo from "../views/ModifyInfo/index.vue"
import Detail from "../views/Detail/index.vue"
import Review from "../views/Review/index.vue"
import Favorite from "../views/Favorite/index.vue"
import Message from "../views/Message/index.vue"
import Report from "../views/Report/index.vue"

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            redirect: "/login",
        },
        {
            path: "/login",
            name: "login",
            component: Login,
            meta: { showTabBar: false, requiresAuth: false },
        },
        {
            path: "/home",
            name: "home",
            component: Home,
            meta: { showTabBar: true, requiresAuth: true },
        },
        {
            path: "/settle",
            name: "settle",
            component: Settle,
            meta: { showTabBar: true, requiresAuth: true },
        },
        {
            path: "/personal",
            name: "personal",
            component: Personal,
            meta: { showTabBar: true, requiresAuth: true },
        },
        {
            path: "/mypush",
            name: "mypush",
            component: MyPush,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/myselled",
            name: "myselled",
            component: MySelled,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/order",
            name: "order",
            component: Order,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/modifyinfo",
            name: "modifyinfo",
            component: ModifyInfo,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/detail/:id",
            name: "detail",
            component: Detail,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/release/:id",
            name: "release",
            component: Release,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/detail/:id",
            name: "detail",
            component: Detail,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/list/:id",
            name: "list",
            component: List,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/review/:businessId",
            name: "review",
            component: Review,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/favorite",
            name: "favorite",
            component: Favorite,
            meta: { showTabBar: false, requiresAuth: true },
        },
        {
            path: "/message",
            name: "message",
            component: Message,
            meta: { showTabBar: true, requiresAuth: true },
        },
        {
            path: "/report",
            name: "report",
            component: Report,
            meta: { showTabBar: false, requiresAuth: true },
        },
    ],
})
router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem("token")
    if (to.meta.requiresAuth && !isAuthenticated) {
        next("/login")
    } else {
        next()
    }
})

export default router
