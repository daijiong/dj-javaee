package org.djflying.apache.zookeeper.javaclient;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用异步方式获取数据节点数据内容
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZookeeperGetDataApiAsyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper      zooKeeper;

    /**
     * 测试使用异步方式获取数据节点数据内容
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        zooKeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZookeeperGetDataApiAsyncUsage());
        connectedSemaphore.await();

        zooKeeper.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zooKeeper.getData(path, true, new IDataCallback(), null);

        zooKeeper.setData(path, "123".getBytes(), -1);

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
            // 数据节点数据内容发生变化
            if (event.getType() == EventType.NodeDataChanged) {
                try {
                    zooKeeper.getData(event.getPath(), true, new IDataCallback(), null);
                } catch (Exception e) {
                }
            }
        }
    }
}

/**
 * 使用异步方式获取数据节点数据内容
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
class IDataCallback implements AsyncCallback.DataCallback {

    /**
     *
     *
     * @param rc
     * @param path
     * @param ctx
     * @param data
     * @param stat
     */
    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {

        System.out.println(rc + ", " + path + ", " + new String(data));
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
    }
}
