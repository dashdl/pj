<template>
    <div>
        <NavBar title="订单"></NavBar>
        <van-tabs
            v-model="active"
            @click="onClick">
            <van-tab title="我买到的">
                <van-card
                    v-for="(item, index) in personList"
                    :key="index"
                    :desc="item.description"
                    :title="item.title"
                    :thumb="`http://localhost:8080${item.photo}`"
                    :thumb-link="`/detail/${item.id}`">
                    <template #tags>
                        <p>
                            {{ item.name }}
                        </p>
                        <p>
                            {{ item.phone }}
                        </p>
                    </template>
                    <template #footer>
                        <div class="card-footer">
                            <van-button size="small" type="primary" @click="goToReview(item.id, item.username)">评价商品</van-button>
                        </div>
                    </template>
                </van-card>
                <van-empty
                    description="暂无订单"
                    v-show="!personList.length" />
            </van-tab>
            <van-tab title="我卖出的">
                <div
                    v-for="(item, index) in businessList"
                    :key="index"
                    class="business-item">
                    <div class="info">
                        <span class="label">姓名:</span>
                        <span class="value">{{ item.cusername }}</span>
                    </div>
                    <div class="info">
                        <span class="label">电话:</span>
                        <span class="value">{{ item.customerphone }}</span>
                    </div>
                    <div class="info">
                        <span class="label">地址:</span>
                        <span class="value">{{ item.customerposition }}</span>
                    </div>
                    <!-- <div class="info">
                        <span class="label">创建时间:</span>
                        <span class="value">
                            {{ formatCreatedAt(item.created_at) }}
                        </span>
                    </div> -->
                </div>
                <van-empty
                    description="暂无订单"
                    v-show="!businessList.length" />
            </van-tab>
        </van-tabs>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getPersonOrder, getBusinessOrder } from '@/api/index'
import NavBar from '../../components/NavBar/index.vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const active = ref(0)
const personList = ref([])
const businessList = ref([])

const onClick = (name) => {
    active.value = name
    name === 0 ? setPersonList() : setBusinessList()
}

const setPersonList = async () => {
    const res = await getPersonOrder(localStorage.getItem('username'))
    personList.value = res
    console.log(res)
}

const setBusinessList = async () => {
    const res = await getBusinessOrder(localStorage.getItem('username'))
    console.log(res)
    businessList.value = res
}

const formatCreatedAt = (dateTimeString) => {
    const originalDate = new Date(dateTimeString)
    return `${originalDate.getFullYear()}-${padZero(originalDate.getMonth() + 1)}-${padZero(originalDate.getDate())} ${padZero(originalDate.getHours())}:${padZero(
        originalDate.getMinutes()
    )}:${padZero(originalDate.getSeconds())}`
}

const padZero = (value) => {
    return value < 10 ? `0${value}` : `${value}`
}

// 跳转到评价页面
const goToReview = (businessId, sellerUsername) => {
    // 生成一个临时订单ID（实际应用中应该使用真实的订单ID）
    const tempOrderId = Date.now()
    router.push({
        path: `/review/${businessId}`,
        query: {
            orderId: tempOrderId,
            sellerUsername: sellerUsername || 'default_seller'
        }
    })
}

onMounted(() => {
    setPersonList()
})
</script>

<style scoped lang="less">
.business-item {
    padding: 15px;
    margin: 15px 20px;
    border-radius: 5px;
    box-shadow: 0 0 5px #ccc;
}

.info {
    margin: 8px 0;
}

.label {
    font-weight: bold;
    margin-right: 5px;
}

.value {
    flex-grow: 1;
}

.card-footer {
    display: flex;
    justify-content: flex-end;
    padding: 8px 0;
}
</style>
