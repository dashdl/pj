<template>
    <div>
        <NavBar title="详情" />
        <div class="content">
            <div class="title">{{ detail.title }}</div>
            <div class="description">{{ detail.description }}</div>
            <div class="created-at">发布时间：{{ formatDate(detail.created_at) }}</div>
            <img
                :src="`http://localhost:8080${detail.photo}`"
                alt=""
                class="photo" />
            <div class="info">
                <div class="name">姓名：{{ detail.name }}</div>
                <div class="phone">手机号：{{ detail.phone }}</div>
            </div>
            <div class="content-text">{{ detail.content }}</div>
            <div class="price">
                <span class="small">￥</span>
                {{ detail.price }}
            </div>
            <!-- 操作按钮 -->
            <div class="actions">
                <van-button
                    icon="star-o"
                    :type="isFavorite ? 'primary' : 'default'"
                    @click="toggleFavorite"
                    size="large">
                    {{ isFavorite ? '已收藏' : '收藏' }}
                </van-button>
                <van-button
                    icon="chat-o"
                    @click="openChat"
                    size="large">
                    联系卖家
                </van-button>
                <van-button
                    icon="warning-o"
                    type="warning"
                    @click="showReportDialog"
                    size="large">
                    举报
                </van-button>
            </div>
        </div>

        <div class="footer">
            <van-submit-bar
                button-text="立即购买"
                @submit="onSubmit"
                v-if="detail.status == 1" />
            <!-- <van-submit-bar
                button-text="取消订单"
                @submit="cancelSubmit"
                v-if="detail.status == 2" /> -->
        </div>

        <!-- 立即购买弹窗 -->
        <van-popup
            v-model:show="showPopup"
            position="bottom"
            closeable
            :style="{ height: '40%' }">
            <div>
                <van-address-edit
                    @save="onSave"
                    :show-area="false"
                    @change-detail="onChangeDetail" />
            </div>
        </van-popup>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import NavBar from '../../components/NavBar/index.vue'
import { getDetail, addOrder, checkFavorite, addFavorite, removeFavorite, sendMessage } from '@/api/index'
import { useRoute, useRouter } from 'vue-router'
import { showSuccessToast, showToast } from 'vant'

const route = useRoute()
const Id = ref('')
const detail = ref([])
const router = useRouter()
const showPopup = ref(false)
const isFavorite = ref(false)
const getList = async (id) => {
    const res = await getDetail(id)
    detail.value = res[0]
    console.log(detail)

    // 检查是否已收藏
    await checkFavoriteStatus()
}

// 检查收藏状态
const checkFavoriteStatus = async () => {
    try {
        const username = localStorage.getItem('username')
        if (!username) return

        const res = await checkFavorite(username, detail.value.id)
        if (typeof res?.data === 'boolean') {
            isFavorite.value = res.data
        } else if (typeof res?.results === 'boolean') {
            isFavorite.value = res.results
        } else if (typeof res === 'boolean') {
            isFavorite.value = res
        }
    } catch (error) {
        console.error('检查收藏状态失败:', error)
    }
}

// 切换收藏状态
const toggleFavorite = async () => {
    try {
        const username = localStorage.getItem('username')
        if (!username) {
            showToast('请先登录')
            router.push('/login')
            return
        }

        if (isFavorite.value) {
            // 取消收藏
            const res = await removeFavorite(username, detail.value.id)
            const ok =
                (res && res.code === 200) ||
                res?.msg === 'success' ||
                res?.message === 'success' ||
                (typeof res?.message === 'string' && res.message.includes('成功')) ||
                (typeof res?.msg === 'string' && res.msg.includes('成功')) ||
                res?.data === 'success' ||
                res === true
            if (ok) {
                isFavorite.value = false
                showSuccessToast('取消收藏成功')
            } else {
                showToast(res?.message || res?.msg || res?.data || '取消收藏失败')
            }
        } else {
            // 添加收藏
            const favoriteData = {
                username: username,
                businessId: detail.value.id,
                folderName: '默认收藏夹'
            }
            const res = await addFavorite(favoriteData)
            const ok =
                (res && res.code === 200) ||
                res?.msg === 'success' ||
                res?.message === 'success' ||
                (typeof res?.message === 'string' && res.message.includes('成功')) ||
                (typeof res?.msg === 'string' && res.msg.includes('成功'))
            if (ok) {
                isFavorite.value = true
                showSuccessToast('收藏成功')
            } else {
                showToast(res?.message || res?.msg || '收藏失败')
            }
        }
    } catch (error) {
        console.error('收藏操作失败:', error)
        showToast('操作失败')
    }
}

// 联系卖家
const openChat = async () => {
    try {
        const username = localStorage.getItem('username')
        if (!username) {
            showToast('请先登录')
            router.push('/login')
            return
        }

        if (username === detail.value.username) {
            showToast('不能和自己聊天')
            return
        }

        // 发送初始消息
        const messageData = {
            senderUsername: username,
            receiverUsername: detail.value.username,
            businessId: detail.value.id,
            content: `你好，我想了解一下商品"${detail.value.title}"`,
            messageType: 1
        }

        const res = await sendMessage(messageData)
        console.log('[detail] sendMessage res:', res)
        const ok = (res && res.code === 200) || res?.msg === 'success' || res?.message === 'success' || res?.data === '消息发送成功' || res === '消息发送成功'
        if (ok) {
            showSuccessToast('消息发送成功')
            router.push('/message')
        } else {
            showToast(res?.data || res?.message || res?.msg || '发送失败')
        }
    } catch (error) {
        console.error('发送消息失败:', error)
        showToast('发送失败')
    }
}

// 显示举报弹窗
const showReportDialog = () => {
    const username = localStorage.getItem('username')
    if (!username) {
        showToast('请先登录')
        router.push('/login')
        return
    }

    // 跳转到举报页面
    router.push({
        path: '/report',
        query: {
            businessId: detail.value.id,
            username: detail.value.username
        }
    })

    
}

const formatDate = (dateTimeString) => {
    const date = new Date(dateTimeString)
    return `${date.getFullYear()}-${padZero(date.getMonth() + 1)}-${padZero(date.getDate())}`
}

const padZero = (value) => {
    return value < 10 ? `0${value}` : `${value}`
}

const onSubmit = () => {
    showPopup.value = true
    console.log(showPopup.value)
}

const onSave = async (content) => {
    const data = {
        cusername: content.name,
        customername: localStorage.getItem('username'),
        customerphone: content.tel,
        customerposition: content.addressDetail,
        business_id: detail.value.id,
        busername: detail.value.username
    }
    const res = await addOrder(data)
    console.log(res)
    content = []
    showPopup.value = false
    showSuccessToast('购买成功！')
    // router.replace({ path: `/list/${detail.value.type}` })
    router.push('/home')
}

onMounted(() => {
    Id.value = route.params.id
    getList(Id.value)
})
</script>

<style scoped lang="less">
.content {
    padding: 16px;
    height: 100vh;
    background-color: #f7f8fa;
    padding-bottom: 80px;
}

.title {
    font-size: 20px;
    font-weight: bold;
    margin: 15px 0;
}

.description {
    font-size: 16px;
    color: #666;
    margin-bottom: 8px;
}

.created-at {
    font-size: 14px;
    color: #888;
    margin-bottom: 8px;
}

.photo {
    width: 100%;
    margin-bottom: 8px;
}

.info {
    display: flex;
    justify-content: space-between;
    margin-bottom: 8px;
}

.name,
.phone {
    flex-grow: 1;
    font-size: 14px;
    color: #333;
}

.content-text {
    font-size: 14px;
    line-height: 1.5;
    color: #333;
    padding: 10px;
    padding-bottom: 50px;
    box-shadow: 0 0 5px #ccc;
    margin-top: 20px;
    border-radius: 4px;
}

.price {
    color: #ee0a24;
    font-weight: bold;
    font-size: 23px;
    text-align: right;
    padding: 15px 6px;
    .small {
        font-size: 14px;
    }
}

.actions {
    display: flex;

    .van-button {
        flex: 1;
        margin: 0 5px;

        &:first-child {
            margin-left: 0;
        }

        &:last-child {
            margin-right: 0;
        }
    }
}
</style>
