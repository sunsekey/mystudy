package com.sunsekey.practise.designpattern.behavioral.command;

public class FileUploadCommand extends Command{
    public FileUploadCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        ((FileReceiver)receiver).fileUpload();
    }
}
