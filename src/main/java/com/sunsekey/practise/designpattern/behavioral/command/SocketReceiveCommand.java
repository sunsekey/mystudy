package com.sunsekey.practise.designpattern.behavioral.command;

public class SocketReceiveCommand extends Command {
    public SocketReceiveCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        ((SocketReceiver)receiver).socketReceive();
    }
}
