package org.djflying.apache.zookeeper.javaclient;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用异步方式更新数据节点数据内容
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZookeeperSetDataApiAsyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper      zooKeeper;

    /**
     * 测试使用异步方式更新数据节点数据内容
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        zooKeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZookeeperSetDataApiAsyncUsage());
        connectedSemaphore.await();

        zooKeeper.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.setData(path, "456".getBytes(), -1, new IStatCallback(), null);

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
        }
    }
}

/**
 * 使用异步方式更新数据节点数据内容
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
class IStatCallback implements AsyncCallback.StatCallback {

    /**
     *
     *
     * @param rc
     * @param path
     * @param ctx
     * @param stat
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {

        if (rc == 0) {
            System.out.println("SUCCESS");
        }
    }
}