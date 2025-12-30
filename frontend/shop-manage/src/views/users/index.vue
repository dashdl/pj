<template>
    <div class="content">
        <el-card style="width: 1400px">
            <div class="user-header">
                <h1>用户管理</h1>
                <div class="search-bar">
                    <el-input
                        v-model="searchKeyword"
                        placeholder="请输入用户账号"
                        clearable
                        :prefix-icon="Search"
                        style="width: 300px"
                        @keyup.enter="handleSearch" />
                    <el-button
                        type="primary"
                        @click="handleSearch"
                        style="margin-left: 10px"
                        :icon="Search">
                        搜索
                    </el-button>
                    <el-button
                        @click="resetSearch"
                        style="margin-left: 10px">
                        重置
                    </el-button>
                </div>
            </div>
            <div class="table">
                <el-table
                    :data="paginatedList"
                    style="width: 100%"
                    stripe
                    border>
                    <el-table-column
                        label="ID"
                        prop="id"
                        width="60" />
                    <el-table-column
                        label="用户账号"
                        prop="username"
                        width="200" />
                    <el-table-column
                        label="用户密码"
                        prop="password"
                        width="" />
                    <el-table-column
                        label="注册时间"
                        prop="createdAt"
                        width="250"
                        :formatter="formatDate" />
                    <el-table-column
                        fixed="right"
                        label="操作"
                        width="160">
                        <template #default="scope">
                            <el-button
                                type="danger"
                                @click="openstop(scope.row.id)"
                                v-if="scope.row.status == 1">
                                封停用户
                            </el-button>
                            <el-button
                                type="warning"
                                @click="openpass(scope.row.id)"
                                v-if="scope.row.status == 0">
                                解除封停
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

        <!-- 封停 -->
        <el-dialog
            v-model="stopshow"
            title="Tips"
            width="30%">
            <span>是否确定封停该用户？</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="stopshow = false">取消</el-button>
                    <el-button
                        type="primary"
                        @click="stopconfirm">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 解除封停 -->
        <el-dialog
            v-model="passshow"
            title="Tips"
            width="30%">
            <span>是否确定解除封停该用户？</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="passshow = false">取消</el-button>
                    <el-button
                        type="primary"
                        @click="passconfirm">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script setup>
import { onMounted, computed } from 'vue'
import { getUser, stopUser, passUser, searchUser } from '../../api'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'

// 格式化日期
const formatDate = (row, column, cellValue) => {
    // 当日期值为空或null时，显示友好提示
    if (!cellValue || cellValue === null || cellValue === 'null') {
        return '暂无注册时间'
    }

    // 处理Java LocalDateTime格式的日期字符串 (例如: 2023-11-09T15:30:45)
    try {
        let date

        // 检查是否为字符串类型
        if (typeof cellValue === 'string') {
            // 处理LocalDateTime格式，将'T'替换为空格
            if (cellValue.includes('T')) {
                const modifiedDateStr = cellValue.replace('T', ' ')
                date = new Date(modifiedDateStr)
            } else {
                date = new Date(cellValue)
            }
        } else if (cellValue instanceof Date) {
            date = cellValue
        }

        if (date && !isNaN(date.getTime())) {
            // 格式化日期为 yyyy-MM-dd HH:mm:ss 格式
            const year = date.getFullYear()
            const month = String(date.getMonth() + 1).padStart(2, '0')
            const day = String(date.getDate()).padStart(2, '0')
            const hours = String(date.getHours()).padStart(2, '0')
            const minutes = String(date.getMinutes()).padStart(2, '0')
            const seconds = String(date.getSeconds()).padStart(2, '0')

            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
        } else {
            // 如果无法解析，显示友好提示
            return '日期格式不正确'
        }
    } catch (e) {
        console.error('日期格式化错误:', e)
        return '日期格式不正确'
    }
}

const list = ref([])
const id = ref('')
const stopshow = ref(false)
const passshow = ref(false)
const searchKeyword = ref('')

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
    try {
        let res = await getUser()
        console.log('用户列表数据:', res)
        list.value = res
        // 重置到第一页
        currentPage.value = 1
    } catch (error) {
        console.error('获取用户列表失败:', error)
    }
}

// 搜索用户
const handleSearch = async () => {
    try {
        if (!searchKeyword.value.trim()) {
            // 如果搜索关键词为空，则显示所有用户
            await getList()
            return
        }

        const res = await searchUser(searchKeyword.value.trim())
        console.log('搜索结果:', res)
        list.value = res
        // 重置到第一页
        currentPage.value = 1
    } catch (error) {
        console.error('搜索用户失败:', error)
    }
}

// 重置搜索
const resetSearch = async () => {
    searchKeyword.value = ''
    await getList()
}

const openstop = async (e) => {
    stopshow.value = true
    id.value = e
}
const openpass = async (e) => {
    passshow.value = true
    id.value = e
}
const stopconfirm = async () => {
    let res = await stopUser(id.value)
    console.log(res)
    stopshow.value = false
    getList()
}
const passconfirm = async () => {
    let res = await passUser(id.value)
    console.log(res)
    passshow.value = false
    getList()
}
onMounted(() => {
    getList()
})
</script>

<style scoped lang="less">
.content {
    display: flex;
    flex-direction: column;
    align-items: center;
    .user-header{
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    h1 {
        font-size: 20px;
        padding: 20px 0;
    }
    .search-bar {
        // margin-bottom: 20px;
        display: flex;
        align-items: center;
    }
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
