<template>
    <div class="list">
        <NavBar title="列表" />
        <van-card v-for="(item, index) in list" :key="index" :desc="item.description" :title="item.title" :thumb="`http://localhost:8080${item.photo}`" :thumb-link="`/detail/${item.id}`">
            <template #tags>
                <div>地址：{{ item.position }}</div>
                <div>联系方式：{{ item.phone }}</div>
            </template>
        </van-card>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import { getType } from "../../api/index"
import { useRoute, useRouter } from "vue-router"
import NavBar from "../../components/NavBar/index.vue"

const menuId = ref("")
const list = ref([])
const route = useRoute()

onMounted(() => {
    menuId.value = route.params.id
    getList()
})

async function getList() {
    let id = route.params.id
    let res = await getType(id)
    list.value = res
}
</script>

<style scoped lang="less"></style>
