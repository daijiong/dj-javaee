package org.djflying.apache.zookeeper.curator.recipes;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator实现分布式Barrier（主线程触Barrier释放）
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class RecipesBarrierSample {

    static String             barrier_path = "/curator_recipes_barrier_path";
    static DistributedBarrier barrier;

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
                        // 1.创建DistributedBarrier实例
                        barrier = new DistributedBarrier(client, barrier_path);
                        // 2.设置Barrier
                        System.out.println(Thread.currentThread().getName() + "号barrier设置");
                        barrier.setBarrier();
                        // 3.等待Barrier释放
                        barrier.waitOnBarrier();
                        // 4.业务处理
                        System.err.println("启动...");
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
        Thread.sleep(20000);
        // 4.释放Barrier，等待的线程全部继续执行
        barrier.removeBarrier();
    }
}