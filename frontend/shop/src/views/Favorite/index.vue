<template>
    <div class="favorite-page">
        <van-nav-bar
            title="我的收藏"
            left-arrow
            @click-left="goBack" />

        <!-- 收藏夹切换 -->
        <div class="folder-tabs">
            <van-tabs
                v-model:active="activeFolder"
                @change="onFolderChange">
                <van-tab
                    v-for="folder in folders"
                    :key="folder"
                    :title="folder"
                    :name="folder" />
            </van-tabs>
        </div>

        <!-- 收藏商品列表 -->
        <div
            class="favorites-list"
            v-if="favorites.length > 0">
            <van-card
                v-for="item in favorites"
                :key="item.id"
                :title="item.title"
                :desc="item.description"
                :thumb="`http://localhost:8080${item.photo}`"
                :thumb-link="`/detail/${item.id}`">
                <template #tags>
                    <van-tag
                        type="primary"
                        class="tag">
                        ¥{{ item.price }}
                    </van-tag>
                    <van-tag
                        class="tag"
                        v-if="item.status == 2"
                        type="danger">
                        已售出
                    </van-tag>
                </template>
                <template #footer>
                    <van-button
                        size="small"
                        type="danger"
                        @click="removeFavoriteItem(item.id)"
                        >
                        取消收藏
                    </van-button>
                </template>
            </van-card>
        </div>

        <!-- 空状态 -->
        <van-empty
            v-else
            description="暂无收藏商品" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getFavoritesByUsername, removeFavorite as removeFavoriteApi } from '@/api'
import { showToast, showSuccessToast, showConfirmDialog } from 'vant'

const router = useRouter()

const favorites = ref([])
const folders = ref(['默认收藏夹'])
const activeFolder = ref('默认收藏夹')

// 获取收藏夹列表
const getFolders = async () => {
    try {
        const username = localStorage.getItem('username')
        if (!username) {
            router.push('/login')
            return
        }

        // 仅保留默认收藏夹
        folders.value = ['默认收藏夹']
        if (folders.value.length > 0) {
            activeFolder.value = folders.value[0]
        }
    } catch (error) {
        console.error('获取收藏夹失败:', error)
        showToast('获取收藏夹失败')
    }
}

// 获取收藏商品列表
const getFavorites = async () => {
    try {
        const username = localStorage.getItem('username')
        if (!username) {
            router.push('/login')
            return
        }

        const res = await getFavoritesByUsername(username)
        console.log('[favorite] list res:', res)
        const rows = Array.isArray(res?.data) ? res.data : Array.isArray(res?.results) ? res.results : Array.isArray(res) ? res : []
        favorites.value = rows.filter((item) => !item.folder_name || item.folder_name === activeFolder.value)
    } catch (error) {
        console.error('获取收藏列表失败:', error)
        showToast('获取收藏列表失败')
    }
}

// 收藏夹切换（只有一个默认收藏夹，仍保持刷新逻辑）
const onFolderChange = () => {
    getFavorites()
}

// 取消收藏
const removeFavoriteItem = async (businessId) => {
    try {
        await showConfirmDialog({
            title: '确认取消收藏',
            message: '确定要取消收藏这个商品吗？'
        })

        const username = localStorage.getItem('username')
        // 乐观更新（先移除，失败回滚）
        const prev = favorites.value.slice()
        favorites.value = favorites.value.filter((it) => String(it.id) !== String(businessId))

        const res = await removeFavoriteApi(username, businessId)
        console.log('[favorite] remove res:', res)
        // 不再依赖返回体，直接以刷新列表为准
        showSuccessToast('取消收藏成功')
        await getFavorites()
    } catch (error) {
        if (error !== 'cancel') {
            console.error('取消收藏失败:', error)
            showToast('取消收藏失败')
        }
    }
}

// 已取消移动收藏夹能力

// 移除创建收藏夹能力：不再提供创建逻辑

// 返回上一页
const goBack = () => {
    router.go(-1)
}

onMounted(() => {
    getFolders()
    getFavorites()
})
</script>

<style scoped lang="less">
.favorite-page {
    min-height: 100vh;
    background-color: #f7f8fa;
    padding-bottom: 60px;
}

.folder-tabs {
    background: white;
    margin-bottom: 10px;
}

.favorites-list {
    background: white;

    .van-card {
        margin-bottom: 10px;
    }
}

.bottom-actions {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 15px;
    background: white;
    border-top: 1px solid #f0f0f0;
}

:deep(.van-tabs__content) {
    display: none;
}

:deep(.van-card__footer) {
    padding: 10px 16px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
}
.tag{
    margin-top: 8px;
    margin-right: 5px;
    padding: 2px 8px;
}
</style>
