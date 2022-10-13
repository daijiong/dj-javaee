package org.djflying.apache.zookeeper.curator.tools;

import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingZooKeeperServer;

/**
 * 使用TestingCluster
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class TestingClusterSample {

    /**
     * 测试使用TestingCluster
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 1.模拟3台机器组成的Zookeeper集群
        TestingCluster cluster = new TestingCluster(3);
        cluster.start();
        Thread.sleep(2000);

        TestingZooKeeperServer leader = null;
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            System.out.print(zs.getInstanceSpec().getServerId() + "-");
            System.out.print(zs.getQuorumPeer().getServerState() + "-");
            System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
            if (zs.getQuorumPeer().getServerState().equals("leading")) {
                leader = zs;
            }
        }
        leader.kill();
        System.out.println("--After leader kill:");
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            System.out.print(zs.getInstanceSpec().getServerId() + "-");
            System.out.print(zs.getQuorumPeer().getServerState() + "-");
            System.out.println(zs.getInstanceSpec().getDataDirectory().getAbsolutePath());
        }
        cluster.stop();
    }
}