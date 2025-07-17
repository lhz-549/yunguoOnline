package com.hz.online.test;

import java.io.File;
import java.util.*;

public class test {

    public static void main2(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("ABCAD");         // 添加元素
        list.add("ABCAD2");         // 添加元素
        list.add("ABCAD3");         // 添加元素
        list.add("ABCAD4");         // 添加元素
        list.get(0);           // 获取索引元素
        list.remove(0);        // 移除元素
        list.size();           // 获取大小
        list.contains("A");    // 检查存在性
        System.out.println(list.subList(0,1));   // 获取子列表

        for (String s : list) {
            System.out.println(s);
        }

        Set<String> set = new HashSet<>();
        set.add("A");          // 添加元素
        set.remove("A");       // 移除元素
        set.contains("A");     // 检查存在性
        set.size();            // 获取大小
        // TreeSet特有
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(5);
        treeSet.add(3);
        treeSet.add(7);
        treeSet.add(10);
        treeSet.add(2);
        System.out.println(treeSet.first());       // 获取最小元素
        System.out.println(treeSet.last());        // 获取最大元素

    }

    public static void main3(String[] args) {
        String str = "hello world";
        Map<Character, Integer> map = new HashMap<>();

        for (char c : str.toCharArray()) {
            if (c != ' ') { // 可选：跳过空格
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }

        // 输出统计结果
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.println("'" + entry.getKey() + "' 出现了 " + entry.getValue() + " 次");
        }
    }

    public static void main(String[] args) {
//        List<String> list = Arrays.asList("apple", "banana");
//
//        Map<Character, Long> freqMap = list.stream()
//                .flatMap(str -> str.chars().mapToObj(c -> (char) c))
//                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
//
//        System.out.println(freqMap);


        listAllFiles(new File("G:\\图\\小程序配图"));
    }

    public static void listAllFiles(File dir) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                listAllFiles(f);
            }
        } else {
            System.out.println(dir.getAbsolutePath());
        }
    }
}
