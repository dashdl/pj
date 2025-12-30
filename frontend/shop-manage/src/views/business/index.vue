<template>
    <div class="content">
        <el-card style="width: 1400px">
            <div class="card-header">
                <h1 style="font-size: 20px">商品管理</h1>
                <div>
                    <div class="card-header-item">
                        <!-- <span>类型选择：</span> -->
                        <el-select v-model="type" class="m-2" placeholder="类型选择">
                            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="card-header-item">
                        <!-- <span>状态选择：</span> -->
                        <el-select v-model="status" placeholder="状态选择">
                            <el-option v-for="item in statuss" :key="item.value" :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="card-header-item">
                        <el-button type="primary" @click="onReload">显示全部</el-button>
                    </div>
                </div>
            </div>
            <div class="table">
                <el-table :data="paginatedList" style="width: 100%" stripe border>
                    <el-table-column fixed prop="username" label="用户名" width="80" />
                    <el-table-column prop="name" label="姓名" width="100" />
                    <el-table-column prop="title" label="标题" width="200" />
                    <el-table-column prop="description" label="描述" />
                    <el-table-column prop="price" label="价格" width="80" />
                    <el-table-column label="类型" width="120">
                        <template v-slot="{ row }">
                            {{ typeconvert(row.type) }}
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="230">
                        <template #default="scope">
                            <el-button type="warning" @click="pass(scope.row.id)" v-if="scope.row.status == 0">通过</el-button>
                            <el-button type="warning" v-if="scope.row.status == 1" @click="openoff(scope.row.id)">下架</el-button>
                            <el-button type="info" disabled v-if="scope.row.status == 2" style="width: 203px">已售出</el-button>
                            <el-button type="primary" v-if="(scope.row.status == 1) | (scope.row.status == 0)" @click="openmodifyshop(scope.row)">编辑</el-button>
                            <el-button type="danger" @click="opendeleteshop(scope.row.id)" v-if="scope.row.status != 2">删除</el-button>
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
        <!-- 通过 -->
        <el-dialog v-model="passshow" title="Tips" width="30%">
            <span>是否确定通过</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="passshow = false">取消</el-button>
                    <el-button type="primary" @click="passconfirm"> 确定 </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 下架 -->
        <el-dialog v-model="offshow" title="Tips" width="30%">
            <span>是否确定下架</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="offshow = false">取消</el-button>
                    <el-button type="primary" @click="offconfirm"> 确定 </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 删除 -->
        <el-dialog v-model="deleteshow" title="Tips" width="30%">
            <span>是否确定删除</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="deleteshow = false">取消</el-button>
                    <el-button type="primary" @click="deleteconfirm"> 确定 </el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 编辑 -->
        <el-dialog v-model="modifyshow" title="编辑商品">
            <el-form :model="modify" :inline="true" :rules="rules" ref="modifyformRef">
                <el-form-item label="用户名" label-width="120" prop="username">
                    <el-input v-model="modify.username" />
                </el-form-item>
                <el-form-item label="姓名" label-width="120" prop="name">
                    <el-input v-model="modify.name" />
                </el-form-item>
                <el-form-item label="手机号" label-width="120" prop="phone">
                    <el-input v-model="modify.phone" />
                </el-form-item>
                <el-form-item label="标题" label-width="120" prop="title">
                    <el-input v-model="modify.title" />
                </el-form-item>
                <el-form-item label="描述" label-width="120" prop="description">
                    <el-input v-model="modify.description" />
                </el-form-item>
                <el-form-item label="内容" label-width="120" prop="content">
                    <el-input v-model="modify.content" />
                </el-form-item>
                <el-form-item label="位置" label-width="120" prop="position">
                    <el-input v-model="modify.position" />
                </el-form-item>
                <el-form-item label="价格" label-width="120" prop="price">
                    <el-input v-model="modify.price" />
                </el-form-item>
                <el-form-item label="类型" label-width="120" prop="type" style="width: 1000px">
                    <el-select v-model="modify.type" class="m-2" placeholder="选择类型" size="large">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="图片" label-width="120" prop="photo">
                    <el-upload class="avatar-uploader" action="/api/upload" :show-file-list="false" :on-success="handleAvatarSuccess" name="image">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="modifyclose">取消</el-button>
                    <el-button type="primary" @click="modifysubmitForm"> 确定 </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from "vue"
import { getShop, passList, offList, updateshop, deletelist } from "../../api"
import { ElMessage } from "element-plus"
const modifyformRef = ref(null)
const list = ref([])
const id = ref("")
const deleteshow = ref(false)
const passshow = ref(false)
const offshow = ref(false)
const modifyshow = ref(false)
const type = ref("")
const status = ref("")

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 分页后的数据
const paginatedList = computed(() => {
    total.value = newlist.value.length
    const startIndex = (currentPage.value - 1) * pageSize.value
    const endIndex = startIndex + pageSize.value
    return newlist.value.slice(startIndex, endIndex)
})

// 分页处理方法
const handleSizeChange = (size) => {
    pageSize.value = size
    currentPage.value = 1 // 重置到第一页
}

const handleCurrentChange = (current) => {
    currentPage.value = current
}

import { Plus } from "@element-plus/icons-vue"
const imageUrl = ref("")
const options = [
    {
        value: "1",
        label: "电子工程学院",
    },
    {
        value: "2",
        label: "管理学院",
    },
    {
        value: "3",
        label: "机械工程学院",
    },
    {
        value: "4",
        label: "计算机学院",
    },
    {
        value: "5",
        label: "经济学院",
    },
    {
        value: "6",
        label: "马克思学院",
    },
    {
        value: "7",
        label: "课外读物",
    },
    {
        value: "8",
        label: "其他",
    },
]
const statuss = [
    {
        value: "0",
        label: "未通过",
    },
    {
        value: "1",
        label: "已通过",
    },
    {
        value: "2",
        label: "已售出",
    },
]
const rules = reactive({
    name: [{ required: true, message: "请输姓名", trigger: "blur" }],
    username: [{ required: true, message: "请输用户名", trigger: "blur" }],
    phone: [{ required: true, message: "请输入手机号", trigger: "blur" }],
    title: [{ required: true, message: "请输入标题", trigger: "blur" }],
    description: [{ required: true, message: "请输入描述", trigger: "blur" }],
    content: [{ required: true, message: "请输入内容", trigger: "blur" }],
    position: [{ required: true, message: "请输入位置", trigger: "blur" }],
    price: [{ required: true, message: "请输入价格", trigger: "blur" }],
    type: [{ required: true, message: "请选择类型", trigger: "blur" }],
    photo: [{ required: true, message: "请上传图片", trigger: "blur" }],
})
const modify = ref({
    username: "",
    name: "",
    phone: "",
    title: "",
    description: "",
    content: [],
    position: "",
    type: "",
    photo: "",
    price: "",
})
const typeconvert = (id) => {
    switch (id.toString()) {
        case "1":
            return "电子工程学院"
        case "2":
            return "管理学院"
        case "3":
            return "机械工程学院"
        case "4":
            return "计算机学院"
        case "5":
            return "经济学院"
        case "6":
            return "马克思学院"
        case "7":
            return "课外读物"
        case "8":
            return "其他"
    }
}

const getList = async () => {
    try {
        let res = await getShop()
        list.value = res
        // 重置到第一页
        currentPage.value = 1
    } catch (error) {
        console.error('获取商品列表失败:', error)
    }
}
const pass = async (e) => {
    id.value = e
    passshow.value = true
}
const passconfirm = async () => {
    let res = await passList(id.value)
    passshow.value = false
    id.value = ""
    getList()
}

// 图片上传
const handleAvatarSuccess = (response, uploadFile) => {
    // add.value.photo = response.imagePath
    modify.value.photo = response.imagePath
    imageUrl.value = URL.createObjectURL(uploadFile.raw)
}

const opendeleteshop = async (e) => {
    deleteshow.value = true
    id.value = e
}
const deleteconfirm = async () => {
    let res = await deletelist(id.value)
    deleteshow.value = false
    getList()
}
const openoff = async (e) => {
    offshow.value = true
    id.value = e
}
const offconfirm = async () => {
    let res = await offList(id.value)
    offshow.value = false
    getList()
}
const newlist = computed(() => {
    if (type.value == "" && status.value == "") {
        return list.value
    } else if (type.value != "" && status.value == "") {
        return list.value.filter((item) => {
            return item.type == type.value
        })
    } else if (status.value != "" && type.value == "") {
        return list.value.filter((item) => {
            return item.status == status.value
        })
    } else {
        return list.value.filter((item) => {
            return item.status == status.value && item.type == type.value
        })
    }
})
const modifyclose = () => {
    imageUrl.value = ""
    ;(modify.value = {
        username: "",
        name: "",
        phone: "",
        title: "",
        description: "",
        content: [],
        position: "",
        type: "",
        photo: "",
        price: "",
    }),
        (modifyshow.value = false),
        getList()
    id.value = ""
}
const openmodifyshop = async (e) => {
    modifyshow.value = true
    // 创建副本并确保类型字段为字符串
    const shopData = { ...e }
    shopData.type = shopData.type.toString() // 转换为字符串类型
    modify.value = shopData
    imageUrl.value = `http://localhost:8080${e.photo}`
    id.value = e.id
}
const modifysubmitForm = async () => {
    modifyformRef.value.validate(async (valid, fields) => {
        if (valid) {
            let res = await updateshop(id.value, modify.value)
            modifyclose()
        } else {
            console.log("error submit!", fields)
        }
    })
}
const onReload = () => {
    location.reload()
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
    float: left;
    margin-left: 20px;
    display: flex;
    align-items: center;
}
.text {
    font-size: 14px;
}

.avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
}

.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    text-align: center;
    background-color: #d9d9db;
}
</style>
