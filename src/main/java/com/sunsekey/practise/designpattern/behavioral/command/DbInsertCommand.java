package com.sunsekey.practise.designpattern.behavioral.command;

public class DbInsertCommand extends Command {
    public DbInsertCommand(Receiver receiver) {
        super(receiver);
    }
    @Override
    public void execute() {
        ((DbReceiver)receiver).dbInsertAction();
    }
}
