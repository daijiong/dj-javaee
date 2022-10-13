package org.djflying.apache.zookeeper.zkclient;

import java.io.IOException;

import org.I0Itec.zkclient.ZkClient;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用ZkClient来创建一个ZooKeeper客户端
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class CreateSessionSample {

    /**
     * 测试使用ZkClient来创建一个ZooKeeper客户端
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        ZkClient zkClient = new ZkClient(Constants.connectString, Constants.connectionTimeout);
        System.out.println("ZooKeeper session established.");
    }
}