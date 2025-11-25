<script setup>
import {useRouter} from "vue-router";
import TeamCardList from "../components/TeamCardList.vue";
import {onMounted, ref} from "vue";
import myAxios from "../plugins/myAxios.ts";
import {showFailToast, showToast} from "vant";

const router = useRouter()
//跳转到创建队伍页
const doCreateTeam = () =>{
    router.push({
      path:"/team/add",
    })
}
const searchText = ref('')
const teamList = ref([]);
const active = ref('public')
const offset = ref({ x: 300, y: 475 });

const onTabChange = (name) =>{
  if(name === 'public'){
    //查公开
    listTeam(searchText.value,0)
  }else {
    //查加密
    listTeam(searchText.value,2)
  }
}
/**
 * 搜索队伍
 * @param val
 * @param status
 * @returns {Promise<void>}
 */
const listTeam = async (val = '',status = 0) =>{
  const res = await myAxios.get("/team/list",{
    params:{
      searchText:val,
      pageNum:1,
      status,
    },
  })
  if(res?.code === 0){
    teamList.value = res.data;
  }else {
    showFailToast('加载队伍失败，请刷新重试')
  }
}
//页面加载时只触发一次
onMounted( () =>{
  listTeam();
})

const onSearch =  (val) => {
    listTeam(val)
};

</script>

<template>
  <div id="teamPage">
    <van-search v-model="searchText" placeholder="搜索队伍" @search="onSearch"/>
    <van-tabs v-model:active="active" @change = "onTabChange">
      <van-tab title="公开" name = "public"/>
      <van-tab title="加密" name = "private"/>
    </van-tabs>
    <van-floating-bubble class="create-button" type="primary" icon="plus" v-model:offset="offset" @click="doCreateTeam" />
    <team-card-list :teamList="teamList"/>
    <van-empty v-if="teamList?.length<1" image="search" description="暂无符合要求的队伍" />
  </div>

</template>

<style scoped>

</style>