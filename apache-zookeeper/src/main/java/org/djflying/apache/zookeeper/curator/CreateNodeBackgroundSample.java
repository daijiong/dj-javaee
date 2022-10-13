package org.djflying.apache.zookeeper.curator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Curator的异步接口
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class CreateNodeBackgroundSample {

    static String          path      = "/zk-book";
    static CountDownLatch  semaphore = new CountDownLatch(2);
    static ExecutorService tp        = Executors.newFixedThreadPool(2);

    /**
     * 测试使用Curator的异步接口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();

        System.out.println("Main thread: " + Thread.currentThread().getName());
        // 此处传入了自定义的Executor，会使用这个Executor来处理。
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            /**
             * called when the async background operation completes
             *
             * @param client 当前客户端实例
             * @param event 服务端事件
             * @throws Exception 异常
             */
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                // 响应码
                /*
                    0(Ok) - 接口调用成功
                    -4(ConnectionLoss) - 客户端与服务端连接已断开
                    -110(NodeExists) - 指定节点已存在
                    -112(SessionExpired) - 会话已过期
                 */
                int rc = event.getResultCode();
                // 事件类型
                /*
                    CREATE,
                    DELETE,
                    EXISTS,
                    GET_DATA,
                    SET_DATA,
                    CHILDREN,
                    SYNC,
                    GET_ACL,
                    SET_ACL,
                    WATCHED,
                    CLOSING;
                 */
                CuratorEventType curatorEventType = event.getType();
                System.out.println("event[code: " + rc + ", type: " + curatorEventType + "]");
                System.out.println("Thread of processResult: " + Thread.currentThread().getName());
                semaphore.countDown();
            }
        }, tp).forPath(path, "init".getBytes());
        // 此处没有传入自定义的Executor，会使用Zookeeper默认的EventThread来处理。
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            /**
             * called when the async background operation completes
             *
             * @param client 当前客户端实例
             * @param event 服务端事件
             * @throws Exception 异常
             */
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("event[code: " + event.getResultCode() + ", type: " + event.getType() + "]");
                System.out.println("Thread of processResult: " + Thread.currentThread().getName());
                semaphore.countDown();
            }
        }).forPath(path, "init".getBytes());
        semaphore.await();
        tp.shutdown();
    }
}