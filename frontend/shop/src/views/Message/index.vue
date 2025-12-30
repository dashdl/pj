<template>
    <div class="message-page">
        <van-nav-bar title="消息中心" left-arrow @click-left="goBack">
            <template #right>
                <van-icon name="chat-o" size="18" @click="showChatList = !showChatList" />
            </template>
        </van-nav-bar>

        <!-- 消息类型切换 -->
        <van-tabs v-model:active="activeTab" @change="onTabChange">
            <van-tab title="聊天" name="chat" />
            <van-tab title="系统消息" name="system" />
        </van-tabs>

        <!-- 聊天列表 -->
        <div v-if="activeTab === 'chat'" class="chat-list">
            <div v-if="chatList.length > 0">
                <div
                    v-for="chat in chatList"
                    :key="chat.other_username || chat.otherUsername || chat.username || chat.id"
                    class="chat-item"
                    @click="openChat(chat.other_username || chat.otherUsername || chat.username)">
                    <van-image :src="getUserAvatar(chat.other_username || chat.otherUsername || chat.username)" class="avatar" round width="50" height="50" />
                    <div class="chat-content">
                        <div class="chat-header">
                            <span class="username">{{ chat.other_username || chat.otherUsername || chat.username || "对话" }}</span>
                            <span class="time">{{ formatTime(chat.last_message_time || chat.lastMessageTime || chat.created_at || chat.createdAt) }}</span>
                        </div>
                        <div class="last-message">
                            {{ getLastMessage(chat.other_username || chat.otherUsername || chat.username) }}
                        </div>
                    </div>
                    <!-- <div class="unread-badge" v-if="getUnreadCountForUser((chat && (chat.other_username || chat.otherUsername || chat.username)) || '') > 0">
                        {{ getUnreadCountForUser((chat && (chat.other_username || chat.otherUsername || chat.username)) || "") }}
                    </div> -->
                </div>
            </div>
            <van-empty v-else description="暂无聊天记录" />
        </div>

        <!-- 系统消息列表 -->
        <div v-if="activeTab === 'system'" class="system-messages">
            <div v-if="systemMessages.length > 0">
                <div v-for="message in systemMessages" :key="message.id" class="message-item" :class="{ unread: !message.is_read }">
                    <van-icon name="bell" class="message-icon" />
                    <div class="message-content">
                        <div class="message-text">{{ message.content }}</div>
                        <div class="message-time">{{ formatTime(message.created_at) }}</div>
                    </div>
                    <van-button v-if="!message.is_read" size="mini" type="primary" @click="markAsRead(message.id)"> 标记已读 </van-button>
                </div>
            </div>
            <van-empty v-else description="暂无系统消息" />
        </div>

        <!-- 聊天窗口 -->
        <van-popup v-model:show="showChat" position="bottom" :style="{ height: '70%' }" round>
            <div class="chat-window">
                <div class="chat-header">
                    <span class="chat-title">与 {{ currentChatUser }} 的对话</span>
                    <van-icon name="cross" @click="closeChat" />
                </div>

                <div class="chat-messages" ref="chatMessages">
                    <div v-for="message in conversation" :key="message.id || message.created_at || message.createdAt" class="message-bubble" :class="{ own: isOwn(message) }">
                        <div class="message-text">{{ message.content }}</div>
                        <div class="message-time">{{ formatTime(message.created_at || message.createdAt) }}</div>
                    </div>
                </div>

                <div class="chat-input">
                    <van-field v-model="newMessage" placeholder="输入消息..." @keyup.enter="sendMessageData">
                        <template #button>
                            <van-button size="small" type="primary" @click="sendMessageData" :disabled="!newMessage.trim()"> 发送 </van-button>
                        </template>
                    </van-field>
                </div>
            </div>
        </van-popup>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, nextTick } from "vue"
import { useRouter } from "vue-router"
import { getChatList, getConversation, sendMessage, markMessageAsRead, getSystemMessages, getUnreadCount as getUnreadCountApi } from "@/api"
import { showToast } from "vant"

const router = useRouter()

const activeTab = ref("chat")
const chatList = ref([])
const systemMessages = ref([])
const conversation = ref([])
const showChat = ref(false)
const currentChatUser = ref("")
const currentUser = ref(localStorage.getItem("username"))
const newMessage = ref("")
const unreadCounts = ref({})

// 获取聊天列表
const getChatListData = async () => {
    try {
        const res = await getChatList(currentUser.value)
        console.log("[message] chatList res:", res)
        const rows = Array.isArray(res?.data) ? res.data : Array.isArray(res?.results) ? res.results : Array.isArray(res) ? res : []
        chatList.value = rows.filter((item) => item && (item.other_username || item.otherUsername || item.username))
    } catch (error) {
        console.error("获取聊天列表失败:", error)
        showToast("获取聊天列表失败")
    }
}

// 获取系统消息
const getSystemMessagesData = async () => {
    try {
        const res = await getSystemMessages(currentUser.value)
        console.log("[message] system res:", res)
        const rows = Array.isArray(res?.data) ? res.data : Array.isArray(res?.results) ? res.results : Array.isArray(res) ? res : []
        systemMessages.value = rows
    } catch (error) {
        console.error("获取系统消息失败:", error)
        showToast("获取系统消息失败")
    }
}

// 获取对话内容
const getConversationData = async (user1, user2, preserveIfNewer = false) => {
    try {
        const res = await getConversation(user1, user2)
        console.log("[message] conversation res:", res)
        const rows = Array.isArray(res?.data) ? res.data : Array.isArray(res?.results) ? res.results : Array.isArray(res) ? res : []
        if (preserveIfNewer && conversation.value.length > 0) {
            // 如果服务端条数少于本地，直接保留本地（避免覆盖乐观插入）
            if (rows.length < conversation.value.length) {
                console.log("[message] keep local conversation; server length < local")
            } else {
                const localLast = conversation.value[conversation.value.length - 1]
                const serverLast = rows[rows.length - 1]
                const sameSender = (localLast?.sender_username || localLast?.senderUsername) === (serverLast?.sender_username || serverLast?.senderUsername)
                const sameContent = localLast?.content === serverLast?.content
                if (!sameSender || !sameContent) {
                    // 服务端最后一条与本地不同，说明还没同步到；先保留本地
                    console.log("[message] keep local conversation; server last different")
                } else {
                    conversation.value = rows
                }
            }
        } else {
            conversation.value = rows
        }
        nextTick(() => {
            scrollToBottom()
        })
    } catch (error) {
        console.error("获取对话失败:", error)
        showToast("获取对话失败")
    }
}

// 轮询直到服务端包含刚发送的消息
const pollConversationUntilIncludes = async (user1, user2, expectMsg, maxTries = 6, intervalMs = 300) => {
    for (let i = 0; i < maxTries; i++) {
        const res = await getConversation(user1, user2)
        const rows = Array.isArray(res?.data) ? res.data : Array.isArray(res?.results) ? res.results : Array.isArray(res) ? res : []
        const found = rows.some((m) => {
            const s = m?.sender_username || m?.senderUsername
            return s === expectMsg.sender && m?.content === expectMsg.content
        })
        if (found) {
            conversation.value = rows
            nextTick(() => scrollToBottom())
            return true
        }
        await new Promise((r) => setTimeout(r, intervalMs))
    }
    return false
}

// 获取未读消息数量
const getUnreadCountData = async () => {
    try {
        const res = await getUnreadCountApi(currentUser.value)
        const num = typeof res?.data === "number" ? res.data : typeof res?.results === "number" ? res.results : typeof res === "number" ? res : 0
        unreadCounts.value = { [currentChatUser.value]: num }
    } catch (error) {
        console.error("获取未读数量失败:", error)
    }
}

// 发送消息
const sendMessageData = async () => {
    if (!newMessage.value.trim()) return

    try {
        const messageData = {
            senderUsername: currentUser.value,
            receiverUsername: currentChatUser.value,
            content: newMessage.value,
            messageType: 1,
        }

        // 乐观更新：先本地插入再发请求
        const nowIso = new Date().toISOString()
        const sentText = newMessage.value
        const tmpMsg = {
            id: Date.now(),
            sender_username: currentUser.value,
            receiver_username: currentChatUser.value,
            content: sentText,
            created_at: nowIso,
        }
        console.log("[message] optimistic add:", tmpMsg)
        conversation.value.push(tmpMsg)
        // 强制触发响应式（保险）：
        conversation.value = conversation.value.slice()
        console.log("[message] conversation length after push:", conversation.value.length)
        nextTick(() => scrollToBottom())
        newMessage.value = ""

        console.log("[message] send payload:", messageData)
        const res = await sendMessage(messageData)
        console.log("[message] send response:", res)
        const ok = (res && res.code === 200) || res?.msg === "success"
        if (!ok) {
            // 回滚本地插入并提示
            conversation.value.pop()
            showToast(res?.data || res?.message || res?.msg || "发送失败")
            return
        }
        // 后端返回新记录 id，直接对齐本地数据，并做一次轻刷新
        const newId = res?.data || res?.results
        if (newId) {
            tmpMsg.id = newId
            conversation.value = conversation.value.slice()
        }
        setTimeout(() => {
            getConversationData(currentUser.value, currentChatUser.value, true)
            getChatListData()
        }, 200)
    } catch (error) {
        console.error("发送消息失败:", error)
        showToast("发送失败")
    }
}

// 标记消息为已读
const markAsRead = async (messageId) => {
    try {
        const res = await markMessageAsRead(messageId)
        if (res.code === 200) {
            getSystemMessagesData()
        }
    } catch (error) {
        console.error("标记已读失败:", error)
    }
}

// 打开聊天窗口
const openChat = async (username) => {
    currentChatUser.value = username
    showChat.value = true
    await getConversationData(currentUser.value, username)
    await getUnreadCountData()
}

// 关闭聊天窗口
const closeChat = () => {
    showChat.value = false
    conversation.value = []
}

// 标签页切换
const onTabChange = () => {
    if (activeTab.value === "system") {
        getSystemMessagesData()
    }
}

// 滚动到底部
const scrollToBottom = () => {
    const chatMessages = document.querySelector(".chat-messages")
    if (chatMessages) {
        chatMessages.scrollTop = chatMessages.scrollHeight
    }
}

// 获取最后一条消息
const getLastMessage = (username) => {
    // 这里应该从conversation中获取最后一条消息
    return "点击查看对话"
}

// 获取未读数量
const getUnreadCountForUser = (username) => {
    return unreadCounts.value[username] || 0
}

// 判断是否自己发送（兼容字段名）
const isOwn = (m) => {
    const me = (currentUser.value || "").trim()
    const sender = (m?.sender_username ?? m?.senderUsername ?? m?.sender ?? "").trim()
    const receiver = (m?.receiver_username ?? m?.receiverUsername ?? m?.receiver ?? "").trim()
    const chatPeer = (currentChatUser.value || "").trim()
    // 只要发送者是我，或接收者是当前对话对象，都视为我发出的（兼容字段异常时）
    if (sender && sender === me) return true
    if (receiver && chatPeer && receiver === chatPeer) return true
    return false
}

// 获取用户头像
const getUserAvatar = (username) => {
    return "/src/assets/default-avatar.png"
}

// 格式化时间
const formatTime = (timeStr) => {
    if (!timeStr) return ""
    const date = new Date(timeStr)
    const now = new Date()
    const diff = now - date

    if (diff < 60000) return "刚刚"
    if (diff < 3600000) return `${Math.floor(diff / 60000)}分钟前`
    if (diff < 86400000) return `${Math.floor(diff / 3600000)}小时前`
    if (diff < 604800000) return `${Math.floor(diff / 86400000)}天前`

    return date.toLocaleDateString()
}

// 返回上一页
const goBack = () => {
    router.go(-1)
}

// 定时刷新聊天列表
let refreshTimer = null

onMounted(() => {
    getChatListData()
    getSystemMessagesData()

    // 每30秒刷新一次聊天列表
    refreshTimer = setInterval(() => {
        getChatListData()
        if (activeTab.value === "system") {
            getSystemMessagesData()
        }
    }, 30000)
})

onUnmounted(() => {
    if (refreshTimer) {
        clearInterval(refreshTimer)
    }
})
</script>

<style scoped lang="less">
.message-page {
    min-height: 100vh;
    background-color: #f7f8fa;
}

.chat-list,
.system-messages {
    background: white;
    margin-top: 10px;
}

.chat-item {
    display: flex;
    align-items: center;
    padding: 15px;
    border-bottom: 1px solid #f0f0f0;
    cursor: pointer;

    &:hover {
        background-color: #f8f9fa;
    }

    &:last-child {
        border-bottom: none;
    }
}

.avatar {
    margin-right: 15px;
}

.chat-content {
    flex: 1;

    .chat-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 5px;

        .username {
            font-size: 16px;
            font-weight: 500;
            color: #333;
        }

        .time {
            font-size: 12px;
            color: #999;
        }
    }

    .last-message {
        font-size: 14px;
        color: #666;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
}

.unread-badge {
    background-color: #ff4d4f;
    color: white;
    border-radius: 10px;
    padding: 2px 8px;
    font-size: 12px;
    min-width: 20px;
    text-align: center;
}

.message-item {
    display: flex;
    align-items: flex-start;
    padding: 15px;
    border-bottom: 1px solid #f0f0f0;

    &.unread {
        background-color: #f8f9fa;
    }

    &:last-child {
        border-bottom: none;
    }
}

.message-icon {
    margin-right: 15px;
    margin-top: 5px;
    color: #1890ff;
}

.message-content {
    flex: 1;

    .message-text {
        font-size: 14px;
        color: #333;
        margin-bottom: 5px;
    }

    .message-time {
        font-size: 12px;
        color: #999;
    }
}

.chat-window {
    height: 100%;
    display: flex;
    flex-direction: column;
}

.chat-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    border-bottom: 1px solid #f0f0f0;

    .chat-title {
        font-size: 16px;
        font-weight: 500;
    }
}

.chat-messages {
    flex: 1;
    padding: 15px;
    overflow-y: auto;
    background-color: #f8f9fa;
}

.message-bubble {
    margin-bottom: 15px;

    &.own {
        text-align: right;

        .message-text {
            background-color: #1890ff;
            color: white;
        }
    }

    &:not(.own) {
        .message-text {
            background-color: white;
            color: #333;
        }
    }

    .message-text {
        display: inline-block;
        padding: 10px 15px;
        border-radius: 18px;
        max-width: 70%;
        word-wrap: break-word;
        font-size: 14px;
    }

    .message-time {
        font-size: 12px;
        color: #999;
        margin-top: 5px;
    }
}

.chat-input {
    padding: 15px;
    border-top: 1px solid #f0f0f0;
    background-color: white;
}

:deep(.van-tabs__content) {
    display: none;
}
</style>
