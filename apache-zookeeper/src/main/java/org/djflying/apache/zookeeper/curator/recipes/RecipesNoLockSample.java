package org.djflying.apache.zookeeper.curator.recipes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 典型时间戳生成的并发问题
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class RecipesNoLockSample {

    public static void main(String[] args) throws Exception {

        final CountDownLatch down = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        down.await();
                    } catch (Exception e) {
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.err.println("生成的订单号是 : " + orderNo);
                }
            }).start();
        }
        down.countDown();
    }
}