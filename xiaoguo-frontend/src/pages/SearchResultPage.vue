<script setup>
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import myAxios from "../plugins/myAxios.js"
import { showFailToast } from 'vant';
import qs from 'qs';
import UserCardList from "../components/UserCardList.vue";


const route = useRoute();
const {tags} = route.query;
const userList = ref([])
const loading = ref(true);

onMounted(async () => {
  loading.value = true;
  const userListData = await myAxios.get(`/user/search/tags?tagNameList=${tags}`,{
      params: {
        tagNameList: tags,
      },
      paramsSerializer: params => {
        return qs.stringify(params,{indices: true});
      }
      })
      .then(function (response) {
        // 处理成功情况
        console.log('/user/search/tags success',response);
        return response?.data;
      })
      .catch(function (error) {
        // 处理错误情况
        console.log('/user/search/tags error',error);
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
  loading.value = false;
})

</script>

<template>
  <user-card-list :user-list="userList" :loading="loading"/>
  <van-empty v-if="!userList || userList.length<1" image="search" description="暂无符合要求的用户" />
</template>

<style scoped>

</style>