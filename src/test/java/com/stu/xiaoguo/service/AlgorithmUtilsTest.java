package com.stu.xiaoguo.service;

import com.stu.xiaoguo.utils.AlgorithmUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 算法工具类测试
 */
public class AlgorithmUtilsTest {
    @Test
    void test() {
        String a = "鱼皮是狗";
        String b = "鱼皮是猫";
        int i = AlgorithmUtils.minDistance(a,b);
        System.out.println(i);
    }
    @Test
    void testTagList() {
        List<String> list1 = Arrays.asList("Java", "大一", "男");
        List<String> list2 = Arrays.asList("Java", "大一", "女");
        List<String> list3 = Arrays.asList("Python", "大二", "女");
        int i = AlgorithmUtils.minDistanceList(list1,list2);
        int j = AlgorithmUtils.minDistanceList(list1,list3);
        System.out.println(i);
        System.out.println(j);
    }
}
