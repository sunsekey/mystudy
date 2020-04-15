package com.sunsekey.practise.designpattern.creational.singleinstance;

import java.util.HashMap;

public class SingletonFactory {
    //使用一个map来当注册表
    static private HashMap<String,Object> registry = new HashMap<>();
    //静态块，在类被加载时自动执行    
    static{
        SingletonFactory rs=new SingletonFactory();
        registry.put(rs.getClass().getName(),rs);
    }
    //受保护的默认构造函数，如果为继承关系，则可以调用，克服了单例类不能为继承的缺点    
    protected SingletonFactory(){}
    //静态工厂方法，返回此类的唯一实例    
    public static Object getInstance(String name){
        if (name == null) {
            return null;
        }
        if(registry.get(name)==null){
            try{
                registry.put(name, Class.forName(name).newInstance());
            }catch(Exception ex){ex.printStackTrace();}
        }
        return registry.get(name);
    }

    public static void main(String[] args) {
        SingletonFactory singletonFactory1 = new SingletonFactory();
        SingletonFactory singletonFactory2 = new SingletonFactory();
        SimpleBean simpleBean = (SimpleBean) SingletonFactory.getInstance("com.sunsekey.practise.designpattern.creational.singleinstance.SimpleBean");
        System.out.println(simpleBean);
        System.out.println(singletonFactory1);
        System.out.println(singletonFactory2);
    }
}
