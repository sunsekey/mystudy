package com.sunsekey.practise.designpattern.behavioral.command;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 调用者角色
 */
public class CommandScheduler {

    private Queue<Command> commandQueue = new LinkedList<>();

    public void schedule(Command command) {
        commandQueue.add(command);
    }

    public void executeAll() {
        for (Command command : commandQueue) {
            command.execute();
        }
    }
}
