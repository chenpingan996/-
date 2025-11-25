<script setup>
import {useRouter} from "vue-router";
import TeamCardList from "../components/TeamCardList.vue";
import {onMounted, ref} from "vue";
import myAxios from "../plugins/myAxios.ts";
import {showFailToast, showToast} from "vant";

const router = useRouter()

const searchText = ref('')
const teamList = ref([]);
/**
 * 搜索队伍
 * @param val
 * @returns {Promise<void>}
 */
const listTeam = async (val = '') =>{
  const res = await myAxios.get("/team/list/my/join",{
    params:{
      searchText:val,
      pageNum:1,
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
  listTeam()
})

const onSearch =  (val) => {
    listTeam(val)
};
</script>

<template>
  <div id="teamPage">
    <van-search v-model="searchText" placeholder="搜索队伍" @search="onSearch"/>
    <team-card-list :teamList="teamList"/>
    <van-empty v-if="teamList?.length<1" image="search" description="暂无符合要求的队伍" />
  </div>

</template>

<style scoped>

</style>