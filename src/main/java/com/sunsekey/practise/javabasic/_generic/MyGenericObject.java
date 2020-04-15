package com.sunsekey.practise.javabasic._generic;

import lombok.Data;

@Data
public class MyGenericObject<T> implements MyGenericInterface<T> {

    Class<?> classType;

    public MyGenericObject(Class<?> clazz) {
        classType = clazz;
    }
    @Override
    public T method1(T t) {
        // 编译器会擦除T的信息，所以不能写这样的代码
        // System.out.println(t instanceOf T);
        // 一是因为擦除，不能确定类型；而是无法确定T是否包含无参构造函数，所以不能new
        // T t1 = new T()
        return null;
    }

    /**
     * 可通过classType记录运行时传入的实参类型，达到instanceOf的效果
     * @param object
     * @return
     */
    public boolean isInstance(Object object) {
        return classType.isInstance(object);
    }

    /**
     * 虽然不能new，但可以通过传入一个具体工厂来创建对应的实例
     */
    public T newInstance(GenericFactory<T> factory) {
        return factory.create();
    }

    /**
     * 静态方法访问不到类上定义的泛型类型，要使用泛型，只能将静态方法设置为泛型方法
     * @param e
     * @param <E>
     */
    public static <E> void staticGenericMethod1(E e) {
        System.out.println(e);
    }

    public <E extends Number> T genericMethod1(T t, E e) {
        System.out.println(t);
        System.out.println(e);
        return t;
    }

}
