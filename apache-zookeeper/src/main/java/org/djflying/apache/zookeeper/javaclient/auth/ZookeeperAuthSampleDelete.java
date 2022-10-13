package org.djflying.apache.zookeeper.javaclient.auth;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 删除节点的权限控制
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZookeeperAuthSampleDelete {

    final static String PATH  = "/zk-book-auth_test";
    final static String PATH2 = "/zk-book-auth_test/child";

    /**
     * 测试删除节点的权限控制
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ZooKeeper zookeeper1 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        zookeeper1.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper1.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zookeeper1.create(PATH2, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        try {
            ZooKeeper zookeeper2 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
            zookeeper2.delete(PATH2, -1);
        } catch (Exception e) {
            System.out.println("删除节点失败: " + e.getMessage());
        }

        ZooKeeper zookeeper3 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        zookeeper3.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper3.delete(PATH2, -1);
        System.out.println("成功删除节点：" + PATH2);

        ZooKeeper zookeeper4 = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, null);
        // 删除操作，作用范围是其子节点。
        zookeeper4.delete(PATH, -1);
        System.out.println("成功删除节点：" + PATH);
    }
}