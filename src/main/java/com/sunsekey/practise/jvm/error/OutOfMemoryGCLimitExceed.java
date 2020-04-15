package com.sunsekey.practise.jvm.error;

import java.util.*;

/**
 * consume almost all of the heap, keep it allocated, and then allocate lots of garbage
 * -Xmx300m
 * 可
 * 1）可增大堆内存
 * 2）增加-XX:-UseGCOverheadLimit选项来关闭GC Overhead limit exceeded的报错，治标不治本，迟早还是会报Java heap space
 * 但都是治标不治本
 *
 * 用JVisualVM、JConsole等工具
 *
 * ps:
 * Linux下发生OOM，不一定完全因为Java服务耗内存，也可能是因为其他程序申请了很多内存，
 * 此时所有应用所需要的内存超过物理内存，然后Java服务相对很耗内存且被Linux操作系统找到，
 * 就会被 kill，这是Linux为避免物理内存过载导致系统崩溃而采取的内存保护机制，这种机制称为OOM Killer
 *
 */
public class OutOfMemoryGCLimitExceed {

    public static void main(String[] argv)
            throws Exception
    {
        List<Object> fixedData = consumeAvailableMemory();
        while (true)
        {
            // 不断创建对象并立即"丢弃"它，（上一次循环中new的对象在当前循环中已经没被引用了，即可回收了）
            // 一段时间后，就会造成，jvm花了大量时间（98%）去回收对象，但只有少量对象（2%）被回收的现象
            // 即报OutOfMemoryError: GC overhead limit exceeded
            Object data = new byte[64 * 1024 - 1];
        }
    }


    /**
     * 先使得堆差不多被用满
     * @return
     * @throws Exception
     */
    private static List<Object> consumeAvailableMemory()
            throws Exception
    {
        LinkedList<Object> holder = new LinkedList<Object>();
        while (true)
        {
            try
            {
                holder.add(new byte[128 * 1024]);
            }
            catch (OutOfMemoryError ex)
            {
                // 堆被用满后放弃最后一个元素，使得堆还有一点点空间
                holder.removeLast();
                return holder;
            }
        }
    }
}
