package org.djflying.apache.zookeeper.javaclient;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用异步方式创建zookeeper节点
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZooKeeperCreateApiAsyncUsage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 测试使用异步方式创建zookeeper节点
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZooKeeperCreateApiAsyncUsage());
        connectedSemaphore.await();

        zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(), "I am context.");

        zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(), "I am context.");

        zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am context.");
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
            connectedSemaphore.countDown();
        }
    }
}

/**
 * 使用异步方式创建zookeeper节点的处理结果回调
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
class IStringCallback implements AsyncCallback.StringCallback {

    /**
     * 回调函数
     *
     * @param rc Result Code，服务端响应码
     * @param path 接口调用时传入API的数据节点的节点路径参数值
     * @param ctx 接口调用时传入API的ctx参数值
     * @param name 实际在服务端创建的节点名
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("Create path result: [" + rc + ", " + path + ", " + ctx + ", real path name: " + name);
    }
}