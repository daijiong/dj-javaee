package org.djflying.apache.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator删除节点
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class DeleteDataSample {

    /**
     * 测试使用Curator删除节点
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();
        // 1.删除一个节点
        /*
            使用该接口只能删除叶子节点
         */
        client.delete().forPath("/zk_test1");
        // 2.删除一个节点，并递归删除其所有子节点
        client.delete().deletingChildrenIfNeeded().forPath("/zk_test2");
        // 3.删除一个节点，强制指定版本进行删除，并递归删除其所有子节点
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/zk_test4");
        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath("/zk_test4");
        // 4.删除一个节点，强制保证删除，并递归删除其所有子节点
        /*
            guaranteed()接口是一个保障措施，只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到节点删除成功。
         */
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/zk_test5");
    }
}