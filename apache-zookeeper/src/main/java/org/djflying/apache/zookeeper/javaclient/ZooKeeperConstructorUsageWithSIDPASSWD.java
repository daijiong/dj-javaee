package org.djflying.apache.zookeeper.javaclient;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用SessionId和SessionPasswd创建zookeeper会话
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZooKeeperConstructorUsageWithSIDPASSWD implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 测试使用SessionId和SessionPasswd创建zookeeper会话
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZooKeeperConstructorUsageWithSIDPASSWD());
        connectedSemaphore.await();
        long sessionId = zookeeper.getSessionId();
        byte[] passwd = zookeeper.getSessionPasswd();
        System.out.println("sessionId=" + sessionId + "; passwd=" + passwd.toString());

        //Use illegal sessionId and sessionPassWd
        new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZooKeeperConstructorUsageWithSIDPASSWD(), 1L, "test".getBytes());
        //Use correct sessionId and sessionPassWd
        new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZooKeeperConstructorUsageWithSIDPASSWD(), sessionId, passwd);
        Thread.sleep(Integer.MAX_VALUE);
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