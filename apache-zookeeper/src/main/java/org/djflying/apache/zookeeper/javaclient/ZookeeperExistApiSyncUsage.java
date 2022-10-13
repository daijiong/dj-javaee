package org.djflying.apache.zookeeper.javaclient;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用同步方式检测节点是否存在
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZookeeperExistApiSyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper      zooKeeper;

    /**
     * 测试使用同步方式检测节点是否存在
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        zooKeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZookeeperExistApiSyncUsage());
        connectedSemaphore.await();

        zooKeeper.exists(path, true);
        zooKeeper.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.setData(path, "123".getBytes(), -1);
        zooKeeper.create(path + "/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.delete(path + "/c1", -1);
        zooKeeper.delete(path, -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 异步通知处理
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        try {
            if (KeeperState.SyncConnected == event.getState()) {
                // 创建会话
                if (EventType.None == event.getType() && null == event.getPath()) {
                    connectedSemaphore.countDown();
                }
                // 创建节点
                if (EventType.NodeCreated == event.getType()) {
                    System.out.println("Node(" + event.getPath() + ")Created");
                    zooKeeper.exists(event.getPath(), true);
                }
                // 删除节点
                if (EventType.NodeDeleted == event.getType()) {
                    System.out.println("Node(" + event.getPath() + ")Deleted");
                    zooKeeper.exists(event.getPath(), true);
                }
                // 节点数据内容变更
                if (EventType.NodeDataChanged == event.getType()) {
                    System.out.println("Node(" + event.getPath() + ")DataChanged");
                    zooKeeper.exists(event.getPath(), true);
                }
            }
        } catch (Exception e) {
        }
    }
}