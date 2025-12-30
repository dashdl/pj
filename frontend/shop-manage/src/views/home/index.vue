<template>
    <div class="content">
        <h1 class="title">欢迎来到学生二手交易后台管理系统</h1>
        <el-card>
            <h1>全部信息</h1>
            <div class="table" ref="tableRef"></div>
        </el-card>
    </div>
</template>

<script setup>
import * as echarts from "echarts"
import { onMounted, ref } from "vue"
import { getOrder, getUser, getShop } from "../../api/index"

const tableRef = ref(null)

onMounted(async () => {
    const myChart = echarts.init(tableRef.value)

    const updateChart = async () => {
        const orderCount = await getOrder()
        const userCount = await getUser()
        const shopCount = await getShop()

        const option = {
            title: {
                text: "信息表格",
                subtext: "包含用户，订单，商品",
                left: "center",
            },
            tooltip: {
                trigger: "item",
            },
            legend: {
                orient: "vertical",
                left: "left",
            },
            series: [
                {
                    name: "详情数据",
                    type: "pie",
                    radius: "50%",
                    data: [
                        { value: userCount.length, name: "用户" },
                        { value: shopCount.length, name: "商品" },
                        { value: orderCount.length, name: "订单" },
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: "rgba(0, 0, 0, 0.5)",
                        },
                    },
                },
            ],
        }

        myChart.setOption(option)
    }

    updateChart()
    tableRef.value = myChart
})
</script>

<style scoped lang="less">
.content {
    display: flex;
    flex-direction: column;
    align-items: center;

    .title {
        font-size: 30px;
        padding: 30px 0 60px 0;
    }
    .table {
        width: 1000px;
        height: 400px;
        margin-top: 40px;
    }
}
</style>
