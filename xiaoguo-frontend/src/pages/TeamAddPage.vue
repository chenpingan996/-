<script setup lang="ts">

import {ref} from "vue";
import myAxios from "../plugins/myAxios.ts";
import {showFailToast, showSuccessToast} from "vant";
import {useRouter} from "vue-router";
import dayjs from "dayjs";



const router = useRouter();
const initFormData = {
  "name": "",
  "description": "",
  "expiresTime": "",
  "maxNum": 3,
  "password": "",
  "status": 0,
}
//需要用户自己填写的表单数据
const addTeamData = ref({...initFormData})
//展示日期选择器
const showPicker = ref(false)
const minDate = new Date();

const confirm = ({selectedValues} : any) =>{
  addTeamData.value.expiresTime = selectedValues.join("-")+" 00:00:00";
  showPicker.value = false;
};

//提交
const onSubmit = async () =>{
  const postData = {
    ...addTeamData.value,
    status: Number(addTeamData.value.status),
    expiresTime: dayjs(addTeamData.value.expiresTime).toISOString()
  }
  const res = await myAxios.post("team/add", postData)
  if (res?.code === 0 && res.data) {
    showSuccessToast('添加成功')
    router.push({
      path: "/team",
      replace: true,
    })
  }else {
    showFailToast('添加失败')
  }
}
</script>

<template>
  <div id="teamAddPage">
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
            v-model="addTeamData.name"
            name="name"
            label="队伍名称"
            placeholder="请输入队伍名称"
            :rules="[{ required: true, message: '请输入队伍名称' }]"
        />
        <van-field
            v-model="addTeamData.description"
            rows="4"
            autosize
            label="队伍描述"
            type="textarea"
            placeholder="请输入队伍描述"
        />
        <van-field
            is-link
            readonly
            v-model="addTeamData.expiresTime"
            name="expiresTime"
            label="过期时间"
            :placeholder="addTeamData.expiresTime ?? '点击选择过期时间'"
            @click="showPicker = true"
        />
        <van-popup v-model:show="showPicker" destroy-on-close position="bottom">
          <van-date-picker
              type="date"
              title="请选择过期时间"
              :min-date="minDate"
              @cancel="showPicker = false"
              @confirm="confirm"
          />
        </van-popup>
        <van-field name="stepper" label="队伍人数">
          <template #input>
            <van-stepper v-model="addTeamData.maxNum" max="10" min="3"/>
          </template>
        </van-field>
        <van-field name="radio" label="队伍状态">
          <template #input>
            <van-radio-group v-model="addTeamData.status" direction="horizontal">
              <van-radio name="0">公开</van-radio>
              <van-radio name="1">私有</van-radio>
              <van-radio name="2">加密</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field
            v-if="Number(addTeamData.status) === 2"
            v-model="addTeamData.password"
            type="password"
            name="password"
            label="密码"
            placeholder="请输入队伍密码"
            :rules="[{ required: true, message: '请填写队伍密码' }]"
        />
      </van-cell-group>
      <div style="margin: 16px;">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<style scoped>

</style>