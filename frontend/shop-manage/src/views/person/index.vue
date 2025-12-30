<template>
    <el-card class="box-card">
        <template #header><h1 style="font-size: 20px">个人信息</h1></template>
        <div class="change-password-box">
            <el-form
                ref="infoformRef"
                status-icon
                label-width="140px"
                :model="info"
                :rules="rules">
                <el-form-item
                    prop="password"
                    label="修改密码">
                    <el-input v-model="info.password" />
                </el-form-item>
                <el-form-item
                    prop="repassword"
                    label="请再次输入密码">
                    <el-input v-model="info.repassword" />
                </el-form-item>
                <el-form-item
                    label="选择头像"
                    label-width="140"
                    prop="avatarUrl">
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
                <el-form-item>
                    <el-button @click="infosubmitForm">提交</el-button>
                    <el-button @click="resetForm">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { updateinfo } from '../../api/index'
const router = useRouter()
const infoformRef = ref('')
const imageUrl = ref('')
const info = ref({
    password: '',
    username: '',
    avatarUrl: '',
    repassword: ''
})
const rules = {
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    repassword: [{ required: true, message: '请再次输入密码', trigger: 'blur' }],
    avatarUrl: [{ required: true, message: '请上传头像', trigger: 'blur' }]
}
const handleAvatarSuccess = (response, uploadFile) => {
    info.value.avatarUrl = response.imagePath
    imageUrl.value = URL.createObjectURL(uploadFile.raw)
}

const infosubmitForm = () => {
    info.value.username = localStorage.getItem('username')
    infoformRef.value.validate(async (valid, fields) => {
        if (valid) {
            if (info.value.password == info.value.repassword) {
                await updateinfo(info.value)
                localStorage.setItem('avatarUrl', info.value.avatarUrl)
                router.push('/login')
                ElMessage.success('请重新登录')
            } else {
                ElMessage.error('2次密码不一致')
            }
        } else {
            console.log('error submit!', fields)
        }
    })
}
const resetForm = () => {
    infoformRef.value.resetFields()
    imageUrl.value = ''
}
</script>
<style lang="less" scoped>
.avatar {
    text-align: center;
}
.upload-demo {
    text-align: center;
}
.box-card {
    font-weight: bold;
}
.change-password-box {
    padding-top: 38px;
}
.card-header {
    padding: 0;
    background-color: rgba(0, 0, 0, 0);
    border-bottom: none;
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
