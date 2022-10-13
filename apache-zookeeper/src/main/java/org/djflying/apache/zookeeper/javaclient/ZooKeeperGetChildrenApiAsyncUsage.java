package org.djflying.apache.zookeeper.javaclient;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用异步方式获取子节点列表
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZooKeeperGetChildrenApiAsyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper      zooKeeper          = null;

    /**
     * 测试使用异步方式获取子节点列表
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        zooKeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZooKeeperGetChildrenApiAsyncUsage());
        connectedSemaphore.await();

        zooKeeper.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(path + "/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zooKeeper.getChildren(path, true, new IChildren2Callback(), null);

        zooKeeper.create(path + "/c2", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 异步通知处理
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {

        if (KeeperState.SyncConnected == event.getState()) {
            // 创建会话
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            }
            // 子节点发生变更
            if (event.getType() == EventType.NodeChildrenChanged) {
                try {
                    System.out.println("ReGet Child:" + zooKeeper.getChildren(event.getPath(), true));
                } catch (Exception e) {
                }
            }
        }
    }
}

/**
 * 使用异步方式获取子节点列表
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
class IChildren2Callback implements AsyncCallback.Children2Callback {

    /**
     *
     * @param rc
     * @param path
     * @param ctx
     * @param children
     * @param stat
     */
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        System.out.println("Get Children znode result: [response code: " + rc + ", param path: " + path + ", ctx: " + ctx + ", children list: " + children + ", stat: " + stat);
    }
}