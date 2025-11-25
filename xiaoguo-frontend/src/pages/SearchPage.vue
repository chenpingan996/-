<script setup lang="ts">
import { ref } from 'vue';
import {useRouter} from "vue-router";
const router = useRouter()

const searchText = ref('');

//标签列表
const originTagList = [
  {
    text: '性别',
    children: [
      { text: '男', id: '男' },
      { text: '女', id: '女' },
    ],
  },
  {
    text: '年级',
    children: [
      { text: '大一', id: '大一' },
      { text: '大二', id: '大二' },
      { text: '大三', id: '大三' },
      { text: '大四', id: '大四' },
    ],
  },
];
let tagList = ref(originTagList);

const onSearch = () => {
  tagList.value = originTagList.map(parentTag => {
    const tempChildren = [...parentTag.children];
    const tempParentTag = {...parentTag};
    tempParentTag.children = tempChildren.filter(item => item.text.includes(searchText.value));
    return tempParentTag;
  });
}
const onCancel = () => {
  //router.push({ path: '/' })
  tagList.value = originTagList;
};
//已选择的标签
const activeIds = ref([]);
const activeIndex = ref(0);

//移除标签
const doClose = (tag : String) => {
  activeIds.value = activeIds.value.filter(item => {
    return item !== tag;
  })
}
/**
 * 执行搜索
 */
const doSearchResult = () => {
    router.push({
      path: '/user/list',
      query: {
        tags: activeIds.value,
      }
    })
}


</script>

<template>
  <van-search
      v-model="searchText"
      placeholder="请输入要搜索的标签"
      wrap-with-form
      show-action
      @search="onSearch"
      @cancel="onCancel"
  />
  <van-divider content-position="left">已选标签</van-divider>
  <div v-if="activeIds.length === 0">请选择标签</div>
  <van-row gutter="16" style="padding: 0 16px">
    <van-col v-for="tag in activeIds">
      <van-tag closeable size="medium" type="primary" @close = "doClose(tag)">
        {{ tag }}
      </van-tag>
    </van-col>
  </van-row>
  <van-divider content-position="left">选择标签</van-divider>
  <van-tree-select
        v-model:active-id="activeIds"
        v-model:main-active-index="activeIndex"
        :items="tagList"
  />
  <div style="padding: 12px">
    <van-button type="primary" @click="doSearchResult" block>搜索</van-button>
  </div>

</template>

<style scoped>

</style>