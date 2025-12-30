<template>
    <div class="content">
        <el-card style="width: 1400px">
            <div class="card-header">
                <h1 style="font-size: 20px">举报管理</h1>
                <div style="display: flex">
                    <div class="card-header-item">
                        <el-select
                            v-model="statusFilter"
                            placeholder="状态筛选">
                            <el-option
                                label="全部"
                                value="" />
                            <el-option
                                label="待处理"
                                value="1" />
                            <el-option
                                label="处理中"
                                value="2" />
                            <el-option
                                label="已处理"
                                value="3" />
                            <el-option
                                label="已驳回"
                                value="4" />
                        </el-select>
                    </div>
                    <div class="card-header-item">
                        <el-button
                            type="primary"
                            @click="getReportList">
                            刷新
                        </el-button>
                    </div>
                </div>
            </div>

            <div class="table">
                <el-table
                    :data="paginatedReports"
                    style="width: 100%"
                    stripe
                    border>
                    <el-table-column
                        prop="id"
                        label="ID"
                        width="80" />
                    <el-table-column
                        prop="reporterUsername"
                        label="举报人"
                        width="120" />
                    <el-table-column
                        label="举报类型"
                        width="100">
                        <template v-slot="{ row }">
                            {{ getReportedTypeText(row.reportedType) }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="reportedId"
                        label="被举报对象ID"
                        width="120" />
                    <el-table-column
                        prop="reportedUsername"
                        label="被举报用户"
                        width="120" />
                    <el-table-column
                        label="举报原因"
                        width="100">
                        <template v-slot="{ row }">
                            {{ getReasonText(row.reason) }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="description"
                        label="详细描述"
                        min-width="200" />
                    <el-table-column
                        label="状态"
                        width="100">
                        <template v-slot="{ row }">
                            <el-tag :type="getStatusTagType(row.status)">
                                {{ getStatusText(row.status) }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="处理人"
                        width="100">
                        <template v-slot="{ row }">
                            {{ row.processedBy || '-' }}
                        </template>
                    </el-table-column>
                    <!-- <el-table-column
                        label="处理时间"
                        width="160">
                        <template v-slot="{ row }">
                            {{ formatDate(row.processedAt) }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="举报时间"
                        width="160">
                        <template v-slot="{ row }">
                            {{ formatDate(row.createdAt) }}
                        </template>
                    </el-table-column> -->
                    <el-table-column
                        fixed="right"
                        label="操作"
                        width="200">
                        <template #default="scope">
                            <el-button
                                type="primary"
                                size="small"
                                @click="showProcessDialog(scope.row)"
                                v-if="scope.row.status === 1">
                                处理
                            </el-button>
                            <el-button
                                type="info"
                                size="small"
                                @click="viewDetails(scope.row)">
                                详情
                            </el-button>
                            <el-button
                                type="danger"
                                size="small"
                                @click="deleteReportItem(scope.row.id)">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 分页组件 -->
                <div
                    class="pagination-container"
                    v-if="total > 0">
                    <el-pagination
                        :page-sizes="[5, 10]"
                        layout="total, sizes, prev, pager, next"
                        :total="total"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange" />
                </div>
            </div>
        </el-card>

        <!-- 处理举报弹窗 -->
        <el-dialog
            v-model="showProcessDialogFlag"
            title="处理举报"
            width="600px">
            <el-form
                :model="processForm"
                :rules="processRules"
                ref="processFormRef"
                label-width="120px">
                <el-form-item
                    label="处理状态"
                    prop="status">
                    <el-select
                        v-model="processForm.status"
                        placeholder="请选择处理状态">
                        <el-option
                            label="处理中"
                            :value="2" />
                        <el-option
                            label="已处理"
                            :value="3" />
                        <el-option
                            label="已驳回"
                            :value="4" />
                    </el-select>
                </el-form-item>
                <el-form-item
                    label="管理员备注"
                    prop="adminRemark">
                    <el-input
                        v-model="processForm.adminRemark"
                        type="textarea"
                        placeholder="请输入处理备注"
                        rows="4" />
                </el-form-item>
                <el-form-item
                    label="处理人"
                    prop="processedBy">
                    <el-input
                        v-model="processForm.processedBy"
                        placeholder="请输入处理人" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="showProcessDialogFlag = false">取消</el-button>
                    <el-button
                        type="primary"
                        @click="submitProcess">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 举报详情弹窗 -->
        <el-dialog
            v-model="showDetailDialog"
            title="举报详情"
            width="800px">
            <div
                v-if="selectedReport"
                class="report-details">
                <el-descriptions
                    :column="2"
                    border>
                    <el-descriptions-item label="举报人">
                        {{ selectedReport.reporterUsername }}
                    </el-descriptions-item>
                    <el-descriptions-item label="举报类型">
                        {{ getReportedTypeText(selectedReport.reportedType) }}
                    </el-descriptions-item>
                    <el-descriptions-item label="被举报对象ID">
                        {{ selectedReport.reportedId }}
                    </el-descriptions-item>
                    <el-descriptions-item label="被举报用户">
                        {{ selectedReport.reportedUsername || '-' }}
                    </el-descriptions-item>
                    <el-descriptions-item label="举报原因">
                        {{ getReasonText(selectedReport.reason) }}
                    </el-descriptions-item>
                    <el-descriptions-item label="状态">
                        <el-tag :type="getStatusTagType(selectedReport.status)">
                            {{ getStatusText(selectedReport.status) }}
                        </el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item label="举报时间">
                        {{ formatDate(selectedReport.createdAt) }}
                    </el-descriptions-item>
                    <el-descriptions-item label="处理时间">
                        {{ formatDate(selectedReport.processedAt) }}
                    </el-descriptions-item>
                </el-descriptions>

                <div class="description-section">
                    <h4>详细描述：</h4>
                    <p>{{ selectedReport.description }}</p>
                </div>

                <div
                    class="remark-section"
                    v-if="selectedReport.adminRemark">
                    <h4>管理员备注：</h4>
                    <p>{{ selectedReport.adminRemark }}</p>
                </div>

                <div
                    class="evidence-section"
                    v-if="selectedReport.evidenceImages">
                    <h4>证据图片：</h4>
                    <div class="image-gallery">
                        <el-image
                            v-for="(image, index) in selectedReport.evidenceImages.split(',')"
                            :key="index"
                            :src="`http://localhost:8080${image}`"
                            :preview-src-list="selectedReport.evidenceImages.split(',').map((img) => `http://localhost:8080${img}`)"
                            style="width: 100px; height: 100px; margin-right: 10px" />
                    </div>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { getAllReports, processReport, deleteReport } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const reportList = ref([])
const statusFilter = ref('')
const showProcessDialogFlag = ref(false)
const showDetailDialog = ref(false)
const selectedReport = ref(null)
const processFormRef = ref(null)

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const processForm = reactive({
    id: '',
    status: '',
    adminRemark: '',
    processedBy: ''
})

const processRules = reactive({
    status: [{ required: true, message: '请选择处理状态', trigger: 'change' }],
    processedBy: [{ required: true, message: '请输入处理人', trigger: 'blur' }]
})

const filteredReports = computed(() => {
    if (!statusFilter.value) {
        return reportList.value
    }
    return reportList.value.filter((report) => report.status.toString() === statusFilter.value)
})

// 分页后的数据
const paginatedReports = computed(() => {
    total.value = filteredReports.value.length
    const startIndex = (currentPage.value - 1) * pageSize.value
    const endIndex = startIndex + pageSize.value
    return filteredReports.value.slice(startIndex, endIndex)
})

// 获取举报列表
const getReportList = async () => {
    try {
        const res = await getAllReports()
        console.log('[report] getAllReports res:', res)
        // 后端Result类没有code字段，根据实际返回格式处理
        if (res && Array.isArray(res.results)) {
            reportList.value = res.results
        } else if (res && res.results === null && res.msg === 'success') {
            // 处理空数据情况
            reportList.value = []
        } else if (Array.isArray(res)) {
            reportList.value = res
        } else {
            reportList.value = []
            if (res && res.msg) {
                ElMessage.info(res.msg)
            }
        }
        // 重置到第一页
        currentPage.value = 1
    } catch (error) {
        console.error('获取举报列表失败:', error)
        ElMessage.error('获取举报列表失败')
    }
}

// 显示处理弹窗
const showProcessDialog = (report) => {
    selectedReport.value = report
    processForm.id = report.id
    processForm.status = ''
    processForm.adminRemark = ''
    processForm.processedBy = ''
    showProcessDialogFlag.value = true
}

// 提交处理
const submitProcess = async () => {
    try {
        await processFormRef.value.validate()

        const res = await processReport(processForm.id, processForm.status, processForm.adminRemark, processForm.processedBy)

        // 后端Result类没有code字段，根据msg判断成功
        if (res && res.msg === '举报处理完成') {
            console.log(111)
            ElMessage.success('处理成功')
            showProcessDialogFlag.value = false
            getReportList()
        } else {
            ElMessage.error(res?.msg || '处理失败')
        }
    } catch (error) {
        console.error('处理举报失败:', error)
        ElMessage.error('处理失败')
    }
}

// 查看详情
const viewDetails = (report) => {
    selectedReport.value = report
    showDetailDialog.value = true
}

// 删除举报
const deleteReportItem = async (id) => {
    try {
        await ElMessageBox.confirm('确定要删除这个举报吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        })

        const res = await deleteReport(id)
        console.log(res, 'res')

        // 后端Result类没有code字段，根据msg判断成功
        if (res && res.msg === '举报删除成功') {
            ElMessage.success('删除成功')
            getReportList()
        } else {
            ElMessage.error(res?.msg || '删除失败')
        }
    } catch (error) {
        if (error !== 'cancel') {
            console.error('删除举报失败:', error)
            ElMessage.error('删除失败')
        }
    }
}

// 获取举报类型文本
const getReportedTypeText = (type) => {
    const types = {
        1: '商品',
        2: '用户',
        3: '消息'
    }
    return types[type] || '未知'
}

// 获取举报原因文本
const getReasonText = (reason) => {
    const reasons = {
        1: '虚假信息',
        2: '价格欺诈',
        3: '商品不符',
        4: '恶意骚扰',
        5: '其他'
    }
    return reasons[reason] || '未知'
}

// 获取状态标签类型
const getStatusTagType = (status) => {
    const types = {
        1: 'warning',
        2: 'info',
        3: 'success',
        4: 'danger'
    }
    return types[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
    const texts = {
        1: '待处理',
        2: '处理中',
        3: '已处理',
        4: '已驳回'
    }
    return texts[status] || '未知'
}

// 格式化日期
const formatDate = (dateStr) => {
    if (!dateStr) return '-'
    const date = new Date(dateStr)
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
}

// 分页处理方法
const handleSizeChange = (size) => {
    pageSize.value = size
    currentPage.value = 1 // 重置到第一页
}

const handleCurrentChange = (current) => {
    currentPage.value = current
}

onMounted(() => {
    getReportList()
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

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px 0px 30px 0;
}

.card-header-item {
    margin-left: 20px;
    display: flex;
    align-items: center;
}

.report-details {
    .description-section,
    .remark-section {
        margin-top: 20px;

        h4 {
            margin-bottom: 10px;
            color: #333;
        }

        p {
            line-height: 1.6;
            color: #666;
        }
    }

    .evidence-section {
        margin-top: 20px;

        h4 {
            margin-bottom: 10px;
            color: #333;
        }

        .image-gallery {
            display: flex;
            flex-wrap: wrap;
        }
    }
}
</style>
