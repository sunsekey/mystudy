package com.sunsekey.practise.javabasic._generic;

/**
 * 编译器会在编译过程中擦除参数的类型信息（擦除到第一个边界）
 * 但编译时会自动加上类型转换，类型检查等逻辑
 *
 * 参考：
 *      https://juejin.im/post/5b28ca4e51882574e10df32c
 *      https://juejin.im/post/5b614848e51d45355d51f792#comment
 */
public class MyGenericDemo {

    public static void main(String[] args) {
        MyGenericObject<Integer> myGenericObject = new MyGenericObject<>(String.class);
        Integer intInst = myGenericObject.newInstance(new IntegerFactory());
        System.out.println("--newInstance--");
        System.out.println(intInst);
        System.out.println("--isInstance--");
        System.out.println(myGenericObject.isInstance("abc"));
        System.out.println("-------genericMethod1-------");
        myGenericObject.genericMethod1(10, 15L);
        System.out.println("-------staticGenericMethod1-------");
        MyGenericObject.staticGenericMethod1(10);
        // 不能创建一个确切的泛型类型的数组，只能用通配符
        MyGenericObject<?>[] genericObjects = new MyGenericObject<?>[10];

        System.out.println("--------food demo--------");
        // 上界通配符，限定了容器里只能装Fruit及其派生类的元素
        Plate<? extends Fruit> fruitPlate = new Plate<>();
        //fruitPlate.setItem(new Apple()); // 不能直接放Apple，因为编译器只知道这里能存Fruit及其派生类型，但不知道具体是哪一种类型
        Fruit fruit = fruitPlate.getItem();// 但是get出来是可以用Fruit类型去接的，因为编译器至少知道这是个Fruit类型，即上界是Fruit
        System.out.println(fruitPlate.getItem());
        // 下界通配符，限定了容器里能装Fruit及其基类类型的元素（理所当然的，它们的派生类型也可以装），所以这是限定了下界，Fruit及其基类型以下的都能装
        Plate<? super Fruit> superFruitPlate = new Plate<>();
        superFruitPlate.setItem(new Apple()); // 这里放Apple是可以的，因为它肯定是Fruit及其基类的派生类型
        Object object =  superFruitPlate.getItem(); // 拿出来时就得作转换了，因为编译器没办法知道实际是哪一种类型，有可能是Fruit
        // ，有可能是Food，说不定Food往上还有，所以只能用最大的Object来接，然后根据情况作类型转换
        Apple apple = (Apple) object;
        System.out.println(apple);
    }

    static class Plate<T>{
        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        T item;
    }

    static class Food{}

    static class Fruit extends Food {}
    static class Meat extends Food {}

    static class Apple extends Fruit {}
    static class Banana extends Fruit {}
    static class Pork extends Meat{}
    static class Beef extends Meat{}

    static class RedApple extends Apple {}
    static class GreenApple extends Apple {}

}
