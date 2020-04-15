package com.sunsekey.practise.javabasic._object;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 重写equals方法，hashCode方法也要一起重写；而只重写hashCode不重写equals虽然不会有太大问题，但没什么意思（个人认为）
 * 如果只重写了equals，两个equals的对象在作为map的key进行put时，会被认为是两个不同的key，因为hashCode不一样
 * 参考：https://zhuanlan.zhihu.com/p/30321358
 */
public class EqualsDemo {

    public static void main(String[] args) {
        MyObject myObject1 = new MyObject();
        myObject1.setName("hello world");
        myObject1.setNum(1);
        MyObject myObject2 = new MyObject();
        myObject2.setName("hello world");
        myObject2.setNum(1);
        System.out.println(myObject1.hashCode());
        System.out.println(myObject2.hashCode());
        System.out.println(myObject1.equals(myObject2));

        Map<MyObject, String> map = new HashMap<>();
        map.put(myObject1, myObject1.getName());
        map.put(myObject2, myObject2.getName());
        System.out.println(map.size());
    }

    @Data
    public static class MyObject{
        private String name;
        private Integer num;
        @Override
        public boolean equals(Object o){
            if (o instanceof MyObject) {
                boolean nameEq = ((MyObject) o).getName().equals(this.name);
                boolean numEq = ((MyObject) o).getNum().equals(this.num);
                return nameEq && numEq;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode() + this.num.hashCode();
        }
    }
}
