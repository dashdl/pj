<template>
    <div class="home">
        <!-- 搜索 -->
        <search-bar></search-bar>
        <!-- 通知栏 -->
        <van-notice-bar scrollable text="欢迎使用二手交易信息平台 客服微信：Lijiale0509-" />
        <!-- 轮播 -->
        <van-swipe :autoplay="1500" lazy-render>
            <van-swipe-item v-for="image in images" :key="image">
                <img :src="image" class="img" />
            </van-swipe-item>
        </van-swipe>
        <!-- 类别 -->
        <div class="home-grid">
            <van-grid :column-num="4" square :gutter="10">
                <van-grid-item v-for="list in menulist" :key="list" :to="list.to">
                    <van-image :src="list.url" />
                    <span style="white-space: nowrap;">{{ list.text }}</span>
                </van-grid-item>
            </van-grid>
        </div>
        <!-- 最热商品 -->
        <div class="footer">
            <van-card
                tag="热"
                v-for="(item, index) in list"
                :key="index"
                :desc="item.description"
                :title="item.title"
                :thumb="`http://localhost:8080${item.photo}`"
                :thumb-link="`/detail/${item.id}`"
                v-show="item.status == 1">
                <template #tags>
                    <div>地址：{{ item.position }}</div>
                    <div>联系方式：{{ item.phone }}</div>
                </template>
            </van-card>
            <van-divider dashed>欢迎你的使用</van-divider>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import { getAllList } from "@/api/index"
import SearchBar from "../../components/Search/index.vue"
const images = ["https://img.alicdn.com/imgextra/i3/6000000000469/O1CN013e2Ben1FKqfFzPwIC_!!6000000000469-0-octopus.jpg", "https://p0.ifengimg.com/fck/2017_48/1b19b49dfc4c56f_w1268_h449.png"]

import menu1 from "../../../src/assets/home/menu1.png"
import menu2 from "../../../src/assets/home/menu2.png"
import menu3 from "../../../src/assets/home/menu4.png"
import menu4 from "../../../src/assets/home/menu3.png"
import menu5 from "../../../src/assets/home/menu5.png"
import menu6 from "../../../src/assets/home/menu6.png"
import menu7 from "../../../src/assets/home/menu7.png"
import menu8 from "../../../src/assets/home/menu8.png"
const menulist = [
    { text: "电子工程学院", url: menu1, to: "/list/1" },
    { text: "管理学院", url: menu2, to: "/list/2" },
    { text: "机械工程学院", url: menu3, to: "/list/3" },
    { text: "计算机学院", url: menu4, to: "/list/4" },
    { text: "经济学院", url: menu5, to: "/list/5" },
    { text: "马克思学院", url: menu6, to: "/list/6" },
    { text: "课外读物", url: menu7, to: "/list/7" },
    { text: "其他", url: menu8, to: "/list/8" },
]

const list = ref([])
async function getList() {
    let res = await getAllList()
    const numRecords = 5 // 最大显示条数
    const randomRecords = getRandomRecords(res, numRecords)
    list.value = randomRecords
}
function getRandomRecords(data, numRecords) {
    const shuffled = data.sort(() => 0.5 - Math.random())
    return shuffled.slice(0, Math.min(numRecords, data.length))
}
onMounted(() => {
    getList()
})
</script>

<style scoped lang="less">
.img {
    width: 100vw;
    height: 30vh;
}

.nav {
    margin-top: 20px;
}

.home-grid {
    padding-top: 10px;
    .van-image {
        width: 55%;
    }
    span {
        font-size: 12px;
        padding-top: 5px;
    }
}

.footer {
    margin-top: 15px;
}

.footer p {
    margin-top: 20px;
    text-align: center;
}
</style>
