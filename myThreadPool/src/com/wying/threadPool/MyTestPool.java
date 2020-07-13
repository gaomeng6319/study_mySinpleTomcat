package com.wying.threadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * description:自定义线程池学习
 * date: 2020/7/13
 * author: gaom
 * version: 1.0
 */
public class MyTestPool {

    //最大线程数
    private static final int maxThreadNum = 10;

    //最小线程数
    private static final int minThreadNum = 1;
    //工作队列
    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    //工作者线程池
    private List<Worker> workerList = Collections.synchronizedList(new ArrayList<>());
    //当前线程数
    private int workerNum ;


    private AtomicLong threadNumber = new AtomicLong();

    public MyTestPool(int initNum){

        //初始化工作线程
        this.workerNum = initNum > maxThreadNum ? maxThreadNum
                : initNum  <  minThreadNum ?  minThreadNum : initNum;
        System.out.println("初始化线程池 欲初始化initNum:"+initNum+" 实际初始化数量:"+this.workerNum);
        init(workerNum);

    }
    private void init(int workerNum) {

        for (int i = 0; i < workerNum; i++) {
            workerList.add(new Worker("我的工作编号：" + threadNumber.incrementAndGet()));
        }
        for (Worker worker : workerList) {
            new Thread(worker).start();
        }
    }

    //执行任务
    public void execute(Runnable runnable) {
        if (runnable != null) {
            synchronized (taskQueue) {
                //加入任务到队列
                taskQueue.add(runnable);
                //同时唤醒 调用wait堵塞了读取队列的线程
                taskQueue.notifyAll();
            }
        }
    }


    //关闭线程池
    public void shutdown() {
        for (Worker worker : workerList) {
            worker.shutdown();
        }
    }

    private class Worker implements Runnable {
        private volatile boolean isStop = false;
        private String workerName = "";

        public Worker(String workerName) {
            System.out.println("初始化了一个新的线程 workerName："+workerName);
            this.workerName = workerName;
        }

        //开启的N个线程不断从任务队列读取是否有新任务
        @Override
        public void run() {
            //工作者是否工作开关
            while (!isStop) {
                Runnable runnable = null;
                //由于并发线程安全问题 需要加锁
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty()) {
                        try {
                            //如果没有任务 则调用wait等待 此时会堵塞所有的线程
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    //任务列表不为空 获取任务
                    runnable = taskQueue.poll();
                }
                //当有任务时 调用notifyAll后 该线程继续工作
                if (runnable != null) {
                    try {
                        System.out.println(this.workerName+ " 开始执行任务 ");
                        //执行任务
                        runnable.run();
                        System.out.println(this.workerName+ " 任务执行完毕 ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            this.isStop = true;
            System.out.println(this.workerName + " is shutdown");
        }
    }

    public static void  main(String  args[]) {
        MyTestPool myTestPool = new MyTestPool(2);
        myTestPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("我需要去搬砖 预计耗时5S "+Thread.currentThread().getId());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        myTestPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("我需要去买酒  预计耗时10S "+Thread.currentThread().getId());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        myTestPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("我需要去送货  预计耗时8S "+Thread.currentThread().getId());
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
/*
        for(int t=0;t<30;t++){
        myTestPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试任务  查看执行的线程id"+Thread.currentThread().getId());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        }*/
    }


}

