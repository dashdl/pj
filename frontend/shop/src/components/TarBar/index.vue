<template>
  <van-tabbar v-model="active" @change="onChange" route>
    <van-tabbar-item icon="home-o" to="/home">首页</van-tabbar-item>
    <van-tabbar-item icon="guide-o" to="/settle">发布</van-tabbar-item>
    <van-tabbar-item icon="chat-o" to="/message">消息</van-tabbar-item>
    <van-tabbar-item icon="friends-o" to="/personal">我的</van-tabbar-item>
  </van-tabbar>
</template>
  
<script setup>
import { ref } from 'vue';
const active = ref(0);
const onChange = (index) => {
  active.value = index;
};
</script>
  