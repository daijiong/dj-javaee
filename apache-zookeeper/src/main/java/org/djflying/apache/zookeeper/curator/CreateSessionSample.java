package org.djflying.apache.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用curator来创建一个ZooKeeper客户端
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class CreateSessionSample {

    /**
     * 测试使用curator来创建一个ZooKeeper客户端
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // ExponentialBackoffRetry是curator提供的几种默认重试策略之一
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES);
        CuratorFramework client = CuratorFrameworkFactory.newClient(Constants.connectString, Constants.sessionTimeout, Constants.connectionTimeout, retryPolicy);
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}