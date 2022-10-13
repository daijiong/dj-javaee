package org.djflying.apache.zookeeper.curator.recipes;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator实现分布式计数器
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class RecipesDistAtomicIntSample {

    static String distatomicint_path = "/curator_recipes_distatomicint_path";

    /**
     * 测试使用Curator实现分布式计数器
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();
        // 1.新建DistributedAtomicInteger实例
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, distatomicint_path, new RetryNTimes(3, 1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        System.out.println("Result: " + rc.succeeded());
    }
}