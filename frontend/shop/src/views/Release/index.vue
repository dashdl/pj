<template>
    <div class="list">
        <NavBar :title="title"></NavBar>
        <div class="input">
            <van-form @submit="onSubmit">
                <van-field
                    v-model="name"
                    label="名字"
                    placeholder="请输入名字"
                    :rules="[{ required: true, message: '请填写名字' }]" />
                <van-field
                    v-model="phone"
                    type="number"
                    name="手机号"
                    label="手机号"
                    placeholder="请输入手机号"
                    :rules="[{ required: true, message: '请填写密码' }]" />
                <van-field
                    v-model="titleinfo"
                    label="标题"
                    placeholder="请输入标题"
                    :rules="[{ required: true, message: '请填写标题' }]" />
                <van-field
                    v-model="description"
                    label="描述"
                    placeholder="请输入描述"
                    :rules="[{ required: true, message: '请填写描述' }]" />
                <van-field
                    v-model="content"
                    label="内容"
                    placeholder="请输入内容"
                    :rules="[{ required: true, message: '请填写内容' }]" />
                <van-field
                    v-model="position"
                    label="位置"
                    placeholder="请输入位置"
                    :rules="[{ required: true, message: '请填写位置' }]" />
                <van-field
                    v-model="price"
                    type="number"
                    label="价格"
                    placeholder="请输入价格"
                    :rules="[{ required: true, message: '请填写价格' }]" />
                <van-field
                    label="文件上传"
                    :rules="[{ required: true, message: '请上传图片' }]">                    
                    <template #input>
                        <van-uploader
                            v-model="file"
                            max-count="1"
                            accept="image/*"
                            :before-read="beforeRead" />                    
                    </template>
                </van-field>
                <div style="margin: 16px">
                    <van-button
                        round
                        block
                        type="primary"
                        native-type="onSubmit">
                        提交
                    </van-button>
                </div>
            </van-form>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import NavBar from '../../components/NavBar/index.vue'
import { getUpload, addList } from '@/api/index'
import { useRoute, useRouter } from 'vue-router'
import { showSuccessToast, showFailToast } from 'vant'
const route = useRoute()
const router = useRouter()
const titleinfo = ref('')
const title = ref('')
const name = ref('')
const phone = ref('')
const description = ref('')
const content = ref('')
const position = ref('')
const price = ref('')
const file = ref([])
const menuId = ref(0)
const maxSize = 2 * 1024 * 1024 // 2MB的前端限制

// 文件读取前的校验 - 简单的文件大小和类型检查
const beforeRead = (file) => {
    if (file.size > maxSize) {
        showFailToast(`文件大小不能超过${maxSize/1024/1024}MB，请选择更小的图片`)
        return false
    }
    
    const acceptedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp']
    if (!acceptedTypes.includes(file.type)) {
        showFailToast('只支持jpg、jpeg、png、gif、webp格式的图片')
        return false
    }
    
    return true
}

// 简化的上传函数
const upload = async (fileList) => {
    try {
        const formData = new FormData()
        formData.append('image', fileList[0].file)
        const res = await getUpload(formData)
        return res.imagePath
    } catch (error) {
        showFailToast('上传失败，请重试')
        console.error('上传错误:', error)
        throw error
    }
}

const onSubmit = async () => {
    let url = await upload(file.value)
    console.log(url + 'url')

    const data = {
        name: name.value,
        phone: phone.value,
        title: titleinfo.value,
        description: description.value,
        content: content.value,
        position: position.value,
        type: menuId.value,
        photo: url,
        price: price.value,
        username: localStorage.getItem('username')
    }
    let res = await addList(data)
    console.log(res)

    showSuccessToast('发布成功！请等待管理员通过...')
    router.go(-1)
}
const setTitle = () => {
    menuId.value = parseInt(route.params.id)
    switch (menuId.value) {
        case 1:
            title.value = '电子工程学院'
            break
        case 2:
            title.value = '管理学院'
            break
        case 3:
            title.value = '机械工程学院'
            break
        case 4:
            title.value = '计算机学院'
            break
        case 5:
            title.value = '经济学院'
            break
        case 6:
            title.value = '马克思学院'
            break
        case 7:
            title.value = '课外读物'
            break
        case 8:
            title.value = '其他'
            break
    }
}

setTitle()
</script>

<style scoped lang="less">
.input {
    margin: 50px 20px;
}
</style>
