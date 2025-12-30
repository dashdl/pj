<template>
    <div class="content">
        <el-card style="width: 1400px">
            <div style="display: flex; justify-content: space-between; align-items: center; padding: 20px 0;">
                <h1>订单管理</h1>
                <el-button type="primary" @click="getList">
                    <el-icon><Refresh /></el-icon>
                    刷新
                </el-button>
            </div>
            <div class="table">
                <el-table
                    :data="paginatedList"
                    style="width: 100%"
                    stripe
                    border>
                    <el-table-column
                        fixed
                        label="订单ID"
                        prop="business_id"
                        width="120" />
                    <el-table-column
                        fixed
                        label="商家名字"
                        prop="busername"
                        width="120" />
                    <el-table-column
                        label="顾客名字"
                        prop="cusername"
                        width="120" />
                    <el-table-column
                        label="顾客手机号"
                        prop="customerphone"
                        width="150" />
                    <el-table-column
                        label="交易地址"
                        prop="customerposition"
                        width="" />
                    <el-table-column
                        label="下单时间"
                        prop="created_at"
                        width=""
                        :formatter="formatDate" />
                    <el-table-column
                        fixed="right"
                        label="操作"
                        width="160">
                        <template #default="scope">
                            <el-button
                                type="primary"
                                @click="openModify(scope.row)">
                                编辑
                            </el-button>
                            <el-button
                                type="danger"
                                @click="openDelete(scope.row.id)">
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
        <el-dialog
            v-model="modifyShow"
            title="修改订单"
            width="700">
            <el-form
                :model="modify"
                :inline="true"
                :rules="rules"
                ref="modifyFormRef">
                <el-form-item
                    label="姓名"
                    label-width="120"
                    prop="cusername">
                    <el-input v-model="modify.cusername" />
                </el-form-item>
                <el-form-item
                    label="手机号"
                    label-width="120"
                    prop="customerphone">
                    <el-input v-model="modify.customerphone" />
                </el-form-item>
                <el-form-item
                    label="地址"
                    label-width="120"
                    prop="customerposition">
                    <el-input v-model="modify.customerposition" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="modifyClose">取消</el-button>
                    <el-button
                        type="primary"
                        @click="modifySubmitForm">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 删除 -->
        <el-dialog
            v-model="deleteShow"
            title="Tips"
            width="30%">
            <span>是否确定删除</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="deleteShow = false">取消</el-button>
                    <el-button
                        type="primary"
                        @click="deleteConfirm">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script setup>
import { onMounted } from 'vue'
import { getOrder, updateorder, deleteorder } from '../../api'
import { ref, reactive } from 'vue'
import { Refresh } from '@element-plus/icons-vue'
const modifyShow = ref(false)
const list = ref([])
const id = ref('')
const deleteShow = ref(false)
const modify = ref({
    cusername: '',
    customerposition: '',
    customerphone: '',
    customername: ''
})
const modifyFormRef = ref('')

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 分页后的数据
const paginatedList = computed(() => {
    total.value = list.value.length
    const startIndex = (currentPage.value - 1) * pageSize.value
    const endIndex = startIndex + pageSize.value
    return list.value.slice(startIndex, endIndex)
})

// 分页处理方法
const handleSizeChange = (size) => {
    pageSize.value = size
    currentPage.value = 1 // 重置到第一页
}

const handleCurrentChange = (current) => {
    currentPage.value = current
}

const getList = async () => {
    let res = await getOrder()
    console.log('后端返回的原始订单数据:', res)

    // 处理返回的数据，为null字段设置默认值
    list.value = res.map((order) => ({
        ...order,
        // 如果created_at为null，设置为当前时间或其他默认值
        created_at: order.created_at || new Date().toISOString(),
        // 如果business_id为null，尝试从其他字段推断或设置为默认值
        business_id: order.business_id || 0
    }))

    // 重置到第一页
    currentPage.value = 1
}
const modifyClose = () => {
    modifyShow.value = false
    // (modify.value = {
    //     cusername: '',
    //     customerposition: '',
    //     customerphone: '',
    //     customername: ''
    // })
    getList()
}
const rules = reactive({
    cusername: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
    customerposition: [{ required: true, message: '请输入位置', trigger: 'blur' }],
    customerphone: [{ required: true, message: '请输入手机号', trigger: 'blur' }]
})
const openModify = (e) => {
    modify.value = e
    modifyShow.value = true
}
const modifySubmitForm = async () => {
    modifyFormRef.value.validate(async (valid, fields) => {
        if (valid) {
            let res = await updateorder(modify.value)
            console.log(res)
            modifyClose()
        } else {
            console.log('error submit!', fields)
        }
    })
}
const openDelete = async (e) => {
    console.log(e)

    deleteShow.value = true
    id.value = e
}
const deleteConfirm = async () => {
    try {
        console.log('准备删除订单，ID:', id.value)
        if (!id.value) {
            console.error('删除ID为空，无法执行删除操作')
            deleteShow.value = false
            return
        }

        let res = await deleteorder(id.value)
        console.log('删除订单响应:', res)
        deleteShow.value = false

        // 刷新列表
        getList()
    } catch (error) {
        console.error('删除订单出错:', error)
        deleteShow.value = false
    }
}
import { computed } from 'vue'
onMounted(() => {
    getList()
})
</script>

<style scoped lang="less">
.content {
    display: flex;
    flex-direction: column;
    align-items: center;
    .table {
        width: 100%;
    }
}
.pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
}
</style>
