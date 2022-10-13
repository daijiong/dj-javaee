package org.djflying.apache.zookeeper.curator.tools;

import java.io.File;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用TestingServer
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class TestingServerSample {

    static String path = "/zookeeper";

    /**
     * 测试使用TestingServer
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        TestingServer server = new TestingServer(2181, new File("/home/Documents/zktestingserver.txt"));

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(server.getConnectString()).sessionTimeoutMs(Constants.sessionTimeout)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();

        System.out.println(client.getChildren().forPath(path));
        server.close();
    }
}