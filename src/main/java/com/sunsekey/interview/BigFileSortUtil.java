package com.sunsekey.interview;

import lombok.SneakyThrows;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  一个大文件10G, 里面一行行的数字(Integer，一行一个数字)，要找其中最大数字
 *  面试时间到21点，最后10分钟时，来不及编码的地方 可以用注释写一下思路
 */
public class BigFileSortUtil {

    // 存放每个子文件最大的数
    private static List<Integer> candidates = Collections.synchronizedList(new ArrayList<>());
    // 子文件map key-index,value-fileName
    private static Map<Integer, String> fileMap = new HashMap<>();

    // 每个子文件的大小
    private final static Integer SIZE = 10240; // 10M

    // 工作线程数量
    private final static Integer THREAD_COUNT = 20;


    /**
     * @param fileName 大文件path
     * @return 最大的数字
     */
    public static String findMax(String fileName) {
        splitFile(fileName);
        doFind();
        return findMaxFromCandidates().toString();

    }

    /**
     * 对每个子文件选出的最大值再作比较，选择最大的
     * @return
     */
    public static Integer findMaxFromCandidates() {
        Integer max = 0;
        // 对candidates排序
        return Collections.max(candidates);
    }

    /**
     * 开启线程去工作
     */
    public static void doFind() {
        // 假设是8核cpu
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        executorService.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                findMaxInSubFile();
            }
        });
        executorService.shutdown();
    }

    /**
     * 从小文件中找出最大的数
     */
    @SneakyThrows
    public static void findMaxInSubFile() throws FileNotFoundException {

        // 伪代码
        String fileName = fileMap.get(0);
        int max = Integer.MIN_VALUE;
        //
        File subFile = new File(fileName);
        FileInputStream fis = new FileInputStream(subFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String s = "";
        while ((s = br.readLine()) != null) {
            // 直接和max比较，更大则替换
            int cur = Integer.parseInt(s);
            if (cur > max) {
                max = cur;
            }
        }
        candidates.add(max);
    }



    /**
     * 拆分文件
     */
    private static void splitFile(String fileName) {
        File file = new File(fileName);
        byte[] data = new byte[(int) file.length()];
        try {
            FileInputStream fis = new FileInputStream(file);

            fis.read(data);// 读取文件到data中
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int len = 1024 * 100;
        int n = (int) (file.length() / len);// 拆分为多少100k的文件夹
        int m = (int) (file.length() / len);// 余数再额外准备一个文件夹
        File[] fs = new File[n + 1];// 设立文件数组，用来存放文件。长度为总共需要生成文件的个数。

        for (int i = 0; i < n + 1; i++) {

            int start = i * 102400;
            int end = (i + 1) * 102400;
            fs[i] = new File(file + "-" + i);

            if (i < n) {
                byte[] b = Arrays.copyOfRange(data, start, end);

                try {
                    FileOutputStream fos = new FileOutputStream(fs[i]);
                    fos.write(b);
                    fos.close();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {

                start = i * 102400;
                end = (int) file.length();
                byte[] b = Arrays.copyOfRange(data, start, end);

                try {
                    FileOutputStream fos = new FileOutputStream(fs[i]);
                    fos.write(b);
                    fos.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            System.out.println("输出子文件夹：" + fs[i].getAbsolutePath()
                    + "  其长度为： " + fs[i].length());
        }
    }

}