package org.djflying.apache.zookeeper.curator.recipes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator实现分布式锁功能
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class RecipesLockSample {

    static String lock_path = "/curator_recipes_lock_path";

    /**
     * 测试使用Curator实现分布式锁功能
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();
        // 1.新建一个InterProcessMutex实例
        final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
        final CountDownLatch down = new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        down.await();
                        // 分布式锁获取
                        lock.acquire();
                    } catch (Exception e) {
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.out.println("生成的订单号是 : " + orderNo);
                    try {
                        // 分布式锁释放
                        lock.release();
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
        down.countDown();
    }
}