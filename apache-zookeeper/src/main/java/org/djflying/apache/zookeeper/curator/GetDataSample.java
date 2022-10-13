package org.djflying.apache.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator获取数据内容
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class GetDataSample {

    private static final String PATH = "/zk_test1";

    /**
     * 测试使用Curator获取数据内容
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();
        // 1.读取一个节点的数据内容
        byte[] data = client.getData().forPath(PATH);
        System.out.println("数据节点的数据内容为：" + new String(data, "utf-8"));
        // 2.读取一个节点的数据内容，同时获取到该节点的stat
        /*
            Curator通过传入一个旧的stat变量的方式来存储服务端返回的最新的节点状态信息。
         */
        Stat stat = new Stat();
        byte[] data2 = client.getData().storingStatIn(stat).forPath(PATH);
        System.out.println("数据节点的数据内容为：" + new String(data2, "utf-8"));
        System.out.println("数据节点的版本为：" + stat.getVersion());
    }
}