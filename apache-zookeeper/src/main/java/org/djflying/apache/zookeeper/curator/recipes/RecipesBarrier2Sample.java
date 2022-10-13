package org.djflying.apache.zookeeper.curator.recipes;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator实现分布式Barrier（线程自发触发Barrier释放）
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class RecipesBarrier2Sample {

    static String barrier_path = "/curator_recipes_barrier_path";

    /**
     * 测试使用Curator实现分布式Barrier
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString)
                            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
                        client.start();
                        // 1.新建DistributedDoubleBarrier实例，并指定了进入Barrier的成员数阈值
                        DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, barrier_path, 5);
                        Thread.sleep(Math.round(Math.random() * 3000));
                        // 2.等待进入（当等待进入的线程数量达到阈值5，则这5个线程同时继续执行）
                        System.out.println(Thread.currentThread().getName() + "号进入barrier");
                        barrier.enter();
                        // 3.业务处理
                        System.out.println("启动...");
                        Thread.sleep(Math.round(Math.random() * 30000));
                        // 4.等待退出（当等待退出的线程数量达到阈值5，则这5个线程同时继续执行）
                        barrier.leave();
                        System.out.println("退出...");
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
    }
}