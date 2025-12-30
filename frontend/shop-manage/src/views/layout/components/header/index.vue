<template>
    <div class="app-wrapper">
        <header>
            <div class="left">校园二手交易后台管理系统</div>
            <div class="right">
                <span>admin</span>
                <img
                    :src="image"
                    alt="User Avatar"
                    @error="handleImageError" />
                <div class="dropdown">
                    <div @click="toPerson">修改个人信息</div>
                    <div @click="dialogVisible = true">退出登录</div>
                </div>
            </div>
        </header>
    </div>
    <!-- 退出 -->
    <el-dialog
        v-model="dialogVisible"
        width="30%">
        <span>你确定退出嘛</span>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button
                    type="primary"
                    @click="handleClose">
                    确定
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import defaultAvatar from '@/assets/default-avatar.png'

const router = useRouter()
const dialogVisible = ref(false)
const image = ref('')
const defaultImage = ref(defaultAvatar)

const handleClose = () => {
    router.push('/login')
}
const toPerson = () => {
    router.push('/person')
}

onMounted(() => {
    image.value = `http://localhost:8080${localStorage.getItem('avatarUrl')}`
})

const handleImageError = (e) => {
    e.target.src = defaultImage.value
}
</script>

<style lang="less" scoped>
header {
    display: flex;
    background: -webkit-gradient(linear, left top, right top, from(#1493fa), to(#01c6fa));
    color: #fff;
    height: 76px;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
    font-size: 20px;
}

header .right {
    display: flex;
    align-items: center;
    position: relative;
    height: 100%;
    width: 95px;
    justify-content: flex-end;
    cursor: pointer;
}

header .right img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
header .right span {
    font-size: 14px;
    font-weight: bold;
    padding-right: 10px;
    color: #fff;
}
header .right .dropdown {
    position: absolute;
    background-color: #fff;
    padding: 5px 0;
    top: 65px;
    right: 0;
    width: 200px;
    box-shadow: 0px 0px 12px rgba(0, 0, 0, 0.12);
    border-radius: 5px;
    transform: scaleY(0);
    transition: 0.2s all;
    transform-origin: top;
}

header .right .dropdown > div {
    display: block;
    padding: 10px 20px;
    color: gray;
    font-size: 14px;
    text-decoration: none;
}

header .right .dropdown div:hover {
    color: rgb(64, 158, 255);
    background-color: rgb(236, 245, 255);
}

header .right:hover .dropdown {
    transform: scaleY(1);
    z-index: 1;
}
</style>
