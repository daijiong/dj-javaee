package org.djflying.apache.zookeeper.curator.tools;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.curator.utils.ZKPaths.PathAndNode;
import org.apache.zookeeper.ZooKeeper;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用ZKPaths
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class ZKPathsSample {

    static String path = "/curator_zkpath_sample";

    /**
     * 测试使用ZKPaths
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(Constants.connectString).sessionTimeoutMs(Constants.sessionTimeout)
            .retryPolicy(new ExponentialBackoffRetry(Constants.BASESLEEPTIMEMS, Constants.MAXRETRIES)).build();
        client.start();

        ZooKeeper zookeeper = client.getZookeeperClient().getZooKeeper();

        System.out.println(ZKPaths.fixForNamespace(path, "sub"));
        System.out.println(ZKPaths.makePath(path, "sub"));
        System.out.println(ZKPaths.getNodeFromPath("/curator_zkpath_sample/sub1"));

        PathAndNode pn = ZKPaths.getPathAndNode("/curator_zkpath_sample/sub1");
        System.out.println(pn.getPath());
        System.out.println(pn.getNode());

        String dir1 = path + "/child1";
        String dir2 = path + "/child2";
        ZKPaths.mkdirs(zookeeper, dir1);
        ZKPaths.mkdirs(zookeeper, dir2);
        System.out.println(ZKPaths.getSortedChildren(zookeeper, path));

        ZKPaths.deleteChildren(client.getZookeeperClient().getZooKeeper(), path, true);
    }
}