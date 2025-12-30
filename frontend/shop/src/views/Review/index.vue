<template>
    <div class="review-page">
        <van-nav-bar title="商品评价" left-arrow @click-left="goBack" />

        <!-- 商品信息 -->
        <div class="product-info" v-if="product">
            <van-card :title="product.title" :thumb="`http://localhost:8080${product.photo}`" :desc="product.description">
                <template #tags>
                    <van-tag type="primary">¥{{ product.price }}</van-tag>
                </template>
            </van-card>
        </div>

        <!-- 评价列表 -->
        <div class="reviews-section" v-if="reviews.length > 0">
            <van-divider>用户评价</van-divider>

            <div class="rating-summary" v-if="ratingInfo">
                <div class="rating-score">
                    <span class="score">{{ ratingInfo.average }}</span>
                    <span class="label">综合评分</span>
                </div>
                <div class="rating-count">
                    <span class="count">{{ ratingInfo.count }}条评价</span>
                </div>
            </div>

            <van-list>
                <div v-for="review in reviews" :key="review.id" class="review-item">
                    <div class="review-header">
                        <div class="user-info">
                            <van-image :src="review.isAnonymous ? '/src/assets/default-avatar.png' : getUserAvatar(review.reviewerUsername)" class="avatar" round width="40" height="40" />
                            <div class="user-details">
                                <span class="username">{{ review.isAnonymous ? "匿名用户" : review.reviewerUsername }}</span>
                                <div class="rating">
                                    <van-rate v-model="review.rating" readonly size="14" color="#ffd21e" void-color="#c7c7c7" />
                                </div>
                            </div>
                        </div>
                        <div class="review-time">
                            {{ formatTime(review.createdAt) }}
                        </div>
                    </div>
                    <div class="review-content">
                        {{ review.content }}
                    </div>
                </div>
            </van-list>
        </div>

        <!-- 空状态 -->
        <van-empty v-else description="暂无评价" />

        <!-- 添加评价按钮 -->
        <div class="add-review-btn" v-if="canAddReview">
            <van-button type="primary" block @click="showAddReviewDialog"> 添加评价 </van-button>
        </div>

        <!-- 添加评价弹窗 -->
        <van-dialog v-model:show="showDialog" title="添加评价" show-cancel-button @confirm="submitReview">
            <div class="review-form">
                <div class="rating-section">
                    <span class="label">评分：</span>
                    <van-rate v-model="newReview.rating" size="20" color="#ffd21e" void-color="#c7c7c7" />
                </div>
                <van-field v-model="newReview.content" type="textarea" placeholder="请输入评价内容" rows="4" maxlength="200" show-word-limit />
                <van-checkbox v-model="newReview.isAnonymous"> 匿名评价 </van-checkbox>
            </div>
        </van-dialog>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue"
import { useRoute, useRouter } from "vue-router"
import { getReviewsByBusinessId, addReview, getUserRating } from "@/api"
import { showToast, showSuccessToast } from "vant"

const route = useRoute()
const router = useRouter()

const product = ref(null)
const reviews = ref([])
const ratingInfo = ref(null)
const showDialog = ref(false)
const canAddReview = ref(false)

const newReview = ref({
    rating: 5,
    content: "",
    isAnonymous: false,
})

const businessId = computed(() => route.params.businessId || route.query.businessId)

// 获取商品评价
const getReviews = async () => {
    try {
        const res = await getReviewsByBusinessId(businessId.value);
        // 后端Result类没有code字段，根据实际返回格式处理
        if (res && Array.isArray(res.results)) {
            reviews.value = res.results;
        } else if (res && res.results === null && res.msg === 'success') {
            // 处理空数据情况
            reviews.value = [];
        } else if (Array.isArray(res)) {
            reviews.value = res;
        } else {
            reviews.value = [];
            showToast(res?.msg || '获取评价失败');
        }
    } catch (error) {
        console.error("获取评价失败:", error);
        showToast("获取评价失败");
    }
};

// 获取评分统计
const getRatingStats = async () => {
    try {
        // 确保sellerUsername有值
        const sellerUsername = route.query.sellerUsername || 'default_seller';
        const res = await getUserRating(sellerUsername);
        // 后端Result类没有code字段，根据实际返回格式处理
        if (res && typeof res.results === 'string') {
            ratingInfo.value = {
                average: parseFloat(res.results.split("平均评分: ")[1]?.split(",")[0]) || 0,
                count: parseInt(res.results.split("评价总数: ")[1]) || 0,
            };
        } else if (res && typeof res === 'string') {
            ratingInfo.value = {
                average: parseFloat(res.split("平均评分: ")[1]?.split(",")[0]) || 0,
                count: parseInt(res.split("评价总数: ")[1]) || 0,
            };
        }
    } catch (error) {
        console.error("获取评分统计失败:", error);
    }
};

// 提交评价
const submitReview = async () => {
    if (!newReview.value.content.trim()) {
        showToast("请输入评价内容");
        return;
    }

    try {
        // 确保orderId是一个有效的整型值，不超过Integer最大值2147483647
        const MAX_INTEGER = 2147483647;
        let orderId = parseInt(route.query.orderId);
        
        // 如果解析失败或超过最大值，使用随机数生成一个有效的orderId
        if (isNaN(orderId) || orderId > MAX_INTEGER) {
            orderId = Math.floor(Math.random() * 1000000); // 使用较小的随机数
        }
        
        // 确保reviewedUsername有值
        const reviewedUsername = route.query.sellerUsername || 'default_seller';
        
        const reviewData = {
            orderId: orderId,
            reviewerUsername: localStorage.getItem("username"),
            reviewedUsername: reviewedUsername,
            businessId: parseInt(businessId.value), // 确保businessId是整型
            rating: newReview.value.rating,
            content: newReview.value.content,
            reviewType: 1, // 买家评价卖家
            isAnonymous: newReview.value.isAnonymous,
        };

        console.log("提交的评价数据:", reviewData); // 添加日志以便调试
        
        const res = await addReview(reviewData);
        // 后端Result类没有code字段，根据msg判断成功
        if (res && res.msg === '评价添加成功') {
            showSuccessToast("评价提交成功");
            showDialog.value = false;
            newReview.value = {
                rating: 5,
                content: "",
                isAnonymous: false,
            };
            getReviews();
            getRatingStats();
        } else {
            showToast(res?.msg || "评价提交失败");
            console.error("评价提交失败响应:", res);
        }
    } catch (error) {
        console.error("提交评价失败:", error);
        showToast("评价提交失败");
    }
};

// 显示添加评价弹窗
const showAddReviewDialog = () => {
    showDialog.value = true
}

// 返回上一页
const goBack = () => {
    router.go(-1)
}

// 格式化时间
const formatTime = (timeStr) => {
    if (!timeStr) return ""
    const date = new Date(timeStr)
    return date.toLocaleDateString() + " " + date.toLocaleTimeString()
}

// 获取用户头像
const getUserAvatar = (username) => {
    // 这里应该根据用户名获取用户头像，暂时使用默认头像
    return "/src/assets/default-avatar.png"
}

onMounted(() => {
    getReviews()
    getRatingStats()

    // 检查是否可以添加评价（简化条件，只要有订单ID就显示添加评价按钮）
    canAddReview.value = !!route.query.orderId
    
    // 移除自动显示弹窗，让用户手动点击添加评价按钮
})
</script>

<style scoped lang="less">
.review-page {
    min-height: 100vh;
    background-color: #f7f8fa;
}

.product-info {
    background: white;
    margin-bottom: 10px;
}

.reviews-section {
    background: white;
    margin-bottom: 10px;
}

.rating-summary {
    display: flex;
    align-items: center;
    padding: 15px;
    border-bottom: 1px solid #f0f0f0;
}

.rating-score {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-right: 20px;

    .score {
        font-size: 24px;
        font-weight: bold;
        color: #ff6b35;
    }

    .label {
        font-size: 12px;
        color: #666;
        margin-top: 2px;
    }
}

.rating-count {
    flex: 1;

    .count {
        color: #666;
        font-size: 14px;
    }
}

.review-item {
    padding: 15px;
    border-bottom: 1px solid #f0f0f0;

    &:last-child {
        border-bottom: none;
    }
}

.review-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 10px;
}

.user-info {
    display: flex;
    align-items: center;

    .avatar {
        margin-right: 10px;
    }

    .user-details {
        .username {
            display: block;
            font-size: 14px;
            color: #333;
            margin-bottom: 4px;
        }

        .rating {
            display: flex;
            align-items: center;
        }
    }
}

.review-time {
    font-size: 12px;
    color: #999;
}

.review-content {
    font-size: 14px;
    line-height: 1.5;
    color: #333;
}

.add-review-btn {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 15px;
    background: white;
    border-top: 1px solid #f0f0f0;
}

.review-form {  
    padding: 20px 30px;

    .rating-section {
        display: flex;
        align-items: center;
        margin-bottom: 15px;

        .label {
            margin-right: 10px;
            font-size: 14px;
        }
    }
}
</style>
