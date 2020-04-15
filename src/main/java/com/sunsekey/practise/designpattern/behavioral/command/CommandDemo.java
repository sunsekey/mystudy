package com.sunsekey.practise.designpattern.behavioral.command;

/**
 * 命令模式：将实际执行操作的对象（接收者）和下达操作的对象（调用者）通过一个命令对象解耦开来。由命令对象持有一个接收者，调用者持有命令对象 。
 * （简单来说就是调用者和接收这通过一个命令对象解耦开来）
 * 缺点：可能会产生很多命令对象
 * 扩展：1）实现动作的撤销（其实就是Command接口中增加一个撤销的方法，具体实现与execute逻辑相反即可）
 *      2）工作队列（如下例，可配合多线程）
 *
 */
public class CommandDemo {

    public static void main(String[] args) {
        // 创建接收者，真正执行动作
        Receiver dbReceiver = new DbReceiver();
        Receiver fileReceiver = new FileReceiver();
        Receiver socketReceiver = new SocketReceiver();

        // 创建命令，命令持有一个执行命令实际动作的接收者对象，命令执行实际是委托接收者对象去完成
        Command dbInsertCommand = new DbInsertCommand(dbReceiver);
        Command fileUploadCommand = new FileUploadCommand(fileReceiver);
        Command socketReceiveCommand = new SocketReceiveCommand(socketReceiver);

        // 创建调用者
        CommandScheduler commandScheduler = new CommandScheduler();
        // 调用不同的命令（这里实际是放到了队列了）
        commandScheduler.schedule(dbInsertCommand);
        commandScheduler.schedule(fileUploadCommand);
        commandScheduler.schedule(socketReceiveCommand);

        // 然后一次过执行所有命令（有点像服务员，收集了各种下单命令，然后在一起交给厨师）；更贴合是，从工作队列中取命令，然后多线程处理
        commandScheduler.executeAll();
        // todo 待复习到多线程再回来改造

    }
}
