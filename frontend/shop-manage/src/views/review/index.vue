<template>
    <div class="content">
        <el-card style="width: 1400px">
            <div style="display: flex; justify-content: space-between; align-items: center; padding: 20px 0;">
                <h1>评价管理</h1>
                <el-button type="primary" @click="loadReviews">
                    <el-icon><Refresh /></el-icon>
                    刷新
                </el-button>
            </div>
            <div class="table">
                <el-table :data="paginatedReviews" style="width: 100%" stripe border>
                    <el-table-column fixed label="评价ID" prop="id" width="80" />
                    <el-table-column label="评价者" prop="reviewerUsername" width="120" />
                    <!-- <el-table-column label="被评价者" prop="reviewedUsername" width="120" /> -->
                    <el-table-column label="商品ID" prop="businessId" width="100" />
                    <el-table-column label="评分" prop="rating" width="80" />
                    <el-table-column label="评价内容" prop="content" width="" />
                    <el-table-column label="评价类型" prop="reviewType" width="100" :formatter="formatReviewType" />
                    <el-table-column label="是否匿名" prop="isAnonymous" width="100" :formatter="formatIsAnonymous" />
                    <el-table-column label="创建时间" prop="createdAt" width="180" />
                    <el-table-column fixed="right" label="操作" width="100">
                        <template #default="scope">
                            <el-button type="danger" @click="deleteReview(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                
                <!-- 分页组件 -->
                <div class="pagination-container" v-if="total > 0">
                    <el-pagination
                        :page-sizes="[5, 10]"
                        layout="total, sizes, prev, pager, next"
                        :total="total"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange" />
                </div>
            </div>
        </el-card>
        <!-- 删除确认对话框 -->
        <el-dialog v-model="deleteDialogVisible" title="确认删除" width="30%">
            <span>是否确定删除此评价？</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="deleteDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmDelete"> 确定 </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script setup>
import { onMounted, computed } from "vue"
import { getAllReviews, deleteReview as apiDeleteReview } from "../../api"
import { ref } from "vue"
import { Refresh } from '@element-plus/icons-vue'

const reviews = ref([])
const deleteDialogVisible = ref(false)
const currentReviewId = ref(null)

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 分页后的数据
const paginatedReviews = computed(() => {
    total.value = reviews.value.length
    const startIndex = (currentPage.value - 1) * pageSize.value
    const endIndex = startIndex + pageSize.value
    return reviews.value.slice(startIndex, endIndex)
})

// 分页处理方法
const handleSizeChange = (size) => {
    pageSize.value = size
    currentPage.value = 1 // 重置到第一页
}

const handleCurrentChange = (current) => {
    currentPage.value = current
}

// 格式化评价类型
const formatReviewType = (row, column, cellValue) => {
    return cellValue === 1 ? '商品评价' : '服务评价'
}

// 格式化是否匿名
const formatIsAnonymous = (row, column, cellValue) => {
    return cellValue ? '是' : '否'
}

// 获取所有评价列表
const loadReviews = async () => {
    try {
        const res = await getAllReviews()
        reviews.value = res.results
        // 重置到第一页
        currentPage.value = 1
    } catch (error) {
        console.error('获取评价列表失败:', error)
    }
}

// 打开删除确认对话框
const deleteReview = (id) => {
    currentReviewId.value = id
    deleteDialogVisible.value = true
}

// 确认删除
const confirmDelete = async () => {
    try {
        await apiDeleteReview(currentReviewId.value)
        deleteDialogVisible.value = false
        loadReviews() // 重新加载评价列表
    } catch (error) {
        console.error('删除评价失败:', error)
    }
}

// 页面加载时获取评价列表
onMounted(() => {
    loadReviews()
})
</script>

<style scoped lang="less">
.content {
    display: flex;
    flex-direction: column;
    align-items: center;
    .table {
        width: 100%;
        
        .pagination-container {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
        }
    }
}
</style>