package org.djflying.apache.zookeeper.javaclient;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用简单方式创建zookeeper会话
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZooKeeperConstructorUsageSimple implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 测试使用简单方式创建zookeeper会话
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZooKeeperConstructorUsageSimple());
        System.out.println(zookeeper.getState());
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
        }
        System.out.println("ZooKeeper session established.");
    }

    /**
     * 异步通知处理
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event：" + event);
        if (KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}