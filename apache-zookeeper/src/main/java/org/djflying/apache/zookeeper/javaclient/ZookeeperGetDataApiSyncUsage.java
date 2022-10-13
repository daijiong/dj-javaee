package org.djflying.apache.zookeeper.javaclient;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用同步方式获取数据节点数据内容
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZookeeperGetDataApiSyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper      zooKeeper          = null;
    private static Stat           stat               = new Stat();

    /**
     * 测试使用同步方式获取数据节点数据内容
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        zooKeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZookeeperGetDataApiSyncUsage());
        connectedSemaphore.await();

        zooKeeper.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println(new String(zooKeeper.getData(path, true, stat)));
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());

        zooKeeper.setData(path, "456".getBytes(), -1);

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
            // 数据节点数据内容发生变更
            if (event.getType() == EventType.NodeDataChanged) {
                try {
                    System.out.println(new String(zooKeeper.getData(event.getPath(), true, stat)));
                    System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
                } catch (Exception e) {
                }
            }
        }
    }
}