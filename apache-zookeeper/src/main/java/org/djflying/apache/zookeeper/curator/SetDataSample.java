package org.djflying.apache.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator更新数据内容
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class SetDataSample {

    private static final String PATH = "/zktest";

    /**
     * 测试使用Curator更新数据内容
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();
        // 1.更新一个节点的数据内容，返回更新之后最新的节点信息
        Stat stat = client.setData().forPath(PATH, "hello1".getBytes());
        System.out.println("数据节点更新之后的版本号为：" + stat.getVersion());
        // 2.更新一个节点的数据内容，强制指定版本进行更新
        /*
            withVersion()接口就是用来实现CAS的，version通常是从一个旧的stat对象中获取到的
         */
        Stat oldStat = new Stat();
        byte[] data = client.getData().storingStatIn(oldStat).forPath(PATH);
        System.out.println("节点的数据内容为：" + new String(data, "utf-8"));
        Stat newStat = client.setData().withVersion(oldStat.getVersion()).forPath(PATH, "hello1".getBytes());
        int newVersion = newStat.getVersion();
        System.out.println("Success set node for : " + PATH + ", new version: " + newVersion);
        try {
            // 使用过期的stat变量进行更新操作，会抛出异常：KeeperErrorCode = BadVersion
            client.setData().withVersion(oldStat.getVersion()).forPath(PATH);
        } catch (Exception e) {
            System.out.println("Fail set nodefor : " + PATH + ", due to " + e.getMessage());
        }
    }
}