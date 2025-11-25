<script setup lang="ts">
import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import myAxios from "../plugins/myAxios.ts";
import { showFailToast,showSuccessToast } from 'vant';
import {getCurrentUser} from "../services/user.ts";

const router = useRouter();
const toEdit = (editKey: string,editName:string,currentValue:string) => {
  router.push({
    path: '/user/edit',
    query: {
      editKey,
      editName,
      currentValue,
    }
  })
}

// const user = {
//   id: 1,
//   username: '小果',
//   userAccount: 'xiaoguo',
//   avatarUrl: 'https://ts1.tc.mm.bing.net/th/id/R-C.77419dc707507ce56c7c702cc5213abb?rik=vk8Ee92g0blD3w&riu=http%3a%2f%2fpuui.qpic.cn%2fvpic_cover%2fg3104cnbxm1%2fg3104cnbxm1_hz.jpg%2f1280&ehk=cKGwr5NkA3vocJj75ytn77GmMI%2fHa1HySiAWmuCSGBc%3d&risl=&pid=ImgRaw&r=0',
//   gender: '男',
//   phone: '123112312',
//   email: '12345@qq.com',
//   planetCode: '001',
//   createTime: new Date(),
// }

const user = ref()
onMounted(async () =>{
  user.value = await getCurrentUser();

})
</script>

<template>
<!--  <template v-if="user">-->
<!--    <van-cell title="昵称" is-link to="/user/edit" :value="user.username" @click="toEdit('username','昵称',user.username)"/>-->
<!--    <van-cell title="账号" :value="user.userAccount"/>-->
<!--    <van-cell title="头像" is-link to="/user/edit">-->
<!--      <img style="height: 30px" :src="user.avatarUrl" />-->
<!--    </van-cell>-->
<!--    <van-cell title="性别" is-link to="/user/edit" :value="user.gender" @click="toEdit('gender','性别',user.gender)"/>-->
<!--    <van-cell title="电话" is-link to="/user/edit" :value="user.phone" @click="toEdit('phone','电话',user.phone)"/>-->
<!--    <van-cell title="邮箱" is-link to="/user/edit" :value="user.email" @click="toEdit('email','邮箱',user.email)"/>-->
<!--    <van-cell title="编号" :value="user.planetCode"/>-->
<!--    <van-cell title="注册时间" :value="user.createTime"/>-->
<!--  </template>-->
  <van-cell title="当前用户" :value="user?.username" />
  <van-cell title="修改信息" is-link to="/user/update" />
  <van-cell title="我创建的队伍" is-link to="/user/team/create" />
  <van-cell title="我加入的队伍" is-link to="/user/team/join" />
</template>

<style scoped>

</style>