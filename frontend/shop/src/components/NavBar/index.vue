<template>
    <div>
        <van-nav-bar :title="title" left-text="返回" left-arrow @click-left="onClickLeft" />
    </div>
</template>

<script setup>
import { useRouter } from "vue-router"
const router = useRouter()
const { title } = defineProps(["title"])

const onClickLeft = () => {
    try {
        // 尝试返回到上一页
        router.go(-1)
    } catch (error) {
        // 如果失败，则导航到首页
        router.push('/home')
    }
}
</script>

<style></style>
