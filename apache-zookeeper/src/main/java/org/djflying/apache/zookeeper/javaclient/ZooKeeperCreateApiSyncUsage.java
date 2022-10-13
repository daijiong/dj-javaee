package org.djflying.apache.zookeeper.javaclient;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用同步方式创建zookeeper节点
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZooKeeperCreateApiSyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 测试使用同步方式创建zookeeper节点
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZooKeeperCreateApiSyncUsage());
        connectedSemaphore.await();
        String path1 = zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path1);
        String path2 = zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: " + path2);
    }

    /**
     * 异步通知处理
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}
