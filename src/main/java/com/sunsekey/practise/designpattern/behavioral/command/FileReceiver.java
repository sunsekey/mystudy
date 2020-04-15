package com.sunsekey.practise.designpattern.behavioral.command;

public class FileReceiver extends Receiver{

    public void fileUpload() {
        System.out.println("文件上传");
    }

    public void fileDownload() {
        System.out.println("文件下载");
    }
}
