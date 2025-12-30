<template>
    <div class="login-wrap">
        <div class="login-content">
            <div class="login-title">系统登录</div>
            <el-form
                ref="loginFormRef"
                class="login-form"
                :model="param"
                :rules="rules"
                status-icon>
                <el-form-item prop="username">
                    <el-input
                        v-model="param.username"
                        placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        v-model="param.password"
                        placeholder="密码"
                        :type="passwordType"
                        @keyup.enter="submitForm"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button
                        type="primary"
                        :loading="btnLoading"
                        @click="submitForm">
                        登录
                    </el-button>
                </div>
                <p class="login-tips">用户名: admin&nbsp;&nbsp;&nbsp;默认密码: 123456</p>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getLogin } from '../../api/index'
const router = useRouter()
const btnLoading = ref(false)
const loginFormRef = ref(null)
// const passwordLock = ref(true)
const passwordType = ref('password')
const param = reactive({
    username: '',
    password: ''
})

const rules = reactive({
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})
const submitForm = async () => {
    if (param.username !== 'admin') {
        ElMessage.error('请使用管理员账号登录')
        return
    }
    loginFormRef.value.validate(async (valid) => {
        if (valid) {
            btnLoading.value = true
            try {
                const res = await getLogin(param)

                if (res.msg == '登录失败') {
                    ElMessage.error('登录失败')
                    param.username = ''
                    param.password = ''
                    return
                }
                localStorage.setItem('token', res.token)
                localStorage.setItem('username', res.username)
                localStorage.setItem('avatarUrl', res.avatarUrl)
                router.push('/home')
            } catch (error) {
                console.error(error)
            } finally {
                btnLoading.value = false
            }
        } else {
            ElMessage.error('请输入用户名和密码')
        }
    })
}
</script>

<style scoped lang="less">
.login-wrap {
    position: relative;
    width: 100vw;
    height: 100vh;
    background-color: #235bae;
    background-image: url('../../../public/login_bg.jpg');
    background-size: cover;
}

.login-title {
    width: 100%;
    font-size: 20px;
    line-height: 50px;
    color: #fff;
    text-align: center;
    border-bottom: 1px solid #ddd;
}

.login-content {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    overflow: hidden;
    background: rgba(255, 255, 255, 0.4);
    border-radius: 5px;
}

.login-form {
    padding: 30px;
}

.login-btn {
    text-align: center;
}

.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}

.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #be1480;
}

.el-icon-lock,
.el-icon-unlock {
    cursor: pointer;
}
</style>
