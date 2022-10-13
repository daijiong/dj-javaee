package org.djflying.apache.zookeeper.javaclient.auth;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用含权限信息的ZooKeeper会话创建数据节点
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZookeeperAuthSample {

    final static String PATH = "/zk-book-auth_test";

    /**
     * 测试使用含权限信息的ZooKeeper会话创建数据节点
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        // 对该zookeeper会话创建权限，权限模式是digest，具体的权限信息为foo:true
        zookeeper.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
}