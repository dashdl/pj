import { createRouter, createWebHashHistory } from "vue-router"

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: "/",
            redirect: "/login",
        },
        {
            path: "/login",
            component: () => import("../views/login/index.vue"),
        },
        {
            path: "/home",
            component: () => import("../views/layout/index.vue"),
            children: [
                {
                    path: "/home",
                    component: () => import("../views/home/index.vue"),
                },
                {
                    path: "/business",
                    component: () => import("../views/business/index.vue"),
                },
                {
                    path: "/businessAdd",
                    component: () => import("../views/businessAdd/index.vue"),
                },
                {
                    path: "/order",
                    component: () => import("../views/order/index.vue"),
                },
                {
                    path: "/users",
                    component: () => import("../views/users/index.vue"),
                },
                {
                    path: "/person",
                    component: () => import("../views/person/index.vue"),
                },
                {
                    path: "/report",
                    component: () => import("../views/report/index.vue"),
                },
                {
                    path: "/review",
                    component: () => import("../views/review/index.vue"),
                },
            ],
        },
    ],
})

export default router
