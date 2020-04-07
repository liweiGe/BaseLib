package com.kongzue.baseframework;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
   static ExecutorService service = Executors.newFixedThreadPool(5);
    public static void main(String[] args) {
//        CyclicBarrier cyl = new CyclicBarrier(10, () -> {
//            System.out.println("线程组执行结束");
//
//        });
        CountDownLatch downLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            service.execute(new Readnum(i,downLatch));
        }
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("任务全部结束");
        }
        System.out.println(Thread.currentThread().getName());
        service.shutdown();
    }


    static class Readnum implements Runnable {
        private int id;
//        private CyclicBarrier cdl;
        CountDownLatch cdl;
        public Readnum(int id, CountDownLatch cdl) {
            super();
            this.id = id;
            this.cdl = cdl;
        }

        @Override
        public void run() {

                System.out.println(Thread.currentThread().getName() + "id");
                cdl.countDown();


        }
    }
}
