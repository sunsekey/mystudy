package com.sunsekey.algorithm;

public class MySingleton {

    private String desc;

    private MySingleton(){
        desc = "hello world..";
    }

    public static MySingleton getInstance(){
        return SingletonEnum.INSTANCE.getInstance();
    }

    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    private enum SingletonEnum{
        INSTANCE;

        private MySingleton mySingleton;
        SingletonEnum(){
            mySingleton = new MySingleton();
        }

        public MySingleton getInstance(){
            return mySingleton;
        }
    }

}
