<template>
    <el-menu active-text-color="#409EFF" class="el-menu-vertical-demo" :default-active="currentRoute" text-color="#333" router>
        <el-menu-item index="/home">
            <el-icon><HomeFilled /></el-icon>
            <span>首页</span>
        </el-menu-item>
        <el-sub-menu>
            <template #title>
                <el-icon><IconMenu /></el-icon>
                <span>商品管理</span>
            </template>
            <el-menu-item index="/business">商品信息</el-menu-item>
            <el-menu-item index="/businessAdd">新增商品</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/order">
            <el-icon>
                <List />
            </el-icon>
            <span>订单管理</span>
        </el-menu-item>
        <el-menu-item index="/users">
            <el-icon><UserFilled /></el-icon>
            <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/report">
            <el-icon><Warning /></el-icon>
            <span>举报管理</span>
        </el-menu-item>
        <el-menu-item index="/review">
            <el-icon><Star /></el-icon>
            <span>评价管理</span>
        </el-menu-item>
        <el-menu-item index="/person">
            <el-icon><Promotion /></el-icon>
            <span>个人信息</span>
        </el-menu-item>
    </el-menu>
</template>

<script setup>
import { Menu as IconMenu, HomeFilled, List, UserFilled, Promotion, Ticket, Warning, Star } from "@element-plus/icons-vue"
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const currentRoute = ref('/home')

onMounted(() => {
    // 初始化时设置当前路由
    currentRoute.value = route.path
    
    // 监听路由变化，更新当前激活的菜单项
    router.afterEach((to) => {
        currentRoute.value = to.path
    })
})
</script>
