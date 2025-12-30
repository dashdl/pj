<template>
    <el-card style="width: 1400px">
        <h1 class="card-header">新增商品</h1>
        <el-form
            :model="add"
            :inline="true"
            :rules="rules"
            ref="addformRef">
            <div style="display: flex;">
                <div>
                    <el-form-item
                        label="用户名"
                        label-width="120"
                        prop="username">
                        <el-input
                            v-model="add.username"
                            class="inputWidth" />
                    </el-form-item>
                    <el-form-item
                        label="姓名"
                        label-width="120"
                        prop="name">
                        <el-input
                            v-model="add.name"
                            class="inputWidth" />
                    </el-form-item>
                    <el-form-item
                        label="手机号"
                        label-width="120"
                        prop="phone">
                        <el-input
                            v-model="add.phone"
                            class="inputWidth" />
                    </el-form-item>
                </div>
                <div>
                    <el-form-item
                        label="标题"
                        label-width="120"
                        prop="title">
                        <el-input
                            v-model="add.title"
                            class="inputWidth" />
                    </el-form-item>
                    <el-form-item
                        label="描述"
                        label-width="120"
                        prop="description">
                        <el-input
                            v-model="add.description"
                            class="inputWidth"
                            type="textarea"
                            :rows="5" />
                    </el-form-item>
                    <el-form-item
                        label="内容"
                        label-width="120"
                        prop="content">
                        <el-input
                            v-model="add.content"
                            class="inputWidth" />
                    </el-form-item>
                    <el-form-item
                        label="位置"
                        label-width="120"
                        prop="position">
                        <el-input
                            v-model="add.position"
                            class="inputWidth" />
                    </el-form-item>
                    <el-form-item
                        label="价格"
                        label-width="120"
                        prop="price">
                        <el-input
                            v-model="add.price"
                            class="inputWidth" />
                    </el-form-item>
                    <el-form-item
                        label="类型"
                        label-width="120"
                        prop="type"
                        class="inputWidth">
                        <el-select
                            v-model="add.type"
                            class="m-2"
                            placeholder="选择类型"
                            size="large">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value" />
                        </el-select>
                    </el-form-item>
                    <el-form-item
                        label="图片"
                        label-width="120"
                        prop="photo"
                        class="inputWidth">
                        <el-upload
                            class="avatar-uploader"
                            action="/api/upload"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            name="image">
                            <img
                                v-if="imageUrl"
                                :src="imageUrl"
                                class="avatar" />
                            <el-icon
                                v-else
                                class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                    </el-form-item>
                </div>
            </div>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addclose">取消</el-button>
                <el-button
                    type="primary"
                    @click="addsubmitForm">
                    确定
                </el-button>
            </span>
        </template>
    </el-card>
</template>

<script setup>
import { getShop, addList } from '../../api'
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()

const addformRef = ref(null)
const list = ref([])
const addshow = ref(false)

import { Plus } from '@element-plus/icons-vue'
const imageUrl = ref('')
const options = [
    {
        value: '1',
        label: '图书教材'
    },
    {
        value: '2',
        label: '衣物服饰'
    },
    {
        value: '3',
        label: '数码产品'
    },
    {
        value: '4',
        label: '鞋类采购'
    },
    {
        value: '5',
        label: '潮玩玩具'
    },
    {
        value: '6',
        label: '宿舍好物'
    },
    {
        value: '7',
        label: '美容彩妆'
    },
    {
        value: '8',
        label: '其他'
    }
]

const rules = reactive({
    name: [{ required: true, message: '请输姓名', trigger: 'blur' }],
    username: [{ required: true, message: '请输用户名', trigger: 'blur' }],
    phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
    title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
    description: [{ required: true, message: '请输入描述', trigger: 'blur' }],
    content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
    position: [{ required: true, message: '请输入位置', trigger: 'blur' }],
    price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
    type: [{ required: true, message: '请选择类型', trigger: 'blur' }],
    photo: [{ required: true, message: '请上传图片', trigger: 'blur' }]
})
const add = ref({
    username: '',
    name: '',
    phone: '',
    title: '',
    description: '',
    content: [],
    position: '',
    type: '',
    photo: '',
    price: ''
})
const modify = ref({
    username: '',
    name: '',
    phone: '',
    title: '',
    description: '',
    content: [],
    position: '',
    type: '',
    photo: '',
    price: ''
})

const getList = async () => {
    let res = await getShop()
    list.value = res
}

const addclose = () => {
    imageUrl.value = ''
    ;(add.value = {
        username: '',
        name: '',
        phone: '',
        title: '',
        description: '',
        content: [],
        position: '',
        type: '',
        photo: '',
        price: ''
    }),
        (addshow.value = false),
        getList()
}
// 图片上传
const handleAvatarSuccess = (response, uploadFile) => {
    add.value.photo = response.imagePath
    modify.value.photo = response.imagePath
    imageUrl.value = URL.createObjectURL(uploadFile.raw)
}
const addsubmitForm = async () => {
    addformRef.value.validate(async (valid, fields) => {
        if (valid) {
            let res = await addList(add.value)
            addclose()
            router.push('./business')
        } else {
            console.log('error submit!', fields)
        }
    })
}
</script>

<style scoped lang="less">
.card-header {
    padding: 10px 0px 20px 20px;
    font-size: 20px;
    border-bottom: 1px solid #ccc;
    margin-bottom: 35px;
}
.inputWidth {
    width: 500px;
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
.dialog-footer {
    display: flex;
    justify-content: flex-end;
}
</style>
