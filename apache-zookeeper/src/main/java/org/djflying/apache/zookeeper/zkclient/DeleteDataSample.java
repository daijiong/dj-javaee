package org.djflying.apache.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.djflying.apache.zookeeper.Constants;

/**
 * 使用ZkClient删除节点数据
 *
 * @author dj4817
 * @version $Id: ZookeeperWatcher.java, v 0.1 2018/3/12 13:44 dj4817 Exp $$
 */
public class DeleteDataSample {

    /**
     * 测试使用ZkClient删除节点数据
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        ZkClient zkClient = new ZkClient(Constants.connectString, Constants.connectionTimeout);
        zkClient.createPersistent(path, "");
        zkClient.createPersistent(path + "/c1", "");
        zkClient.deleteRecursive(path);
    }
}