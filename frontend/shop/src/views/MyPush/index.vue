<template>
    <div>
        <NavBar title="我发布的"></NavBar>
        <div>
            <ul>
                <li v-for="(contact, index) in contacts" :key="index">
                    <div class="row1">
                        <h3>{{ contact.title }}</h3>
                        <div class="button">
                            <span :class="{ pending: contact.status == 0, approved: contact.status == 1 }">
                                {{ contact.status == 0 ? "待审核" : "已通过" }}
                            </span>
                        </div>
                    </div>
                    <span>姓名: {{ contact.name }}</span>
                    <span>联系方式: {{ contact.phone }}</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import { getMyPush } from "@/api/index"
import NavBar from "../../components/NavBar/index.vue"
const contacts = ref([])
const getList = async () => {
    contacts.value = await getMyPush(localStorage.getItem("username"))
    console.log(contacts.value)
}

onMounted(() => {
    getList()
})
</script>

<style scoped lang="less">
div {
    width: auto;
}

ul {
    list-style: none;
    padding: 0;
}

li {
    border: 1px solid #ddd;
    border-radius: 8px;
    margin-bottom: 10px;
    padding: 15px;
    background-color: #fff;
    margin: 10px;
}

.row1 {
    display: flex;
    flex-direction: column;
    margin-bottom: 10px;
}

h3 {
    margin: 0;
    color: #333;
    font-size: 16px;
}

.button {
    display: flex;
    justify-content: flex-end;
    padding-top: 10px;
}

.button span {
    padding: 5px 10px;
    border-radius: 5px;
    display: inline-block;
    text-align: center;
    margin-left: 10px;
    font-size: 12px;
    font-weight: bold;
    color: #fff;
}

.button span.pending {
    background-color: #f39c12;
    /* 待审核状态的颜色 */
}

.button span.approved {
    background-color: #2ecc71;
    /* 已通过状态的颜色 */
}

span {
    display: block;
    margin-bottom: 5px;
    color: #555;
    font-size: 13px;
}
</style>
