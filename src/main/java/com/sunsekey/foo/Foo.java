package com.sunsekey.foo;

import java.util.HashSet;
import java.util.Objects;

public class Foo extends BaseFoo {

    @Override
    public void foobar() {

    }

    public static void main(String[] args) {
        HashSet<MyClass> hs = new HashSet<>();
        MyClass myClass1 = new MyClass("abc");
        MyClass myClass2 = new MyClass("cba");
        hs.add(myClass1);
        hs.add(myClass2);
        System.out.println(hs.size());
        System.out.println("hc2: " + myClass2.hashCode());
        myClass2.setName("qqq");
        System.out.println("hc2: " + myClass2.hashCode());
        System.out.println(hs.size());
    }

    private static class MyClass{
        private String name;

        public MyClass(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyClass myClass = (MyClass) o;
            return name.equals(myClass.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }



}
