package org.djflying.apache.zookeeper.javaclient.auth;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用Super权限模式进行权限控制
 *
 * @author dj4817
 * @version $Id: AuthSampleSuper.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class AuthSampleSuper {

    final static String PATH = "/zk-book";

    /**
     * 测试使用Super权限模式进行权限控制
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper1 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        zookeeper1.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper1.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zookeeper2 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        // 由于开启了foo:zk-book这个超级管理员账号，所以他可以操作任意一个受权限控制的节点。
        zookeeper2.addAuthInfo("digest", "foo:zk-book".getBytes());
        System.out.println(zookeeper2.getData(PATH, false, null));

        ZooKeeper zookeeper3 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        // 返回KeeperErrorCode = NoAuth异常
        zookeeper3.addAuthInfo("digest", "foo:false".getBytes());
        System.out.println(zookeeper3.getData(PATH, false, null));
    }
}