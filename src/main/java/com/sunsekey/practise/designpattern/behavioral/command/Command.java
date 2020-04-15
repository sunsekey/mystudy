package com.sunsekey.practise.designpattern.behavioral.command;

/**
 * 抽象命令对象
 */
public abstract class Command {

    protected Receiver receiver;

    public Command(Receiver receiver) {
        this.receiver = receiver;
    }

    public abstract void execute();

}
