<template>
    <div class="content">
        <!-- 登录 -->
        <div
            class="login"
            v-if="loginShow">
            <h1>登录</h1>
            <div class="input">
                <van-form @submit="onSubmitLogin">
                    <van-field
                        class="inputItem"
                        v-model="loginUsername"
                        name="用户名"
                        placeholder="请输入用户名"
                        :rules="[{ required: true, message: '请填写用户名' }]" />
                    <van-field
                        class="inputItem"
                        v-model="loginPassword"
                        type="password"
                        name="密码"
                        placeholder="请输入密码"
                        :rules="[{ required: true, message: '请填写密码' }]" />
                    <div style="text-align: right">
                        <van-button
                            round
                            block
                            type="primary"
                            native-type="onSubmitLogin"
                            class="LoginLink">
                            立即登录
                        </van-button>
                        <div
                            @click="loginShow = false"
                            class="RegisterLink">
                            新用户注册
                        </div>
                    </div>
                </van-form>
            </div>
            <van-radio-group v-model="checked">
                <van-radio
                    name="1"
                    icon-size="16px"
                    class="footer">
                    同意《用户协议》和《隐私政策》
                </van-radio>
            </van-radio-group>
        </div>
        <!-- 注册 -->
        <div
            class="login"
            v-else>
            <h1>注册</h1>
            <div class="input">
                <van-form @submit="onSubmitRegister">
                    <van-field
                        class="inputItem"
                        v-model="registerUsername"
                        name="用户名"
                        placeholder="请输入用户名"
                        :rules="[{ required: true, message: '请填写用户名' }]" />
                    <van-field
                        class="inputItem"
                        v-model="registerPassword"
                        type="password"
                        name="密码"
                        placeholder="请输入密码"
                        :rules="[{ required: true, message: '请填写密码' }]" />
                    <van-field
                        class="inputItem"
                        v-model="confirmPassword"
                        type="password"
                        name="确认密码"
                        placeholder="请再次输入密码"
                        :rules="[{ required: true, message: '请再次填写密码' }]" />
                    <div>
                        <van-button
                            round
                            block
                            type="primary"
                            native-type="onSubmitRegister"
                            class="LoginLink float"
                            style="">
                            注册
                        </van-button>
                        <van-button
                            round
                            block
                            type="primary"
                            @click="loginShow = true"
                            class="LoginLink float">
                            返回
                        </van-button>
                    </div>
                </van-form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { showToast } from 'vant'
import { getRegister, getLogin } from '@/api/index'
import { useRouter } from 'vue-router'
const router = useRouter()
const loginShow = ref(true)
const loginUsername = ref('')
const loginPassword = ref('')
const registerUsername = ref('')
const registerPassword = ref('')
const confirmPassword = ref('')

const checked = ref()

// 用户登录
const onSubmitLogin = async () => {
    const data = {
        username: loginUsername.value,
        password: loginPassword.value
    }
    try {
        const res = await getLogin(data)
        if (res.msg === '登录失败') {
            showToast('登录失败')
            return
        }
        loginPassword.value = ''
        loginUsername.value = ''
        localStorage.setItem('token', res.token)
        localStorage.setItem('username', res.username)
        localStorage.setItem('avatarUrl', res.avatarUrl)
        router.push('/home')
        showToast('登录成功')
    } catch {
        showToast('登录失败')
    }
}
// 用户注册
const onSubmitRegister = async () => {
    if (confirmPassword.value !== registerPassword.value) {
        showToast('2次密码不一致')
        return
    }
    const data = {
        username: registerUsername.value,
        password: registerPassword.value
    }
    try {
        let res = await getRegister(data)
        if (res.msg === '用户名已存在') {
            showToast('用户名已存在')
            return
        }
        registerUsername.value = ''
        registerPassword.value = ''
        confirmPassword.value = ''
        showToast('注册成功')
        loginShow.value = true
    } catch {
        showToast('注册失败')
    }
}
</script>

<style scoped lang="less">
.content {
    display: flex;
    justify-content: center;
    padding-top: 101px;
    height: 88vh;
    background-image: linear-gradient(360deg, #ffebd0, #fcd79f 77%);
}
.inputItem {
    margin: 20px 0;
    border-radius: 10px;
}
.login {
    width: 85%;
}
.LoginLink {
    margin: 10px 0;
    border-radius: 10px;
    font-size: 16px;
    background-image: linear-gradient(269deg, #ff9000, #ff783b 77%);
    border: none;
}
.RegisterLink {
    padding: 0px 5px;
    font-size: 14px;
}

.input {
    margin-bottom: 380px;
}

.float {
    width: 155px;
    float: left;
    margin: 0 5px;
    border-radius: 25px;
}
.footer {
    justify-content: center;
    font-size: 13px;
}
</style>
