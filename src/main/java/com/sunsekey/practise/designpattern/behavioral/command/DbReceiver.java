package com.sunsekey.practise.designpattern.behavioral.command;

public class DbReceiver extends Receiver{


    public void dbInsertAction() {
        System.out.println("数据库插入操作");
    }

    public void dbUpdateAction() {
        System.out.println("数据库更新操作");
    }
}
