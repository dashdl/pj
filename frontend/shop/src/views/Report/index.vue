<template>
    <div class="report-page">
        <van-nav-bar
            title="举报"
            left-arrow
            @click-left="goBack" />

        <div class="report-form">
            <van-form @submit="submitReport">
                <!-- 举报类型 -->
                <van-field
                    v-model="reportForm.reportedType"
                    name="reportedType"
                    label="举报类型"
                    placeholder="请选择举报类型"
                    readonly
                    is-link
                    @click="showTypePicker = true"
                    :rules="[{ required: true, message: '请选择举报类型' }]" />

                <!-- 举报对象 -->
                <van-field
                    v-model="reportForm.reportedId"
                    name="reportedId"
                    label="举报对象ID"
                    placeholder="请输入举报对象ID"
                    :rules="[{ required: true, message: '请输入举报对象ID' }]" />

                <van-field
                    v-model="reportForm.reportedUsername"
                    name="reportedUsername"
                    label="被举报用户名"
                    placeholder="请输入被举报用户名（可选）" />

                <!-- 举报原因 -->
                <van-field
                    v-model="reportForm.reason"
                    name="reason"
                    label="举报原因"
                    placeholder="请选择举报原因"
                    readonly
                    is-link
                    @click="showReasonPicker = true"
                    :rules="[{ required: true, message: '请选择举报原因' }]" />

                <!-- 详细描述 -->
                <van-field
                    v-model="reportForm.description"
                    name="description"
                    label="详细描述"
                    type="textarea"
                    placeholder="请详细描述举报原因，我们会认真处理您的举报"
                    rows="4"
                    maxlength="500"
                    show-word-limit
                    :rules="[{ required: true, message: '请输入详细描述' }]" />

                <!-- 证据图片 -->
                <van-field
                    name="evidenceImages"
                    label="证据图片">
                    <template #input>
                        <van-uploader
                            v-model="fileList"
                            :max-count="3"
                            :after-read="afterRead"
                            :before-delete="beforeDelete"
                            upload-text="上传图片" />
                    </template>
                </van-field>

                <!-- 提交按钮 -->
                <div class="submit-btn">
                    <van-button
                        round
                        block
                        type="primary"
                        native-type="submit"
                        :loading="submitting">
                        提交举报
                    </van-button>
                </div>
            </van-form>
        </div>

        <!-- 举报类型选择器 -->
        <van-popup
            v-model:show="showTypePicker"
            position="bottom">
            <van-picker
                :columns="typeColumns"
                @confirm="onTypeConfirm"
                @cancel="showTypePicker = false" />
        </van-popup>

        <!-- 举报原因选择器 -->
        <van-popup
            v-model:show="showReasonPicker"
            position="bottom">
            <van-picker
                :columns="reasonColumns"
                @confirm="onReasonConfirm"
                @cancel="showReasonPicker = false" />
        </van-popup>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { addReport, getUpload } from '@/api'
import { showToast, showSuccessToast, showConfirmDialog } from 'vant'

const route = useRoute()
const router = useRouter()

const submitting = ref(false)
const showTypePicker = ref(false)
const showReasonPicker = ref(false)
const fileList = ref([])

const reportForm = reactive({
    reportedType: '',
    reportedId: '',
    reportedUsername: '',
    reason: '',
    description: '',
    evidenceImages: ''
})

const typeColumns = [
    { text: '商品', value: 1 },
    { text: '用户', value: 2 },
    { text: '消息', value: 3 }
]

const reasonColumns = [
    { text: '虚假信息', value: 1 },
    { text: '价格欺诈', value: 2 },
    { text: '商品不符', value: 3 },
    { text: '恶意骚扰', value: 4 },
    { text: '其他', value: 5 }
]

// 举报类型选择
const onTypeConfirm = ({ selectedOptions }) => {
    reportForm.reportedType = selectedOptions[0].value
    showTypePicker.value = false
}

// 举报原因选择
const onReasonConfirm = ({ selectedOptions }) => {
    reportForm.reason = selectedOptions[0].value
    showReasonPicker.value = false
}

// 图片上传
const afterRead = async (file) => {
    try {
        const formData = new FormData()
        formData.append('image', file.file)

        const res = await getUpload(formData)
        if (res) {
            file.url = res.imagePath
            showToast('图片上传成功')
        } else {
            showToast('图片上传失败')
        }
    } catch (error) {
        console.error('图片上传失败:', error)
        showToast('图片上传失败')
    }
}

// 图片删除
const beforeDelete = async (file) => {
    try {
        await showConfirmDialog({
            title: '确认删除',
            message: '确定要删除这张图片吗？'
        })
        return true
    } catch (error) {
        return false
    }
}

// 提交举报
const submitReport = async () => {
    if (submitting.value) return

    try {
        await showConfirmDialog({
            title: '确认提交',
            message: '确定要提交举报吗？我们会认真处理您的举报。'
        })

        submitting.value = true

        // 处理图片路径
        const imagePaths = fileList.value
            .filter((file) => file.url)
            .map((file) => file.url)
            .join(',')

        const reportData = {
            reporterUsername: localStorage.getItem('username'),
            reportedType: reportForm.reportedType,
            reportedId: parseInt(reportForm.reportedId),
            reportedUsername: reportForm.reportedUsername || null,
            reason: reportForm.reason,
            description: reportForm.description,
            evidenceImages: imagePaths,
            status: 1 // 待处理
        }

        const res = await addReport(reportData)
        if (res) {
            showSuccessToast('举报提交成功，我们会尽快处理')

            // 重置表单
            Object.keys(reportForm).forEach((key) => {
                reportForm[key] = ''
            })
            fileList.value = []

            // 返回上一页
            setTimeout(() => {
                router.go(-1)
            }, 1000)
        } else {
            showToast(res.message || '举报提交失败')
        }
    } catch (error) {
        if (error !== 'cancel') {
            console.error('提交举报失败:', error)
            showToast('举报提交失败')
        }
    } finally {
        submitting.value = false
    }
}

// 返回上一页
const goBack = () => {
    router.go(-1)
}

// 初始化表单数据
const initFormData = () => {
    // 从路由参数获取预填数据
    if (route.query.businessId) {
        reportForm.reportedType = 1 // 商品
        reportForm.reportedId = route.query.businessId
    }

    if (route.query.userId) {
        reportForm.reportedType = 2 // 用户
        reportForm.reportedId = route.query.userId
        reportForm.reportedUsername = route.query.username
    }
}

onMounted(() => {
    initFormData()
})
</script>

<style scoped lang="less">
.report-page {
    min-height: 100vh;
    background-color: #f7f8fa;
}

.report-form {
    padding: 15px;
}

.submit-btn {
    margin-top: 30px;
    padding: 0 15px;
}

:deep(.van-field__label) {
    width: 100px;
    color: #333;
}

:deep(.van-uploader__upload) {
    width: 80px;
    height: 80px;
    border: 1px dashed #dcdee0;
    border-radius: 4px;
}

:deep(.van-uploader__preview) {
    width: 80px;
    height: 80px;
    border-radius: 4px;
}

:deep(.van-picker__toolbar) {
    padding: 15px;
}

:deep(.van-picker__columns) {
    padding: 0 15px;
}
</style>
