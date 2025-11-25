<script setup lang="ts">
import {useRoute} from "vue-router";
import {onMounted, ref, watchEffect} from "vue";
import myAxios from "../plugins/myAxios.js"
import { showSuccessToast, showFailToast } from 'vant';
import qs from 'qs';
import UserCardList from "../components/UserCardList.vue";
//import myAxios from '../myAxios';


const isMatchMode = ref<boolean>(false);
const route = useRoute();
const {tags} = route.query;
const userList = ref([]);
const loading = ref(true);

/**
 * 加载数据
 */
const loadData = async () =>{
  let userListData
  loading.value = true;
  //匹配模式 根据标签匹配用户
  if(isMatchMode.value){
    const num = 10;
    userListData = await myAxios.get(`/user/match`,{
      params: {
        num
      },
      paramsSerializer: params => {
        return qs.stringify(params,{indices: true});
      }
    })
        .then(function (response) {
          // 处理成功情况
          console.log('/user/match success',response);
          showSuccessToast('请求成功');
          return response?.data;
        })
        .catch(function (error) {
          // 处理错误情况
          console.log('/user/match error',error);
          showFailToast('请求失败');
        });
    if(userListData){
      userListData.forEach((user) => {
        if (user.tags){
          user.tags = JSON.parse(user.tags);
        }
      })
      userList.value = userListData;
    }
  }else {
    //普通模式 分页查询用户
    userListData = await myAxios.get(`/user/recommend`,{
      params: {
        pageSize:8,
        pageNum:1,
      },
      paramsSerializer: params => {
        return qs.stringify(params,{indices: true});
      }
    })
        .then(function (response) {
          // 处理成功情况
          console.log('/user/recommend success',response);
          return response?.data?.records;
        })
        .catch(function (error) {
          // 处理错误情况
          console.log('/user/recommend error',error);
          showFailToast('请求失败');
        });
    if(userListData){
      userListData.forEach((user) => {
        if (user.tags){
          user.tags = JSON.parse(user.tags);
        }
      })
      userList.value = userListData;
    }
  }
  loading.value = false;
}
watchEffect(() =>{
  loadData();
})
</script>

<template>
  <van-cell center title="心动模式">
    <template #right-icon>
      <van-switch v-model="isMatchMode" />
    </template>
  </van-cell>
  <user-card-list :user-list="userList" :loading="loading"/>
  <van-empty v-if="!userList || userList.length<1" image="search" description="暂无符合要求的用户" />
</template>

<style scoped>

</style>