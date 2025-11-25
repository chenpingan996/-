<script setup lang="ts">
import type {TeamType} from "../models/team";
import {teamStatusEnum} from "../constants/team.ts";
import myAxios from "../plugins/myAxios.ts";
import {showFailToast, showSuccessToast} from "vant";
import {onMounted, ref} from "vue";
import {getCurrentUser} from "../services/user.ts";
import {useRouter} from "vue-router";

interface TeamCardListProps {
  teamList: TeamType[];
}
const props = withDefaults(defineProps<TeamCardListProps>(),{
  teamList: () => [] as TeamType[],
})
const router = useRouter()
const currentUser = ref();
const showPasswordDialog = ref(false);
const password = ref('')
const joinTeamId = ref();
onMounted(async ()=>{
  currentUser.value = await getCurrentUser();
})

const preJoinTeam = (team:TeamType) => {
  joinTeamId.value = team.id
  if(team.status === 0){
    doJoinTeam()
  }else {
    showPasswordDialog.value = true;
  }
}
//加入队伍
const doJoinTeam = async () =>{
    if (!joinTeamId.value){
        return;
    }
    const res = await myAxios.post('/team/join',{
      teamId:joinTeamId.value,
      password:password.value,
    })
    if (res?.code === 0){
      showSuccessToast('加入成功')
      doJoinCancel()
    }else {
      showFailToast('加入失败' + (res.description ? `，${res.description}` : ''))
    }
}

/**
 * 跳转至更新队伍页
 * @param id
 */
const doUpdateTeam = (id: number) =>{
    router.push({
      path: '/team/update',
      query:{
        id,
      }
    })
}
/**
 * 退出队伍
 * @param id
 */
const doQuitTeam = async (id : number) =>{
  const res = await myAxios.post('/team/quit',{
    teamId:id,
  })
  if (res?.code === 0){
    showSuccessToast('退出成功')
  }else {
    showFailToast('退出失败' + (res.description ? `，${res.description}` : ''))
  }
}
/**
 * 解散队伍
 * @param id
 */
const doDeleteTeam = async (id: number) =>{
  const res = await myAxios.post('/team/delete',{
    id:id,
  })
  if (res?.code === 0){
    showSuccessToast('解散成功')
  }else {
    showFailToast('解散失败' + (res.description ? `，${res.description}` : ''))
  }
}
//清空输入框
const doJoinCancel = () =>{
  joinTeamId.value = 0
  password.value = ''
}
</script>

<template>
  <div id="teamCardList">
    <van-card
        v-for="team in props.teamList"
        thumb="https://s1.aigei.com/prevfiles/155aa1c1c1dc4ac980165f02b5767fbc.png?e=2051020800&token=P7S2Xpzfz11vAkASLTkfHN7Fw-oOZBecqeJaxypL:dl0zquo-JbhrBgghogXtlVD2LD4="
        :desc="team.description"
        :title="`${team.name}`"
    >
      <template #tags>
        <van-tag plain type="danger" style="margin-right: 8px;margin-top: 8px">
          {{ teamStatusEnum[team.status as keyof typeof teamStatusEnum] }}
        </van-tag>
      </template>
      <template #bottom>
        <div>
          {{
            `队伍人数：${team.hasJoinNum}/${team.maxNum}`
          }}
        </div>
        <div v-if="team.expiresTime">
          {{
            `过期时间：`+team.expiresTime
          }}
        </div>
        <div>
          {{
            `创建时间：`+team.createTime
          }}
        </div>
      </template>
      <template #footer>
        <van-button v-if="(team.userId !== currentUser?.id) && !team.hasJoin" size="small" type="primary" plain
                    @click ="preJoinTeam(team)">加入队伍</van-button>
        <van-button v-if="team.userId === currentUser?.id" size="small" plain
                    @click = "doUpdateTeam(team.id)">更新队伍</van-button>
        <van-button v-if="(team.userId !== currentUser?.id) && team.hasJoin" size="small" plain
                    @click = "doQuitTeam(team.id)">退出队伍</van-button>
        <van-button v-if="team.userId === currentUser?.id" size="small" type="danger" plain
                    @click = "doDeleteTeam(team.id)">解散队伍</van-button>
      </template>
    </van-card>

    <van-dialog v-model:show="showPasswordDialog" title="请输入密码" show-cancel-button @confirm="doJoinTeam" @cancel="doJoinCancel">
      <van-field v-model="password" placeholder="请输入密码" />
    </van-dialog>
  </div>

</template>

<style scoped>
#teamCardList :deep(.van-image__img){
  height: 120px;
}
</style>