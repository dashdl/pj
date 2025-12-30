<template>
    <div>
        <NavBar title="编辑个人资料"></NavBar>
        <van-form @submit="onSubmit" style="padding-top: 10px">
            <van-field v-model="username" name="用户名" label="用户名" readonly />
            <van-field v-model="password" type="password" name="密码" label="密码" placeholder="输入密码" :rules="[{ required: true, message: '请填写密码' }]" />
            <van-field v-model="confirmpassword" type="password" name="确认密码" label="确认密码" placeholder="再一次输入密码" :rules="[{ required: true, message: '请填写密码' }]" />
            <van-field name="uploader" label="头像上传">
                <template #input>
                    <van-uploader v-model="file" max-count="1" />
                </template>
            </van-field>
            <div style="margin: 16px">
                <van-button round block type="primary" native-type="submit">提交</van-button>
            </div>
        </van-form>
    </div>
</template>

<script setup>
import { ref } from "vue"
import { getUpload, getUpdateInfo } from "@/api/index"
import NavBar from "../../components/NavBar/index.vue"
import { useRouter } from "vue-router"
import { showNotify } from "vant"
const router = useRouter()
const username = localStorage.getItem("username")
const password = ref("")
const confirmpassword = ref("")
const file = ref([])
const onSubmit = async () => {
    if (password.value !== confirmpassword.value) {
        showNotify({ type: "danger", message: "两次密码不一致" })
        return
    }
    const url = await upload(file.value)
    const data = {
        username: username,
        password: password.value,
        avatarUrl: url,
    }
    const res = await getUpdateInfo(data)
    showNotify({ type: "success", message: res.msg })
    localStorage.clear()
    router.push("/login")
}
const upload = async (file) => {
    const formData = new FormData()
    formData.append("image", file[0].file)
    const res = await getUpload(formData)
    return res.imagePath
}
</script>

<style scoped lang="less"></style>
