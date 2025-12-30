<template>
    <div
        class="app-wrapper"
        :class="classObj">
        <!-- 头部导航 -->
        <Header />
        <!--主体内容-->
        <div class="main-container">
            <!--侧边栏-->
            <div class="sidebar">
                <Sidebar />
            </div>
            <!-- 右侧部分 -->
            <div class="right">
                <Appmain />
            </div>
        </div>
    </div>
</template>

<script setup>
import Sidebar from './components/sidebar/index.vue'
import Header from './components/header/index.vue'
import Appmain from './components/appmain/index.vue'
</script>

<style lang="less" scoped>
.app-wrapper {
    // height: 100%;
    height: 100vh;
    overflow: hidden;
}

.main-container {
    display: flex;
    height: 100%;
    .sidebar {
        // flex: 0.5;
        width: 230px;
    }

    .right {
        flex: 3;
        background-color: #e8e8e8;
        // height: 100%;
        // height: 1500px;
    }
}
</style>
