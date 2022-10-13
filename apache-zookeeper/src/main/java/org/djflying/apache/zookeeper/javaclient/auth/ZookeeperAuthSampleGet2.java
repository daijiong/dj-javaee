package org.djflying.apache.zookeeper.javaclient.auth;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用错误权限信息的ZooKeeper会话访问含权限信息的数据节点
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZookeeperAuthSampleGet2 {

    final static String PATH = "/zk-book-auth_test";

    /**
     * 测试使用错误权限信息的ZooKeeper会话访问含权限信息的数据节点
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper1 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        zookeeper1.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper1.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zookeeper2 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        zookeeper2.addAuthInfo("digest", "foo:true".getBytes());
        System.out.println(zookeeper2.getData(PATH, false, null));

        ZooKeeper zookeeper3 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        zookeeper3.addAuthInfo("digest", "foo:false".getBytes());
        // 输出KeeperErrorCode = NoAuth for /zk-book-auth_test异常
        zookeeper3.getData(PATH, false, null);
    }
}