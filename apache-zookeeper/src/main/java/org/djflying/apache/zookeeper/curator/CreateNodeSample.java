package org.djflying.apache.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator创建节点
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class CreateNodeSample {

    /**
     * 测试使用Curator创建节点
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();
        // 1.创建一个节点，初始化内容为空。
        /*
            如果没有设置节点属性，那么Curator默认创建的是持久节点。
         */
        client.create().forPath("/zk_test1");
        // 2.创建一个节点，附带初始内容。
        client.create().forPath("/zk_test2", "init".getBytes());
        // 3.创建一个临时节点，初始化内容为空。
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/zk_test3");
        // 4.创建一个临时节点，并自动递归创建父节点。
        /*
            1.通过调用creatingParentsIfNeeded接口，Curator就能够自动地递归创建所有需要的父节点。
            2.由于在Zookeeper中规定了所有非叶子节点必须为持久节点，
              所以使用这个方法创建的话，只有path参数对应的数据节点是临时节点，其父节点均为持久节点。
         */
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/zk_test4/test");
        // 5.创建一个临时节点，初始内容为init，并自动递归创建父节点
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/zk_test5/test", "init".getBytes());
    }
}