<template>
    <div>
        <van-search v-model="keyword" show-action shape="round" background="#fe9b02" placeholder="请输入搜索关键词" @search="onSearch">
            <template #action>
                <div @click="onSearch" class="search">搜索</div>
            </template>
        </van-search>
    </div>
</template>
<script setup>
import { useRouter } from "vue-router"
import { ref } from "vue"
import { showFailToast } from "vant"
const keyword = ref("")
const router = useRouter()

const onSearch = () => {
    if (keyword.value === "") {
        return
    } else if (keyword.value === "图书") {
        router.push("/list/1")
    } else if (keyword.value === "衣物") {
        router.push("/list/2")
    } else if (keyword.value === "数码") {
        router.push("/list/3")
    } else if (keyword.value === "鞋类") {
        router.push("/list/4")
    } else if (keyword.value === "玩具") {
        router.push("/list/5")
    } else if (keyword.value === "宿舍") {
        router.push("/list/6")
    } else if (keyword.value === "美容") {
        router.push("/list/7")
    } else {
        showFailToast("无此类商品")
    }
}
</script>

<style scoped lang="less">
.search {
    padding: 0 5px;
    color: #fff;
    font-weight: bold;
}
</style>
