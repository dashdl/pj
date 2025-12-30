<template>
    <div class="personal">
        <div class="profile">
            <img
                :src="avatarUrl"
                alt="Profile Image"
                @error="handleImageError" />
            <h1>{{ username }}</h1>
            <p>往前走，原本看不见的星星就会露出来</p>
        </div>
        <van-row class="user-links">
            <van-col span="6">
                <van-icon name="pending-payment" />
                待付款
            </van-col>
            <van-col span="6">
                <van-icon name="todo-list-o" />
                待收获
            </van-col>
            <van-col span="6">
                <van-icon name="tosend" />
                待评价
            </van-col>
            <van-col span="6">
                <van-icon name="logistics" />
                售后
            </van-col>
        </van-row>
        <div class="list">
            <van-cell
                title="编辑个人资料"
                is-link
                size="large"
                to="modifyinfo"
                style="font-weight: bold" />
            <van-cell
                title="我的订单"
                is-link
                icon="points"
                to="order" />
            <van-cell
                title="我发布的"
                is-link
                icon="gold-coin-o"
                to="mypush" />
            <van-cell
                title="我卖出的"
                is-link
                icon="gift-o"
                to="myselled" />
            <van-cell
                title="我的收藏"
                is-link
                icon="star-o"
                to="favorite" />
            <van-cell
                title="退出登录"
                is-link
                icon="guide-o"
                @click="exit" />
        </div>
    </div>
</template>

<script setup>
import { showConfirmDialog } from 'vant'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import defaultAvatar from '@/assets/default-avatar.png'

const username = ref('')
const avatarUrl = ref('')
const defaultImage = ref(defaultAvatar)
const router = useRouter()
onMounted(() => {
    username.value = localStorage.getItem('username') || ''
    avatarUrl.value = `http://localhost:8080${localStorage.getItem('avatarUrl') || ''}`
})
const handleImageError = (e) => {
    e.target.src = defaultImage.value
}
const exit = () => {
    showConfirmDialog({
        title: '退出',
        message: '确定退出登录嘛？'
    })
        .then(() => {
            router.push('/login')
            localStorage.clear()
        })
        .catch(() => {})
}
</script>

<style scoped lang="less">
.profile {
    padding: 20px;
    text-align: center;
    background: url('https://img0.baidu.com/it/u=811638403,1746685823&fm=253&fmt=auto&app=138&f=JPEG?w=822&h=500') center center no-repeat;
    background-size: cover;
    padding-top: 30px;
}

.profile img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-bottom: 10px;
}

.profile h1 {
    margin: 0;
    color: #fff;
}

.profile p {
    color: #777;
}

.user {
    &-group {
        margin-bottom: 15px;
    }
    &-links {
        padding: 15px 0;
        font-size: 12px;
        text-align: center;
        .van-icon {
            display: block;
            font-size: 24px;
        }
    }
}
</style>
