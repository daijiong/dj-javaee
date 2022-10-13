package org.djflying.apache.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Fluent风格的API接口来创建一个ZooKeeper客户端
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class CreateSessionSampleFluent {

    /**
     * 测试使用Fluent风格的API接口来创建一个ZooKeeper客户端
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES);
        // 通过.namespace()方法来定义该客户端的独立命令空间为/base，该客户端对zookeeper上数据节点的任何操作，都是基于该相对目录进行的。
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout).retryPolicy(retryPolicy).namespace("base").build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}