package org.djflying.apache.zookeeper.javaclient.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.AuthFailedException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用错误的scheme进行授权
 *
 * @author dj4817
 * @version $Id: ZookeeperAuthFailed2Sample.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZookeeperAuthFailed2Sample implements Watcher {

    static ZooKeeper zkClient       = null;
    static ZooKeeper zkClient_error = null;
    static List<ACL> acls           = new ArrayList<ACL>(1);

    static {

        for (ACL ids_acl : Ids.CREATOR_ALL_ACL) {
            acls.add(ids_acl);
        }
    }

    /**
     * 测试使用错误的scheme进行授权
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        try {
            zkClient = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZookeeperAuthFailed2Sample());
            zkClient.addAuthInfo("digest", "taokeeper:true".getBytes());
            zkClient.create("/zk-book", "".getBytes(), acls, CreateMode.EPHEMERAL);

            zkClient_error = new ZooKeeper(Constants.connectString, Constants.sessionTimeout, new ZookeeperAuthFailed2Sample());
            zkClient_error.addAuthInfo("digest2", "taokeeper:error".getBytes());
            zkClient_error.getData("/zk-book", true, null);
        } catch (AuthFailedException e) {
            // 抛出KeeperErrorCode = AuthFailed异常
            e.printStackTrace();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 回调方法
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println(event.getState());
        System.out.println(event.getType());
    }

}
