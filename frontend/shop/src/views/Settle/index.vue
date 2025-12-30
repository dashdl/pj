<template>
    <div class="release" style="padding-bottom: 100px">
        <van-nav-bar title="申请入驻" />
        <van-notice-bar wrapable :scrollable="false" text="欢迎使用我们的二手交易平台！本声明旨在明确用户与平台之间的权责关系，以确保您的交易体验安全和愉快。" />
        <div class="prompt">请选择发布类型</div>

      
        <div class="home-grid">
            <van-grid :column-num="2" :gutter="15">
                <van-grid-item v-for="list in menulist" :key="list" :to="list.to">
                    <van-image :src="list.url" width="150" style="border-top-right-radius: 8px; overflow: hidden" />
                    <span>{{ list.text }}</span>
                </van-grid-item>
            </van-grid>
        </div>
    </div>
</template>

<script setup>
// import { useRouter } from "vue-router"
// const router = useRouter()
// const navigateToRelease = (type) => {
//     router.push({ path: `/release/${type}` })
// }
import menu1 from "../../../src/assets/release/电子工程学院.png"
import menu2 from "../../../src/assets/release/管理学院.png"
import menu3 from "../../../src/assets/release/机械工程学院.png"
import menu4 from "../../../src/assets/release/计算机学院.png"
import menu5 from "../../../src/assets/release/经济学院.png"
import menu6 from "../../../src/assets/release/马克思学院.png"
import menu7 from "../../../src/assets/release/book.webp"
import menu8 from "../../../src/assets/release/其他学院.png"

const menulist = [
    { text: "电子工程学院", url: menu1, to: "/release/1" },
    { text: "管理学院", url: menu2, to: "/release/2" },
    { text: "机械工程学院", url: menu3, to: "/release/3" },
    { text: "计算机学院", url: menu4, to: "/release/4" },
    { text: "经济学院", url: menu5, to: "/release/5" },
    { text: "马克思学院", url: menu6, to: "/release/6" },
    { text: "课外读物", url: menu7, to: "/release/7" },
    { text: "其他", url: menu8, to: "/release/8" },
]
</script>

<style scoped lang="less">
.prompt {
    font-size: 15px;
    margin-top: 20px;
    margin-left: 20px;
}
.home-grid {
    padding-top: 10px;
    span {
        font-size: 15px;
        padding-top: 13px;
        font-weight: bold;
    }
}
</style>
